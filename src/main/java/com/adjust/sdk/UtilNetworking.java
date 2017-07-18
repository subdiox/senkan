package com.adjust.sdk;

import android.net.Uri;
import android.net.Uri.Builder;
import com.rekoo.libs.net.URLCons;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONException;
import org.json.JSONObject;

public class UtilNetworking {
    private static String userAgent;

    private static ILogger getLogger() {
        return AdjustFactory.getLogger();
    }

    public static void setUserAgent(String userAgent) {
        userAgent = userAgent;
    }

    public static ResponseData createPOSTHttpsURLConnection(String urlString, ActivityPackage activityPackage, int queueSize) throws Exception {
        Exception e;
        Throwable th;
        DataOutputStream wr = null;
        try {
            HttpsURLConnection connection = AdjustFactory.getHttpsURLConnection(new URL(urlString));
            Map<String, String> parameters = new HashMap(activityPackage.getParameters());
            setDefaultHttpsUrlConnectionProperties(connection, activityPackage.getClientSdk());
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            DataOutputStream wr2 = new DataOutputStream(connection.getOutputStream());
            try {
                wr2.writeBytes(getPostDataString(parameters, queueSize));
                ResponseData responseData = readHttpResponse(connection, activityPackage);
                if (wr2 != null) {
                    try {
                        wr2.flush();
                        wr2.close();
                    } catch (Exception e2) {
                    }
                }
                return responseData;
            } catch (Exception e3) {
                e = e3;
                wr = wr2;
                try {
                    throw e;
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                wr = wr2;
                if (wr != null) {
                    try {
                        wr.flush();
                        wr.close();
                    } catch (Exception e4) {
                    }
                }
                throw th;
            }
        } catch (Exception e5) {
            e = e5;
            throw e;
        }
    }

    public static ResponseData createGETHttpsURLConnection(ActivityPackage activityPackage) throws Exception {
        try {
            HttpsURLConnection connection = AdjustFactory.getHttpsURLConnection(new URL(buildUri(activityPackage.getPath(), new HashMap(activityPackage.getParameters())).toString()));
            setDefaultHttpsUrlConnectionProperties(connection, activityPackage.getClientSdk());
            connection.setRequestMethod("GET");
            return readHttpResponse(connection, activityPackage);
        } catch (Exception e) {
            throw e;
        }
    }

    private static ResponseData readHttpResponse(HttpsURLConnection connection, ActivityPackage activityPackage) throws Exception {
        String message;
        StringBuffer sb = new StringBuffer();
        ILogger logger = getLogger();
        ResponseData responseData = ResponseData.buildResponseData(activityPackage);
        try {
            InputStream inputStream;
            connection.connect();
            Integer responseCode = Integer.valueOf(connection.getResponseCode());
            if (responseCode.intValue() >= 400) {
                inputStream = connection.getErrorStream();
            } else {
                inputStream = connection.getInputStream();
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                sb.append(line);
            }
            if (connection != null) {
                connection.disconnect();
            }
            String stringResponse = sb.toString();
            logger.verbose("Response: %s", stringResponse);
            if (!(stringResponse == null || stringResponse.length() == 0)) {
                JSONObject jsonResponse = null;
                try {
                    jsonResponse = new JSONObject(stringResponse);
                } catch (JSONException e) {
                    message = String.format("Failed to parse json response. (%s)", new Object[]{e.getMessage()});
                    logger.error(message, new Object[0]);
                    responseData.message = message;
                }
                if (jsonResponse != null) {
                    responseData.jsonResponse = jsonResponse;
                    message = jsonResponse.optString("message", null);
                    responseData.message = message;
                    responseData.timestamp = jsonResponse.optString("timestamp", null);
                    responseData.adid = jsonResponse.optString(URLCons.ADID, null);
                    if (message == null) {
                        message = "No message found";
                    }
                    if (responseCode == null || responseCode.intValue() != 200) {
                        logger.error("%s", message);
                    } else {
                        logger.info("%s", message);
                        responseData.success = true;
                    }
                }
            }
            return responseData;
        } catch (Exception e2) {
            logger.error("Failed to read response. (%s)", e2.getMessage());
            throw e2;
        } catch (Throwable th) {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private static String getPostDataString(Map<String, String> body, int queueSize) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        for (Entry<String, String> entry : body.entrySet()) {
            String encodedName = URLEncoder.encode((String) entry.getKey(), "UTF-8");
            String value = (String) entry.getValue();
            String encodedValue = value != null ? URLEncoder.encode(value, "UTF-8") : "";
            if (result.length() > 0) {
                result.append("&");
            }
            result.append(encodedName);
            result.append("=");
            result.append(encodedValue);
        }
        String dateString = Util.dateFormatter.format(Long.valueOf(System.currentTimeMillis()));
        result.append("&");
        result.append(URLEncoder.encode("sent_at", "UTF-8"));
        result.append("=");
        result.append(URLEncoder.encode(dateString, "UTF-8"));
        if (queueSize > 0) {
            result.append("&");
            result.append(URLEncoder.encode("queue_size", "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode("" + queueSize, "UTF-8"));
        }
        return result.toString();
    }

    private static void setDefaultHttpsUrlConnectionProperties(HttpsURLConnection connection, String clientSdk) {
        connection.setRequestProperty("Client-SDK", clientSdk);
        connection.setConnectTimeout(60000);
        connection.setReadTimeout(60000);
        if (userAgent != null) {
            connection.setRequestProperty("User-Agent", userAgent);
        }
    }

    private static Uri buildUri(String path, Map<String, String> parameters) {
        Builder uriBuilder = new Builder();
        uriBuilder.scheme("https");
        uriBuilder.authority(Constants.AUTHORITY);
        uriBuilder.appendPath(path);
        for (Entry<String, String> entry : parameters.entrySet()) {
            uriBuilder.appendQueryParameter((String) entry.getKey(), (String) entry.getValue());
        }
        uriBuilder.appendQueryParameter("sent_at", Util.dateFormatter.format(Long.valueOf(System.currentTimeMillis())));
        return uriBuilder.build();
    }
}
