package com.example.yooo.ultimatechat;

import android.app.Application;
import android.util.Log;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

class WebSocketControls {
    // Client can emit:
    public static final String REQUEST_ROOM_NAMES = "GET_ROOMS";
    public static final String PUBLISH_NEW_ROOM = "NEW_ROOM";
    public static final String DELETE_ROOM = "DELETE_ROOM";
    public static final String JOIN_ROOM = "JOIN_ROOM";
    public static final String LEAVE_ROOM = "LEAVE_ROOM";
    public static final String MESSAGE_SEND = "MESSAGE_SEND";
    public static final String REQUEST_MESSAGE_HISTORY = "REQUEST_MESSAGE_HISTORY";
    public static final String NEW_USER = "NEW_USER";
    public static final String USER_LEAVE = "USER_LEAVE";

    // Server emits:
    public static final String ERR_ROOM_EXISTS = "ERR_ROOM_EXISTS";
    public static final String REPOPULATE_ROOMS = "REPOPULATE_ROOMS";
    public static final String NEW_MESSAGE = "NEW_MESSAGE";
    public static final String RESPONSE_MESSAGE_HISTORY = "RESPONSE_MESSAGE_HISTORY";


    private static Socket mSocket;

    private static void initSocket() {
        try {
            mSocket = IO.socket("http://192.168.1.42:1338");
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

