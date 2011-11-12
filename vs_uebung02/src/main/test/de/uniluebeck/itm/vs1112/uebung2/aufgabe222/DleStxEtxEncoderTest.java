package de.uniluebeck.itm.vs1112.uebung2.aufgabe222;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import de.uniluebeck.itm.vs1112.uebung2.EncodingException;
import de.uniluebeck.itm.vs1112.uebung2.aufgabe212.SpeiseplanRequest;
import de.uniluebeck.itm.vs1112.uebung2.aufgabe212.SpeiseplanRequestEncoder;

public class DleStxEtxEncoderTest {
    
    private static DleStxEtxEncoder<SpeiseplanRequest> dlee;

    @BeforeClass
    public static void beforeClass() {
       dlee = new DleStxEtxEncoder<SpeiseplanRequest>(new SpeiseplanRequestEncoder());
    }

    @Test
    public void testEncodeEmptyInput() throws EncodingException {
        assertEquals(0, dlee.encode(new SpeiseplanRequest[] {}).length);
    }

    @Test
    public void testWithoutInnerDle() throws EncodingException {
        SpeiseplanRequest r1 = new SpeiseplanRequest(1);
        SpeiseplanRequest[] requests = new SpeiseplanRequest[] { r1 };
        byte[] encoded = dlee.encode(requests);
        
        assertEquals(DleStxEtxConstants.DLE, encoded[0]);
        assertEquals(DleStxEtxConstants.STX, encoded[1]);
        
        assertEquals(DleStxEtxConstants.DLE, encoded[encoded.length-2]);
        assertEquals(DleStxEtxConstants.ETX, encoded[encoded.length-1]);

        
        // 0x10, 0x2, 0x0, 0x0, 0x1, 0x10, 0x3, 
    }
    
    @Test
    public void testWithInnerDle() throws EncodingException {
        SpeiseplanRequest r1 = new SpeiseplanRequest(16); // this will result in the encoded data containing a DLE
        SpeiseplanRequest[] requests = new SpeiseplanRequest[] { r1 };
        byte[] encoded = dlee.encode(requests);
        
        /* skip first and last two byte, because we know they contain a valid header/footer */
        for (int i = 2; i < encoded.length-2; i++) {
            if (encoded[i] == DleStxEtxConstants.DLE) {
                assertEquals(DleStxEtxConstants.DLE, encoded[i+1]);
            }
        }
    }

}
