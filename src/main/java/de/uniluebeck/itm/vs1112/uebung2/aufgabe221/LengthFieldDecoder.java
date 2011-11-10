package de.uniluebeck.itm.vs1112.uebung2.aufgabe221;

import de.uniluebeck.itm.vs1112.uebung2.Decoder;
import de.uniluebeck.itm.vs1112.uebung2.DecodingException;

import java.lang.reflect.Array;
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

		// TODO implement decoding and add decoded elements to list

		return decodedElements.toArray((T[]) Array.newInstance(clazz, decodedElements.size()));
	}
}
