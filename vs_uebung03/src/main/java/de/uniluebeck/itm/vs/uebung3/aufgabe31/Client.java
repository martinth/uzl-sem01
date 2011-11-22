package de.uniluebeck.itm.vs.uebung3.aufgabe31;

import de.uniluebeck.itm.vs.uebung3.apps.ClientApp;

import java.io.InputStream;
import java.io.OutputStream;

//This is the client for exercise 3.1 and MUST not be modified!
public class Client implements Runnable{

    private InputStream c_in;
    private OutputStream c_out;

    public Client(InputStream in, OutputStream out){
        this.c_in = in;
        this.c_out = out;
    }

    public void run() {
        new ClientApp(c_in, c_out).run();
    }
}
