package de.uniluebeck.itm.vs1112.uebung2.aufgabe222;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.uniluebeck.itm.vs1112.uebung2.Encoder;
import de.uniluebeck.itm.vs1112.uebung2.EncodingException;

public class DleStxEtxEncoder<T> implements Encoder<T[]> {

	private Encoder<T> elementEncoder;

	public DleStxEtxEncoder(final Encoder<T> elementEncoder) {
		this.elementEncoder = elementEncoder;
	}

	public byte[] encode(final T[] object) throws EncodingException {
	    
	    ByteArrayOutputStream output = new ByteArrayOutputStream();
        
        try {
            for (T t : object) {
                byte[] encoded = this.elementEncoder.encode(t);
                
                output.write(DleStxEtxConstants.DLE_STX);
                for (byte b : encoded) {
                    if (b == DleStxEtxConstants.DLE) {
                        output.write(DleStxEtxConstants.DLE);
                    }
                    output.write(b);
                } 
                output.write(DleStxEtxConstants.DLE_ETX);
            }
        } catch (IOException e) {
            throw new EncodingException(e);
        }
        return output.toByteArray();
	}
}
