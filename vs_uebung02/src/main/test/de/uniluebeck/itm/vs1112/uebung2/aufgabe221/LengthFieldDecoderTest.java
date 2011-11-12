package de.uniluebeck.itm.vs1112.uebung2.aufgabe221;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import de.uniluebeck.itm.vs1112.uebung2.DecodingException;
import de.uniluebeck.itm.vs1112.uebung2.EncodingException;
import de.uniluebeck.itm.vs1112.uebung2.aufgabe212.SpeiseplanRequest;
import de.uniluebeck.itm.vs1112.uebung2.aufgabe212.SpeiseplanRequestDecoder;
import de.uniluebeck.itm.vs1112.uebung2.aufgabe212.SpeiseplanRequestEncoder;

public class LengthFieldDecoderTest {

    private static LengthFieldEncoder<SpeiseplanRequest> lfe;
    private static LengthFieldDecoder<SpeiseplanRequest> lfd;

    @BeforeClass
    public static void beforeClass() {
       lfe = new LengthFieldEncoder<SpeiseplanRequest>(new SpeiseplanRequestEncoder());
       lfd = new LengthFieldDecoder<SpeiseplanRequest>(new SpeiseplanRequestDecoder(), SpeiseplanRequest.class);
    }

    @Test
    public void testEncodeEmptyInput() throws EncodingException, DecodingException {
        
        byte[] data = lfe.encode(new SpeiseplanRequest[] {});
        SpeiseplanRequest[] decoded = lfd.decode(data);
        
        assertEquals(0, decoded.length);
       
    }

    @Test
    public void testTwo() throws EncodingException, DecodingException {
        SpeiseplanRequest r1 = new SpeiseplanRequest(1);
        SpeiseplanRequest r2 = new SpeiseplanRequest(366);
        SpeiseplanRequest[] requests = new SpeiseplanRequest[] { r1, r2 };
        
        byte[] data = lfe.encode(requests);
        SpeiseplanRequest[] decoded = lfd.decode(data);
        
        assertEquals(requests[0].getDayOfYear(), decoded[0].getDayOfYear());
        assertEquals(requests[1].getDayOfYear(), decoded[1].getDayOfYear());
    }

}
