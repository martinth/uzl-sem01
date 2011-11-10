package de.uniluebeck.itm.vs1112.uebung2.aufgabe212;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Categories.ExcludeCategory;

import de.uniluebeck.itm.vs1112.uebung2.DecodingException;
import de.uniluebeck.itm.vs1112.uebung2.EncodingException;
import de.uniluebeck.itm.vs1112.uebung2.aufgabe212.SpeiseplanRequest;
import de.uniluebeck.itm.vs1112.uebung2.aufgabe212.SpeiseplanRequestEncoder;

public class SpeiseplanRequestEncodeDecodeTest {
	
	private SpeiseplanRequestEncoder enc;
	private SpeiseplanRequestDecoder dec;

	@Before
	public void createEncDec() {
		this.enc = new SpeiseplanRequestEncoder();
		this.dec = new SpeiseplanRequestDecoder();
	}
	
	@Test
	public void testSimpleInOut() throws EncodingException, DecodingException {
		SpeiseplanRequest in = new SpeiseplanRequest(1);
		byte[] data = this.enc.encode(in);
		SpeiseplanRequest out = this.dec.decode(data);
		
		assertEquals(in.getDayOfYear(), out.getDayOfYear());
	}
}
