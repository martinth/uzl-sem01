package de.uniluebeck.itm.vs1112.uebung2.aufgabe211;

import static org.junit.Assert.*;

import org.junit.Test;

import de.uniluebeck.itm.vs1112.uebung2.EncodingException;
import de.uniluebeck.itm.vs1112.uebung2.aufgabe211.xml.SpeiseplanRequest;

public class JAXBEncoderTest {

	@Test
	public void test() throws EncodingException {
		JAXBEncoder enc = new JAXBEncoder<SpeiseplanRequest>();
		SpeiseplanRequest req = new SpeiseplanRequest();
		req.setDayOfYear(200);
		enc.encode(req);
		
	}

}
