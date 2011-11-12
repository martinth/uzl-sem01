package de.uniluebeck.itm.vs1112.uebung2.aufgabe222;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.uniluebeck.itm.vs1112.uebung2.Decoder;
import de.uniluebeck.itm.vs1112.uebung2.DecodingException;

public class DleStxEtxDecoder<T> implements Decoder<T[]> {

	private Decoder<T> elementDecoder;
	private static enum STATES {
	    outsideFrame, startDLE, startSTX, inFrame, potEndDLE
	};
	private STATES state;
	T type;

	public DleStxEtxDecoder(final Decoder<T> elementDecoder) {
		this.elementDecoder = elementDecoder;
	}

	@SuppressWarnings("unchecked")
    public T[] decode(final byte[] bytes) throws DecodingException {
	    
	    final ArrayList<T> decodedElements = new ArrayList<T>();
	    ByteArrayInputStream input = new ByteArrayInputStream(bytes);

	    ByteArrayOutputStream temp = new ByteArrayOutputStream();
	    state = STATES.outsideFrame;
	    while (input.available() > 0) {
	        
	        switch (state) {
                case outsideFrame:
                    if (input.read() == DleStxEtxConstants.DLE) {
                        state = STATES.startDLE;
                    }
                    // else: stay in this state
                    break;  
                case startDLE:
                    if (input.read() == DleStxEtxConstants.STX) {
                        state = STATES.inFrame;
                    } else {
                        state = STATES.outsideFrame;
                    }
                    break;
                case inFrame:
                    byte b1 = (byte) input.read();
                    if (b1 == DleStxEtxConstants.DLE) {
                        state = STATES.potEndDLE;
                    } else {
                        temp.write(b1);
                        state = STATES.inFrame;
                    }
                    break;
                case potEndDLE:
                    byte b2 = (byte) input.read();
                    if (b2 == DleStxEtxConstants.DLE) {
                        temp.write(b2);
                        state = STATES.inFrame;
                    } else if (b2 == DleStxEtxConstants.ETX) { // frame is complete
                        decodedElements.add( this.elementDecoder.decode(temp.toByteArray()) );
                        temp = new ByteArrayOutputStream();
                        state = STATES.outsideFrame;
                    } else { // error case, void data and return to initial state
                        temp = new ByteArrayOutputStream();
                        state = STATES.outsideFrame;
                    }
                    break;
               
                default:
                    throw new DecodingException("Ivalid state. This should not be possible");
            }  
	    }
	    
	   /* FIXME: hier müsste das array nicht vom Typ Object sein, das geht aber ohne Laufzeitinformationen
	    * über den Typ nicht */
	   //return decodedElements.toArray((T[]) Array.newInstance(Object.class, decodedElements.size()));
	    return null;
	}
	
	
}
