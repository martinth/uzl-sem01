package de.uniluebeck.itm.vs.uebung3.serialization.elementwise;

import de.uniluebeck.itm.vs.uebung3.types.Speise;
import de.uniluebeck.itm.vs.uebung3.types.Speiseplan;
import de.uniluebeck.itm.vs.uebung3.serialization.Encoder;
import de.uniluebeck.itm.vs.uebung3.serialization.EncodingException;
import de.uniluebeck.itm.vs.uebung3.types.SpeiseplanResponse;

import java.util.Collection;
import java.util.Iterator;

public class SpeiseplanResponseEncoder implements Encoder<SpeiseplanResponse> {

	public byte[] encode(final SpeiseplanResponse object) throws EncodingException {

        byte[] result = new byte[]{1};

		int dayOfYear = object.getDayOfYear();
        if(dayOfYear < 1 || dayOfYear > 366){
            throw new EncodingException();
        }

        result = concat(result, new byte[]{((byte)(dayOfYear >>> 8))});
        result = concat(result, new byte[]{(byte)((dayOfYear << 24) >>> 24)});

        Speiseplan speiseplan = object.getSpeiseplan();

        Collection<Speise> speisen = speiseplan.getSpeisen();

        int dishCount = speisen.size();
        if(dishCount < 0 || dishCount > 3){
            throw new EncodingException();
        }

        result = concat(result, new byte[]{((byte) dishCount)});

        Iterator<Speise> it = speisen.iterator();

        while(it.hasNext()){
            Speise speise = it.next();

            int price = speise.getPriceInCent();
            if(price < 0 || price > 255){
                throw new EncodingException();
            }
            result = concat(result, new byte[]{0, (byte)price});

            String description = speise.getDescription();
            int descLength = description.length();
            if(descLength > 255){
                throw new EncodingException();
            }
            result = concat(result, new byte[]{((byte) descLength)});
            result = concat(result, description.getBytes());
        }

        return result;
	}

    public byte[] concat(byte[] a, byte[] b){
        byte[] result = new byte[a.length + b.length];
        for(int i = 0; i < a.length; i++){
            result[i] = a[i];
        }
        for(int i = 0; i < b.length; i++){
            result[a.length + i] = b[i];
        }
        return result;
    }
}
