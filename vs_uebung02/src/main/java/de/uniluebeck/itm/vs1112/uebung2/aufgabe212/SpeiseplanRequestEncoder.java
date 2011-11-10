package de.uniluebeck.itm.vs1112.uebung2.aufgabe212;

import java.nio.ByteBuffer;

import de.uniluebeck.itm.vs1112.uebung2.Encoder;
import de.uniluebeck.itm.vs1112.uebung2.EncodingException;

public class SpeiseplanRequestEncoder implements Encoder<SpeiseplanRequest> {

	@Override
	public byte[] encode(final SpeiseplanRequest object) throws EncodingException {
		
		ByteBuffer buf = ByteBuffer.allocate(3);
		buf.put((byte) 0);
		
		if (object.getDayOfYear() < 1 || object.getDayOfYear() > 366) {
			throw new EncodingException("DayOfYear is out of range");
		}
		buf.putShort((short) object.getDayOfYear());
		
		
		return buf.array();
	}
}