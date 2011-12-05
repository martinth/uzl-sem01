package de.uniluebeck.itm.vs.uebung3.aufgabe31;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import de.uniluebeck.itm.vs.uebung3.SpeiseplanServer;
import de.uniluebeck.itm.vs.uebung3.serialization.DecodingException;
import de.uniluebeck.itm.vs.uebung3.serialization.EncodingException;

public class Server implements Runnable {

    private SpeiseplanServer server;
    private OutputStream out;
    private InputStream in;

    public Server(InputStream in, OutputStream out) {
        this.in = in;
        this.out = out;
        this.server = new SpeiseplanServer(in, out);
    }

    /**
     * Within this method incoming data must be read from "in", processed and
     * the response must be written to "out". Use
     * SpeiseplanDatabase.getSpeiseplan(int dayOfYear) to determine the actual
     * dishes.
     */
    public void run() {
        try {
            this.server.handleOne();
        } catch (IOException e) {
            // can't do anything useful, let finally clean up
        	System.err.println(e);
        } catch (DecodingException e) {
            // can't do anything useful, let finally clean up
        	System.err.println(e);
        } catch (EncodingException e) {
            // can't do anything useful, let finally clean up
        	System.err.println(e);
        } finally {
            try {
                this.out.close();
                this.in.close();
            } catch (IOException e) {
                // what the hell, if close() fails there are probably bigger
                // problems
            	System.err.println(e);
            }
        }
    }
}
