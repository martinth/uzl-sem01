package de.uniluebeck.itm.vs.uebung3.aufgabe32;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import de.uniluebeck.itm.vs.uebung3.SpeiseplanServer;
import de.uniluebeck.itm.vs.uebung3.serialization.DecodingException;
import de.uniluebeck.itm.vs.uebung3.serialization.EncodingException;

public class Server implements Runnable {


    private ServerSocket serverSocket = null;

    public Server(int port) {
        // create listenig socket
        try {
            serverSocket  = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Bindind of server socket failed!");
            e.printStackTrace();
        }
    }

    public void run() {
        // this loop waits for clients and handles them sequentially
        while (serverSocket != null && !serverSocket.isClosed()) {

            Socket clientSocket = null;
            try {
                clientSocket = serverSocket.accept(); // may block

                // use inner server for the response
                SpeiseplanServer innerServer = new SpeiseplanServer(clientSocket);
                innerServer.handleOne();
            } catch (IOException e) {
                // let finally close the socket
            } catch (DecodingException e) {
                // let finally close the socket
            } catch (EncodingException e) {
                // let finally close the socket
            } finally {
                try {
                    if (clientSocket != null) {
                        clientSocket.close();
                    }
                } catch (IOException e) {
                    // so "close()" failed, this is somewhat bad and somewhat
                    // irrelevant
                }
            }
        }

    }
}
