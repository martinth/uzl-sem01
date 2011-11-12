package de.uniluebeck.itm.vs1112.uebung2.aufgabe221;

import de.uniluebeck.itm.vs1112.uebung2.Decoder;
import de.uniluebeck.itm.vs1112.uebung2.DecodingException;

import java.lang.reflect.Array;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

/**
 * The LengthFieldDecoder decodes a byte array into a set of decoded plain old Java objects (POJOs). Therefore, it
 * "splits" the byte array into individual serialized messages which are then passed to the {@link
 * LengthFieldDecoder#elementDecoder} for deserialization.
 *
 * @param <T>
 * 		the type of the deserialized objects
 */
public class LengthFieldDecoder<T> implements Decoder<T[]> {

	private Decoder<T> elementDecoder;

	private final Class<T> clazz;

	public LengthFieldDecoder(final Decoder<T> elementDecoder, final Class<T> clazz) {
		this.elementDecoder = elementDecoder;
		this.clazz = clazz;
	}

	@SuppressWarnings("unchecked")
	public T[] decode(final byte[] bytes) throws DecodingException {

		final List<T> decodedElements = new LinkedList<T>();
		
		
		try {
    		if (bytes.length != 0) { // empty input will result in empty output
    		    
    		    ByteBuffer buf = ByteBuffer.wrap(bytes);
    		    
    		    while (bytes.length - buf.position() > 0) {
    		        /* if there are less than 4 bytes remaining in the buffer, this is an error,
    		         * because even one empty element would have a 4 byte header */
                    if (bytes.length - buf.position() < 4) {
                        throw new DecodingException("Input data has invalid length (<4 bytes)");
                    }
                    byte[] rawData = new byte[buf.getInt()];
                    buf.get(rawData);
                    decodedElements.add( this.elementDecoder.decode(rawData) );
    		    } 
    		}
		} catch (BufferUnderflowException bue) {
		    throw new DecodingException("Input data was shorter than indicated by length header", bue);
		}

		return decodedElements.toArray((T[]) Array.newInstance(clazz, decodedElements.size()));
	}
}
