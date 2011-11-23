package de.uniluebeck.itm.vs.uebung3;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import de.uniluebeck.itm.vs.uebung3.apps.SpeiseplanDatabase;
import de.uniluebeck.itm.vs.uebung3.serialization.DecodingException;
import de.uniluebeck.itm.vs.uebung3.serialization.EncodingException;
import de.uniluebeck.itm.vs.uebung3.serialization.elementwise.SpeiseplanRequestDecoder;
import de.uniluebeck.itm.vs.uebung3.serialization.elementwise.SpeiseplanResponseEncoder;
import de.uniluebeck.itm.vs.uebung3.types.Speiseplan;
import de.uniluebeck.itm.vs.uebung3.types.SpeiseplanRequest;
import de.uniluebeck.itm.vs.uebung3.types.SpeiseplanResponse;

/**
 * A helper implementation to server SpeiseplanRequests and their responses.
 * 
 * @author martin
 */
public class SpeiseplanServer {
    private InputStream in;
    private OutputStream out;
    private SpeiseplanRequestDecoder decoder;
    private SpeiseplanResponseEncoder encoder;

    public SpeiseplanServer(InputStream in, OutputStream out) {
        this.in = in;
        this.out = out;

        decoder = new SpeiseplanRequestDecoder();
        encoder = new SpeiseplanResponseEncoder();
    }

    public SpeiseplanServer(Socket connection) throws IOException {
        this(connection.getInputStream(), connection.getOutputStream());
    }

    /**
     * Handles one SpeiseplanRequest and sends a SpeiseplanResponse. This method
     * may block if the underlying communication channel has not enough data in
     * it.
     * 
     * @throws IOException on IOException in the underlying communication
     *             channel
     * @throws DecodingException if the SpeiseplanRequest cannot be decoded
     * @throws EncodingException if the SpeiseplanResponse cannot be encoded
     */
    public void handleOne() throws IOException, DecodingException, EncodingException {

        // read header and determine length
        ByteBuffer buf = ByteBuffer.allocate(4);
        for (int i = 0; i < 4; i++) {
            buf.put((byte) in.read());
        }
        int length = buf.getInt(0);

        // read data
        ByteBuffer inputBuffer = ByteBuffer.allocate(length);
        for (int i = 0; i < length; i++) {
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
    }
}
