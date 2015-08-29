/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diamonddev.chatappserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.glassfish.tyrus.server.Server;

/**
 *
 * @author Diamond
 */
public class ChatAppServer {

    public static void main(String[] args) {
        runServer();
    }

    public static void runServer() {

        // This implies that your server URL will look like "ws://localhost:8025/chatapp-websockets/chat"
        Server server = new Server("localhost", 8025, "/chatapp-websockets", ChatAppEndpoint.class);
        try {
            server.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Place your cursor here and press enter to close the connection.");
            reader.readLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            server.stop();
        }
    }
}
