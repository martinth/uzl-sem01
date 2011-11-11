package de.uniluebeck.itm.vs1112.uebung2.aufgabe212;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import de.uniluebeck.itm.vs1112.uebung2.Decoder;
import de.uniluebeck.itm.vs1112.uebung2.DecodingException;

public class SpeiseplanResponseDecoder implements Decoder<SpeiseplanResponse> {

	@Override
	public SpeiseplanResponse decode(final byte[] bytes) throws DecodingException {
		
ByteBuffer buf = ByteBuffer.wrap(bytes);
		
		try {
			if (buf.get() != 1) {
				throw new DecodingException("Response has invalid type (!= 1)");
			}
			short dayOfYear = buf.getShort();
			if (dayOfYear < 0 || dayOfYear > 366) {
				throw new DecodingException("DayOfYear is out of range");
			}
			
			byte dishCount = buf.get();
			if (dishCount < 0 || dishCount > 3) {
				throw new DecodingException("DishCount is out of range");
			}
			
			ArrayList<Speise> speisen = new ArrayList<Speise>();
			
			for (int i = 0; i < dishCount; i++) {
				short priceInCent = buf.getShort();
				if (priceInCent < 0) {
					throw new DecodingException("PriceInCent is out of range");
				}
				
				byte descLength = buf.get();
				if (descLength < 0 || descLength > 255) {
					throw new DecodingException("Description has invalid length");
				}
				byte[] msg = new byte[descLength];
				buf.get(msg);
				
				speisen.add(new Speise(new String(msg), priceInCent));
			}
			
			Speiseplan plan = new Speiseplan(speisen);
			return new SpeiseplanResponse(dayOfYear, plan);
			
		} catch (IndexOutOfBoundsException e) {
			throw new DecodingException("Invalid message format", e);
		}
	}
}
