package de.uniluebeck.itm.vs.uebung3.aufgabe32;

import de.uniluebeck.itm.vs.uebung3.apps.ClientApp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

//This is the Client for exercises 3.2, 3.3 and 3.4 and MUST not be modified!
public class Client implements Runnable{

    private int target_port;

    public Client(int target_port){
        this.target_port = target_port;
    }

    public void run(){
        try{
            Socket client_socket = new Socket("127.0.0.1", target_port);
            System.out.println("Client (" + Thread.currentThread().getName() +
                    ") connected to server: " + client_socket.isConnected());
            InputStream c_in = client_socket.getInputStream();
            OutputStream c_out = client_socket.getOutputStream();

            new ClientApp(c_in, c_out).run();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
