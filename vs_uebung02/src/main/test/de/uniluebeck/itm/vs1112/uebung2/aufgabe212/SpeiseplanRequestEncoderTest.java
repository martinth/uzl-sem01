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

public class SpeiseplanRequestEncoderTest {
	
	private SpeiseplanRequestEncoder enc;
	private SpeiseplanRequestDecoder dec;

	@Before
	public void createEncDec() {
		this.enc = new SpeiseplanRequestEncoder();
		this.dec = new SpeiseplanRequestDecoder();
	}
	
	@Test
	public void testEncode() throws EncodingException {
		SpeiseplanRequest in = new SpeiseplanRequest(1);
		byte[] data = this.enc.encode(in);
		assertArrayEquals(new byte[] {0,0,1}, data);
	}
	
	@Test(expected=EncodingException.class)
	public void testInvalidRangeLow() throws EncodingException {
		SpeiseplanRequest in = new SpeiseplanRequest(-1);
		this.enc.encode(in);
	}
	
	@Test(expected=EncodingException.class)
	public void testInvalidRangeHigh() throws EncodingException {
		SpeiseplanRequest in = new SpeiseplanRequest(367);
		this.enc.encode(in);
	}

}
