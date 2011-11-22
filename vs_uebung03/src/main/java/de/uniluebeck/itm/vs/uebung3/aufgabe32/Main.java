package de.uniluebeck.itm.vs.uebung3.aufgabe32;

import java.io.IOException;

/**
 * This is the Main class to test exercise 3.2 and MUST not be modified!
 */
public class Main {

    public static void main(String[] args) throws IOException {
        final int port = 3322;

        //Server Thread
        new Thread(){
            @Override
            public void run(){
                Server server = new Server(port);
                server.run();
            }
        }.start();

        //Client Thread
        new Thread(){
            @Override
            public void run(){
                Client client = new Client(port);
                client.run();
            }
        }.start();
    }


}
