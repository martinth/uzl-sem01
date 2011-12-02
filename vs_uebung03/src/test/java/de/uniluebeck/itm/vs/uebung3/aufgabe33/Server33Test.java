package de.uniluebeck.itm.vs.uebung3.aufgabe33;

import org.junit.BeforeClass;
import org.junit.Test;

import de.uniluebeck.itm.vs.uebung3.aufgabe32.Client;

public class Server33Test {
    
    private static int PORT = 3334;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        //Server Thread
        new Thread(){
            @Override
            public void run(){
                Server server = new Server(PORT);
                server.run();
            }
        }.start();
    }

    @Test
    public void test() {
        for (int i = 0; i< 20; i++) {
            //Client Thread
            new Thread(){
                @Override
                public void run(){
                    Client client = new Client(PORT);
                    client.run();
                }
            }.start();
        }
    }
}
