package com.mob.commons.iosbridge;

import android.content.Context;
import android.util.Base64;
import com.mob.commons.a;
import com.mob.commons.j;
import com.mob.tools.MobLog;
import com.mob.tools.network.KVPair;
import com.mob.tools.network.NetworkHelper;
import com.mob.tools.network.NetworkHelper.NetworkTimeOut;
import com.mob.tools.utils.Data;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.Hashon;
import com.mob.tools.utils.R;
import com.rekoo.libs.net.URLCons;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CRC32;

public class UDPServer {
    private static UDPServer a;
    private Context b;
    private boolean c;

    private UDPServer(Context context) {
        this.b = context.getApplicationContext();
    }

    private String a(String str) {
        Process exec = Runtime.getRuntime().exec("cat /proc/net/arp");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
        CharSequence readLine = bufferedReader.readLine();
        String str2 = null;
        while (readLine != null) {
            if (readLine.startsWith(str) && !readLine.contains("00:00:00:00:00:00")) {
                Matcher matcher = Pattern.compile("\\w{2}:\\w{2}:\\w{2}:\\w{2}:\\w{2}:\\w{2}").matcher(readLine);
                if (matcher.find()) {
                    str2 = matcher.group();
                    System.out.println("ipToHwAddr: " + str + " -- " + str2);
                }
            }
            readLine = bufferedReader.readLine();
        }
        exec.waitFor();
        return str2;
    }

    private void a() {
        if (!this.c) {
            try {
                j.a(new File(R.getCacheRoot(this.b), "comm/locks/.usLock"), new a(this));
            } catch (Throwable th) {
                MobLog.getInstance().w(th);
            }
        }
    }

    private boolean a(String str, String str2) {
        HashMap hashMap = new HashMap();
        hashMap.put("imei", DeviceHelper.getInstance(this.b).getIMEI());
        hashMap.put("serialno", DeviceHelper.getInstance(this.b).getSerialno());
        hashMap.put(URLCons.MAC, DeviceHelper.getInstance(this.b).getMacAddress());
        hashMap.put("model", DeviceHelper.getInstance(this.b).getModel());
        hashMap.put("plat", Integer.valueOf(1));
        HashMap hashMap2 = new HashMap();
        hashMap2.put(URLCons.MAC, str2);
        hashMap2.put("udpbody", str);
        hashMap.put("iosdata", hashMap2);
        String encodeToString = Base64.encodeToString(Data.AES128Encode("sdk.commonap.sdk", new Hashon().fromHashMap(hashMap)), 2);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new KVPair("m", encodeToString));
        NetworkTimeOut networkTimeOut = new NetworkTimeOut();
        networkTimeOut.connectionTimeout = 5000;
        networkTimeOut.readTimout = 30000;
        encodeToString = new NetworkHelper().httpPost("http://devs.data.mob.com:80/macinfo", arrayList, null, null, networkTimeOut);
        return encodeToString != null && encodeToString.contains("\"status\":200");
    }

    private byte[] a(byte b, short s, byte[] bArr, int i, String str) {
        switch (b) {
            case (byte) 1:
                return a(s);
            case (byte) 3:
                return a(s, bArr, i, str);
            default:
                return null;
        }
    }

    private byte[] a(short s) {
        switch (s) {
            case (short) 1:
                OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                dataOutputStream.writeShort(s);
                dataOutputStream.writeByte(2);
                dataOutputStream.writeInt(0);
                dataOutputStream.flush();
                dataOutputStream.close();
                return byteArrayOutputStream.toByteArray();
            default:
                return null;
        }
    }

    private byte[] a(short s, byte[] bArr, int i, String str) {
        switch (s) {
            case (short) 1:
                String str2 = new String(bArr, 11, i, "utf-8");
                String a = a(str);
                if (a == null || !a(str2, a)) {
                    return null;
                }
                OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                dataOutputStream.writeShort(s);
                dataOutputStream.writeByte(4);
                dataOutputStream.writeInt(0);
                dataOutputStream.flush();
                dataOutputStream.close();
                return byteArrayOutputStream.toByteArray();
            default:
                return null;
        }
    }

    private void b() {
        this.c = false;
    }

    private void c() {
        try {
            DatagramSocket datagramSocket = new DatagramSocket(9058);
            datagramSocket.setSoTimeout(5000);
            byte[] bArr = new byte[16395];
            while (this.c) {
                if (a.a(this.b)) {
                    DatagramPacket datagramPacket = new DatagramPacket(bArr, bArr.length);
                    datagramSocket.receive(datagramPacket);
                    InetAddress address = datagramPacket.getAddress();
                    int port = datagramPacket.getPort();
                    short s = (short) (((bArr[0] & 255) << 8) + (bArr[1] & 255));
                    byte b = bArr[2];
                    int i = (bArr[6] & 255) + ((((bArr[3] & 255) << 24) + ((bArr[4] & 255) << 16)) + ((bArr[5] & 255) << 8));
                    if (i > 0) {
                        long j = ((((((long) bArr[7]) & 255) << 24) + ((((long) bArr[8]) & 255) << 16)) + ((((long) bArr[9]) & 255) << 8)) + (((long) bArr[10]) & 255);
                        CRC32 crc32 = new CRC32();
                        crc32.update(bArr, 11, i);
                        if (j != crc32.getValue()) {
                        }
                    }
                    byte[] a = a(b, s, bArr, i, address.getHostAddress());
                    if (a != null) {
                        datagramSocket.send(new DatagramPacket(a, a.length, address, port));
                    }
                } else {
                    return;
                }
            }
        } catch (SocketTimeoutException e) {
        } catch (Throwable th) {
            MobLog.getInstance().w(th);
        }
    }

    public static synchronized void start(Context context) {
        synchronized (UDPServer.class) {
            if (a == null) {
                a = new UDPServer(context);
            }
            a.a();
        }
    }

    public synchronized void stop() {
        if (a != null) {
            a.b();
        }
    }
}
