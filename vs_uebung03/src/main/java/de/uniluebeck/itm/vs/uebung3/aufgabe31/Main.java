package de.uniluebeck.itm.vs.uebung3.aufgabe31;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * This is the Main class to test exercise 3.1 and MUST not be modified!
 */
public class Main {

    public static void main(String[] args) throws IOException {
        final PipedOutputStream c_out = new PipedOutputStream();
        final PipedOutputStream s_out = new PipedOutputStream();

        final PipedInputStream s_in = new PipedInputStream(c_out);
        final PipedInputStream c_in = new PipedInputStream(s_out);

        //Client Thread
        new Thread(){
            @Override
            public void run(){
                Client client = new Client(c_in, c_out);
                client.run();
            }
        }.start();

        //Server Thread
        new Thread(){
            @Override
            public void run(){
                Server server = new Server(s_in, s_out);
                server.run();
            }
        }.start();
    }
}
