package com.yunpengai.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOServer {
    public static void main(String[] args) {
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(6666);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("服务器启动了");
        while (true) {
            final Socket socket;
            try {
                socket = serverSocket.accept();
                System.out.println("连接到一个客户端");
                newCachedThreadPool.execute(new Runnable() {
                    public void run() {
                        handler(socket);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static void handler(Socket socket) {
        try {
            System.out.println("线程信息 ID =" + Thread.currentThread().getId() + "名字=" + Thread.currentThread().getName());
            byte[] bytes = new byte[1024];
            InputStream inputStream = socket.getInputStream();

            while (true) {
                System.out.println("线程信息 ID =" + Thread.currentThread().getId() + "名字=" + Thread.currentThread().getName());
                int read = inputStream.read(bytes);
                if (read != -1) {
                    System.out.println(new String(bytes, 0, read));
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("关闭和client的连接");
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }



    }
}
