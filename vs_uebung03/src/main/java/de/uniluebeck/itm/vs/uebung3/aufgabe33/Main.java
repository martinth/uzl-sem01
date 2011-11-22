package de.uniluebeck.itm.vs.uebung3.aufgabe33;

import de.uniluebeck.itm.vs.uebung3.aufgabe32.Client;

/**
 * This is the Main class to test exercise 3.3 and MUST not be modified!
 */
public class Main {

    public static void main(String[] args){
        final int port = 3333;

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
