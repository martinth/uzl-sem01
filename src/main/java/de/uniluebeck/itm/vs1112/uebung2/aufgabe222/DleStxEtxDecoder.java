package de.uniluebeck.itm.vs1112.uebung2.aufgabe222;

import de.uniluebeck.itm.vs1112.uebung2.Decoder;
import de.uniluebeck.itm.vs1112.uebung2.DecodingException;

public class DleStxEtxDecoder<T> implements Decoder<T[]> {

	private Decoder<T> elementDecoder;

	public DleStxEtxDecoder(final Decoder<T> elementDecoder) {
		this.elementDecoder = elementDecoder;
	}

	public T[] decode(final byte[] bytes) throws DecodingException {
		return null;  // TODO implement
	}
}
