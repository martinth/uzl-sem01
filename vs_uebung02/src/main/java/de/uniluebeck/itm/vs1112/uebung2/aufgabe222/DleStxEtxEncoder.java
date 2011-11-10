package de.uniluebeck.itm.vs1112.uebung2.aufgabe222;

import de.uniluebeck.itm.vs1112.uebung2.Encoder;
import de.uniluebeck.itm.vs1112.uebung2.EncodingException;

public class DleStxEtxEncoder<T> implements Encoder<T[]> {

	private Encoder<T> elementEncoder;

	public DleStxEtxEncoder(final Encoder<T> elementEncoder) {
		this.elementEncoder = elementEncoder;
	}

	public byte[] encode(final T[] object) throws EncodingException {
		return new byte[0];  // TODO implement
	}
}
