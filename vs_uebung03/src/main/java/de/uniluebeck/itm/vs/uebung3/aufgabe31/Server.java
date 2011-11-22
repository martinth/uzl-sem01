package de.uniluebeck.itm.vs.uebung3.aufgabe31;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import de.uniluebeck.itm.vs.uebung3.apps.SpeiseplanDatabase;
import de.uniluebeck.itm.vs.uebung3.serialization.DecodingException;
import de.uniluebeck.itm.vs.uebung3.serialization.EncodingException;
import de.uniluebeck.itm.vs.uebung3.serialization.elementwise.SpeiseplanRequestDecoder;
import de.uniluebeck.itm.vs.uebung3.serialization.elementwise.SpeiseplanResponseEncoder;
import de.uniluebeck.itm.vs.uebung3.types.Speiseplan;
import de.uniluebeck.itm.vs.uebung3.types.SpeiseplanRequest;
import de.uniluebeck.itm.vs.uebung3.types.SpeiseplanResponse;

public class Server implements Runnable{

    private InputStream in;
    private OutputStream out;
    private SpeiseplanRequestDecoder decoder;
    private SpeiseplanResponseEncoder encoder;

    public Server(InputStream in, OutputStream out){
        this.in = in;
        this.out = out;
        
        decoder = new SpeiseplanRequestDecoder();
        encoder = new SpeiseplanResponseEncoder();
    }

    /**
     * Within this method incoming data must be read from "in", processed and the response must be written to "out".
     * Use SpeiseplanDatabase.getSpeiseplan(int dayOfYear) to determine the actual dishes.
     */
    public void run() {
        try {
            // read header and determine length
            ByteBuffer buf = ByteBuffer.allocate(4);
            for(int i= 0; i <4; i++) {
                buf.put((byte) in.read());
            }
            int length = buf.getInt(0);
            
            // read data
            ByteBuffer inputBuffer = ByteBuffer.allocate(length);
            for(int i= 0; i<length; i++) {
                inputBuffer.put((byte) in.read());
            }

            // get and encode response
            SpeiseplanRequest request = decoder.decode(inputBuffer.array());
            Speiseplan plan = SpeiseplanDatabase.getSpeiseplan(request.getDayOfYear());
            byte[] response = encoder.encode(new SpeiseplanResponse(request.getDayOfYear(), plan));
            
            // prepare length header
            ByteBuffer outputBuffer = ByteBuffer.allocate(4 + response.length);
            outputBuffer.putInt(response.length);
            outputBuffer.put(response);
            
            // send data
            out.write(outputBuffer.array());
            
        } catch (IOException e) {
            // can't do anything useful, let finally clean up
        } catch (DecodingException e) {
            // can't do anything useful, let finally clean up
        } catch (EncodingException e) {
            // can't do anything useful, let finally clean up
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                // what the hell, if close() fails there are probably bigger problems
            }
        }
    }
}
