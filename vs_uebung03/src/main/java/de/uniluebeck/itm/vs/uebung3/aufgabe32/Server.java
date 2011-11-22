package de.uniluebeck.itm.vs.uebung3.aufgabe32;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

    private int port;


    public Server(int port) {
        this.port = port;
    }


    public void run() {
        try {
            // create listenig socket and wait for client
            ServerSocket socket = new ServerSocket(this.port);
            Socket client = socket.accept();
            
            // use existing code to do the work
            de.uniluebeck.itm.vs.uebung3.aufgabe31.Server innerServer = 
                    new de.uniluebeck.itm.vs.uebung3.aufgabe31.Server(client.getInputStream(), client.getOutputStream());
            
            innerServer.handleOne();
            client.close();
        } catch (IOException e) {
            //TODO was könnte man hier sinnvolles tun?
        }
    }
}
