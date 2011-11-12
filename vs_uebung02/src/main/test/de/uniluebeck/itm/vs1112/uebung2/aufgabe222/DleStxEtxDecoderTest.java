package de.uniluebeck.itm.vs1112.uebung2.aufgabe222;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import de.uniluebeck.itm.vs1112.uebung2.DecodingException;
import de.uniluebeck.itm.vs1112.uebung2.EncodingException;
import de.uniluebeck.itm.vs1112.uebung2.aufgabe212.SpeiseplanRequest;
import de.uniluebeck.itm.vs1112.uebung2.aufgabe212.SpeiseplanRequestDecoder;
import de.uniluebeck.itm.vs1112.uebung2.aufgabe212.SpeiseplanRequestEncoder;

public class DleStxEtxDecoderTest {
    
    private static DleStxEtxDecoder<SpeiseplanRequest> dled;

    @BeforeClass
    public static void beforeClass() {
       dled = new DleStxEtxDecoder<SpeiseplanRequest>(new SpeiseplanRequestDecoder());
    }

    @Test
    public void testDecodeEmptyInput() throws DecodingException {
        byte[] data = new byte[] {};
        //FIXME!
        assertEquals(0, dled.decode(data).length);
    }

    @Test
    public void testDecodeSimple() throws DecodingException {
        byte[] data = new byte[] {0x10, 0x2, 0x0, 0x0, 0x1, 0x10, 0x3};
        SpeiseplanRequest[] decoded = dled.decode(data);
        fail("Kann erst nach Klärung der Aufgabenstellung erledigt werden");
    }
    
    @Test
    public void testDecodeStuffed() throws DecodingException {
        byte[] data = new byte[] {0x10, 0x2, 0x0, 0x0, 0x10, 0x10, 0x10, 0x3};
        SpeiseplanRequest[] decoded = dled.decode(data);
        fail("Kann erst nach Klärung der Aufgabenstellung erledigt werden");
    }

}
