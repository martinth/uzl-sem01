package de.uniluebeck.itm.vs.uebung3.serialization.elementwise;

import de.uniluebeck.itm.vs.uebung3.types.Speise;
import de.uniluebeck.itm.vs.uebung3.types.Speiseplan;
import de.uniluebeck.itm.vs.uebung3.serialization.Decoder;
import de.uniluebeck.itm.vs.uebung3.serialization.DecodingException;
import de.uniluebeck.itm.vs.uebung3.types.SpeiseplanResponse;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;

public class SpeiseplanResponseDecoder implements Decoder<SpeiseplanResponse> {

	public SpeiseplanResponse decode(final byte[] bytes) throws DecodingException {

        if(bytes.length < 4){
            throw new DecodingException("Response must have at least 4 bytes");
        }

        if (bytes[0] != 1){
            throw new DecodingException("Responses are of type 1");
        }

        ArrayList<Speise> speisen = new ArrayList<Speise>();

        int dayOfYear = ((bytes[1] & 0xff) << 8) | (bytes[2] & 0xff);
        if(dayOfYear < 1 || dayOfYear > 366){
            throw new DecodingException("The year has only 366 days.");
        }

        int dishCount = bytes[3] & 0xff;
        if(dishCount < 0 || dishCount > 3){
            throw new DecodingException("Maximum of 3 dishes exceeded");
        }

        int next = 4;
        for(int d = 0; d < dishCount; d++){
            if(bytes.length < next + 4){
                throw new DecodingException("There should be another dish but is not");
            }

            int price = ((bytes[next] & 0xff) << 8) | (bytes[next + 1] & 0xff);
            int descLength = bytes[next + 2] & 0xff;

            if(bytes.length < next + 3 + descLength ){
                throw new DecodingException("Description is shorter than expected description length");
            }

            String description = new String(Arrays.copyOfRange(bytes, next + 3, next + 3 + descLength), Charset.forName("UTF-8"));

            Speise speise = new Speise(description, price);
            speisen.add(speise);
            next = next + 3 + descLength;
        }

        if(bytes.length > next){
            throw new DecodingException("Bytes after end of last description;");
        }

        Speiseplan speiseplan = new Speiseplan(speisen);

        return new SpeiseplanResponse(dayOfYear, speiseplan);
	}
}
