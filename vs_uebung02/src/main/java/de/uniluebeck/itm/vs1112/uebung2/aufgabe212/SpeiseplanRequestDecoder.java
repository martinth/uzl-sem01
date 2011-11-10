package de.uniluebeck.itm.vs1112.uebung2.aufgabe212;

import java.nio.ByteBuffer;

import de.uniluebeck.itm.vs1112.uebung2.Decoder;
import de.uniluebeck.itm.vs1112.uebung2.DecodingException;

public class SpeiseplanRequestDecoder implements Decoder<SpeiseplanRequest> {

	@Override
	public SpeiseplanRequest decode(final byte[] bytes) throws DecodingException {
		
		ByteBuffer buf = ByteBuffer.wrap(bytes);
		
		try {
			if (buf.get() != 0) {
				throw new DecodingException("Request has invalid type (!= 0)");
			}
			short dayOfYear = buf.getShort();
			if (dayOfYear < 0 || dayOfYear > 366) {
				throw new DecodingException("DayOfYear is out of range");
			}
			return new SpeiseplanRequest(dayOfYear);
		} catch (IndexOutOfBoundsException e) {
			throw new DecodingException("Invalid message format", e);
		}
	}
}
