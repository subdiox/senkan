package com.mob.commons.iosbridge;

class a implements Runnable {
    final /* synthetic */ UDPServer a;

    a(UDPServer uDPServer) {
        this.a = uDPServer;
    }

    public void run() {
        this.a.c = true;
        this.a.c();
    }
}
