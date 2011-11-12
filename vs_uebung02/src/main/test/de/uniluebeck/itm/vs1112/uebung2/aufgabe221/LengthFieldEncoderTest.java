package de.uniluebeck.itm.vs1112.uebung2.aufgabe221;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import de.uniluebeck.itm.vs1112.uebung2.EncodingException;
import de.uniluebeck.itm.vs1112.uebung2.aufgabe212.SpeiseplanRequest;
import de.uniluebeck.itm.vs1112.uebung2.aufgabe212.SpeiseplanRequestEncoder;

public class LengthFieldEncoderTest {
    
    private static LengthFieldEncoder<SpeiseplanRequest> lfe;

    @BeforeClass
    public static void beforeClass() {
       lfe = new LengthFieldEncoder<SpeiseplanRequest>(new SpeiseplanRequestEncoder());
        
        
    }

    @Test
    public void testEncodeEmptyInput() throws EncodingException {
        assertEquals(0, lfe.encode(new SpeiseplanRequest[] {}).length);
    }

    @Test
    public void testTwo() throws EncodingException {
        SpeiseplanRequest r1 = new SpeiseplanRequest(1);
        SpeiseplanRequest r2 = new SpeiseplanRequest(366);
        SpeiseplanRequest[] requests = new SpeiseplanRequest[] { r1, r2 };
        
        for (byte b : lfe.encode(requests)) {
            System.out.printf("0x%x ", b);
        }
        System.out.println();
        
    }
}
