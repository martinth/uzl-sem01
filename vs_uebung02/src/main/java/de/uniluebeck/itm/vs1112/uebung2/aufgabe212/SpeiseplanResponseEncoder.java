package de.uniluebeck.itm.vs1112.uebung2.aufgabe212;

import java.nio.ByteBuffer;

import de.uniluebeck.itm.vs1112.uebung2.Encoder;
import de.uniluebeck.itm.vs1112.uebung2.EncodingException;

public class SpeiseplanResponseEncoder implements Encoder<SpeiseplanResponse> {

	@Override
	public byte[] encode(final SpeiseplanResponse object) throws EncodingException {
		
		ByteBuffer buf = ByteBuffer.allocate(1000); // we overallocate, 1000 bytes are enough for max. 3 meals
		buf.put((byte) 1);
		
		if (object.getDayOfYear() < 1 || object.getDayOfYear() > 366) {
			throw new EncodingException("DayOfYear is out of range");
		}
		buf.putShort((short) object.getDayOfYear());
		
		int dishCount = object.getSpeiseplan().getSpeisen().size();
		if (dishCount < 0 || dishCount > 3) {
			throw new EncodingException("DishCount is out of range");
		}
		buf.put((byte) dishCount);
		
		int i = 0;
		for (Speise speise : object.getSpeiseplan().getSpeisen()) {
			if (speise.getPriceInCent() < 0) {
				throw new EncodingException(String.format(
					"Price in meal %d is out of range", i)
				);
			}
			buf.putShort((short) speise.getPriceInCent());
			
			if (speise.getDescription().length() > 255) {
				throw new EncodingException(String.format(
					"Description in meal %d is to long", i)
				);
			}
			buf.put((byte) speise.getDescription().length());
			buf.put(speise.getDescription().getBytes());
		}
		
		byte[] retBuf = new byte[buf.position()];
		System.arraycopy(buf.array(), 0, retBuf, 0, buf.position());
		
		return retBuf;
	}
}