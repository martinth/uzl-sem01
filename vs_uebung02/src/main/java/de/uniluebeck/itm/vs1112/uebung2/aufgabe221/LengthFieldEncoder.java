package de.uniluebeck.itm.vs1112.uebung2.aufgabe221;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import de.uniluebeck.itm.vs1112.uebung2.Encoder;
import de.uniluebeck.itm.vs1112.uebung2.EncodingException;

/**
 * The LengthFieldEncoder encodes a set of plain old Java objects (POJOs) into a byte array. Therefore, it
 * serializes the individual elements using the {@link LengthFieldEncoder#elementEncoder} and writes them into the
 * resulting byte array, split by length fields prepending each individual serialized element.
 *
 * @param <T>
 * 		the type of the deserialized objects
 */
public class LengthFieldEncoder<T> implements Encoder<T[]> {

	private final Encoder<T> elementEncoder;
	private static int HEADER_LENGTH = 4;

	public LengthFieldEncoder(final Encoder<T> elementEncoder) {
		this.elementEncoder = elementEncoder;
	}

	public byte[] encode(final T[] object) throws EncodingException {
	    
	    ByteArrayOutputStream output = new ByteArrayOutputStream();
	    
	    try {
    	    for (T t : object) {
                byte[] encoded = this.elementEncoder.encode(t);
                ByteBuffer buf = ByteBuffer.allocate(HEADER_LENGTH + encoded.length);
                buf.putInt(encoded.length);
                buf.put(encoded);
                output.write(buf.array());
            }
	    } catch (IOException e) {
	        throw new EncodingException(e);
	    }
	    return output.toByteArray();
	}
}
