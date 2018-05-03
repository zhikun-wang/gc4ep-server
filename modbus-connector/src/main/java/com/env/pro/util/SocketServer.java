package com.env.pro.util;

import com.env.pro.util.exception.SocketOperatorException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 Created by Zhikun on 12/01/2018. */
public class SocketServer {

    public static Map<String, Socket> allSocket = new HashMap<>();

    public static Socket getSocket(String ip) {
        if (allSocket.containsKey(ip))
            return allSocket.get(ip);
        throw new SocketOperatorException("not exist socket");
    }

    public static void start(int port) {
        new Thread(() -> {
            ServerSocket serverSocket = null;
            try {
                System.out.println("socket listening on [" + port + "]");
                serverSocket = new ServerSocket(port);
                while (true) {
                    System.out.println("wait receive message from client...");
                    //接收客户端连接的socket对象
                    Socket socket = null;
                    //接收客户端传过来的数据，会阻塞
                    socket = serverSocket.accept();

                    System.out.println(socket.toString());
                    System.out.println(socket.getRemoteSocketAddress());
                    socket.getRemoteSocketAddress();
                    allSocket.put(socket.getInetAddress().getHostName(), socket);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (serverSocket != null) {
                    try {
                        serverSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}
