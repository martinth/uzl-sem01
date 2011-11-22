package de.uniluebeck.itm.vs.uebung3.apps;

import de.uniluebeck.itm.vs.uebung3.types.SpeiseplanRequest;
import de.uniluebeck.itm.vs.uebung3.types.SpeiseplanResponse;
import de.uniluebeck.itm.vs.uebung3.serialization.elementwise.SpeiseplanRequestEncoder;
import de.uniluebeck.itm.vs.uebung3.serialization.elementwise.SpeiseplanResponseDecoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

public class ClientApp implements Runnable{

    private InputStream c_in;
    private OutputStream c_out;

    private SpeiseplanRequestEncoder request_encoder = new SpeiseplanRequestEncoder();
    private SpeiseplanResponseDecoder response_decoder = new SpeiseplanResponseDecoder();

    public ClientApp(InputStream in, OutputStream out){
        this.c_out = out;
        this.c_in = in;
    }

    public void run() {
        try{
            //Create Request
            Random rand = new Random(System.currentTimeMillis());
            SpeiseplanRequest request = new SpeiseplanRequest(rand.nextInt(367));

            System.out.println("Client (" + Thread.currentThread().getName() + ") sends request: " + request);

            //Encode requests using encoder
            byte[] encoded_request = request_encoder.encode(request);
            byte[] length_bytes = convertIntToByteArray(encoded_request.length);

            long send_time = System.currentTimeMillis();

            //Write length and encoded request to c_out
            c_out.write(length_bytes);
            c_out.write(encoded_request);

            //Wait for Response
            byte[] encoded_response = convertStreamDataToByteArray(c_in);
            long receive_time = System.currentTimeMillis();

            //Decode response
            SpeiseplanResponse response = response_decoder.decode(encoded_response);

            System.out.println("Client (" + Thread.currentThread().getName() +
                    ") received response:" + response + " after " + (receive_time - send_time) + " ms");

            c_out.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private byte[] convertIntToByteArray(int i){
        byte[] result = new byte[4];
        result[0] = (byte)(i >>> 24);
        result[1] = (byte)((i & 0x00ff0000)  >>> 16);
        result[2] = (byte)((i & 0x0000ff00) >>> 8);
        result[3] = (byte)((i & 0x000000ff));

        return result;
    }

    private byte[] convertStreamDataToByteArray(InputStream in) throws IOException {
        byte[] bytes = new byte[4];
        bytes[0] = (byte)in.read();
        bytes[1] = (byte)in.read();
        bytes[2] = (byte)in.read();
        bytes[3] = (byte)in.read();

        int length = ((bytes[0] & 0xff) << 24) |
                     ((bytes[1] & 0xff) << 16) |
                     ((bytes[2] & 0xff) << 8) |
                     (bytes[3] & 0xff);

        byte[] result = new byte[length];
        for(int i = 0; i < length; i++){
            result[i] = (byte)in.read();
        }

        return result;
    }
}
