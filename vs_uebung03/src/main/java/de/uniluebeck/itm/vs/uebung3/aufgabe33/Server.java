package de.uniluebeck.itm.vs.uebung3.aufgabe33;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.uniluebeck.itm.vs.uebung3.SpeiseplanServer;
import de.uniluebeck.itm.vs.uebung3.serialization.DecodingException;
import de.uniluebeck.itm.vs.uebung3.serialization.EncodingException;

/**
 * Created by IntelliJ IDEA.
 * User: olli
 * Date: 21.11.11
 * Time: 08:33
 * To change this template use File | Settings | File Templates.
 */
public class Server implements Runnable {
    
    private static int POOL_SIZE = 10;
    private ServerSocket serverSocket = null;
    private ExecutorService pool;

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            // we can't re-raise the exception (but we should!) because this would break calling code
            e.printStackTrace();
        }
        pool = Executors.newFixedThreadPool(POOL_SIZE);
    }

    public void run() {
     // this loop waits for clients and handles them sequentially
        while (serverSocket != null && !serverSocket.isClosed()) {
            try {
                pool.execute(new SpeiseplanHandler(serverSocket.accept()));
            } catch (IOException e) {
                pool.shutdown();
                try {
                    serverSocket.close();
                } catch (IOException e1) {
                    // there is nothing we can do
                    e1.printStackTrace();
                }
            }
            
        }
    }
}

class SpeiseplanHandler implements Runnable {

    private Socket clientSocket;

    public SpeiseplanHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        // use inner server for the response
        try {
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
