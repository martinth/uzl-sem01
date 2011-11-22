package de.uniluebeck.itm.vs.uebung3.serialization.elementwise;

import de.uniluebeck.itm.vs.uebung3.types.SpeiseplanRequest;
import de.uniluebeck.itm.vs.uebung3.serialization.Encoder;
import de.uniluebeck.itm.vs.uebung3.serialization.EncodingException;

public class SpeiseplanRequestEncoder implements Encoder<SpeiseplanRequest> {

	public byte[] encode(final SpeiseplanRequest object) throws EncodingException {
        int dayOfYear = object.getDayOfYear();
		if(dayOfYear < 1 || dayOfYear > 366){
            throw new EncodingException("The year has 366 days");
        }
        byte[] result = new byte[3];

        result[0] = 0;
        result[1] = (byte)(dayOfYear >>> 8);
        result[2] = (byte)((dayOfYear << 24) >>> 24);

        return result;
	}
}
