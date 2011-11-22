package de.uniluebeck.itm.vs.uebung3.serialization.elementwise;

import de.uniluebeck.itm.vs.uebung3.types.SpeiseplanRequest;
import de.uniluebeck.itm.vs.uebung3.serialization.Decoder;
import de.uniluebeck.itm.vs.uebung3.serialization.DecodingException;

public class SpeiseplanRequestDecoder implements Decoder<SpeiseplanRequest> {

	public SpeiseplanRequest decode(final byte[] bytes) throws DecodingException {
        if(bytes.length != 3){
            throw new DecodingException("Requests must have a length of 3 bytes");
        }

        byte type = bytes[0];
        if(type != 0){
            throw new DecodingException("Request must have type 0");
        }

        int dayOfYear = ((0xff & bytes[1]) << 8) | (bytes[2] & 0xff);
        if(dayOfYear < 1 || dayOfYear > 366){
            throw new DecodingException("The year has only 366 days");
        }

        return new SpeiseplanRequest(dayOfYear);
    }
}
