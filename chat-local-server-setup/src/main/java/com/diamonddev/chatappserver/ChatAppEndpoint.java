/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diamonddev.chatappserver;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Diamond
 */
@ServerEndpoint("/chat")
public class ChatAppEndpoint {

    private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());

    @OnMessage
    public String onMessage(Session session, String message) {
        System.out.println(session.getUserProperties());
        if (!session.getUserProperties().containsKey("name")) {
            session.getUserProperties().put("name", message);
            for (Session peer : peers) {
                try {
                    if (!peer.getUserProperties().containsValue(message)) {
                        peer.getBasicRemote().sendText(message + " has joined.");
                    }
                } catch (IOException ex) {
                    Logger.getLogger(ChatAppEndpoint.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return null;
        }
        for (Session peer : peers) {
            try {
                System.out.print(peer.getId());
                peer.getBasicRemote().sendText(session.getUserProperties().get("name") + ": " + message);
            } catch (IOException ex) {
                Logger.getLogger(ChatAppEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @OnOpen
    public void onOpen(Session peer) {
        peers.add(peer);
    }

    @OnClose
    public void onClose(Session peer) {
        peers.remove(peer);
    }

}
