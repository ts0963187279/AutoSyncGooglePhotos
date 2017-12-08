package com.walton.java.example.module;

import com.walton.java.example.processor.SyncGoogleTask;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main{
    public static void main(String[] args){
        try {
            ServerSocket serverSocket = new ServerSocket(5566);
            while (true) {
                System.out.println("wait to connect.....");
                Socket socket = serverSocket.accept();
                new SyncGoogleTask(socket).start();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}