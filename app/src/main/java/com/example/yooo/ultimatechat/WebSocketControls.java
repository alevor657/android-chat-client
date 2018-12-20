package com.example.yooo.ultimatechat;

import android.app.Application;
import android.util.Log;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

class WebSocketControls {
    public static final String REQUEST_ROOM_NAMES = "get rooms";
    public static final String PUBLISH_NEW_ROOM = "new room";
    public static final String RECIEVED_ROOMS = "rooms";
    public static final String DELETE_ROOM = "delete room";
    public static final String ERR_ROOM_EXISTS = "ERR_ROOM_EXISTS";


    private static Socket mSocket;

    private static void initSocket() {
        try {
            mSocket = IO.socket("http://192.168.1.48:1338");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static Socket getSocket() {
        if (mSocket == null) {
            initSocket();
        }
        return mSocket;
    }
}

