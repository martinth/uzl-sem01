package de.uniluebeck.itm.vs1112.uebung2.aufgabe211;

import static org.junit.Assert.*;

import org.junit.Test;

import de.uniluebeck.itm.vs1112.uebung2.EncodingException;
import de.uniluebeck.itm.vs1112.uebung2.aufgabe211.xml.Speiseplan;
import de.uniluebeck.itm.vs1112.uebung2.aufgabe211.xml.SpeiseplanRequest;
import de.uniluebeck.itm.vs1112.uebung2.aufgabe211.xml.SpeiseplanResponse;

public class JAXBEncoderTest {

	@Test
	public void testRequest() throws EncodingException {
		SpeiseplanRequest req = new SpeiseplanRequest();
		req.setDayOfYear(100);
		JAXBEncoder<SpeiseplanRequest> enc = new JAXBEncoder<SpeiseplanRequest>();
		byte[] data = enc.encode(req);
		// die daten validieren können wir leider nicht :(
	}
	
	@Test
	public void testResponse() throws EncodingException {
		SpeiseplanResponse resp = new SpeiseplanResponse();
		resp.setDayOfYear(100);
		Speiseplan plan = new Speiseplan();
		resp.setSpeiseplan(plan);
		JAXBEncoder<SpeiseplanResponse> enc = new JAXBEncoder<SpeiseplanResponse>();
		byte[] data = enc.encode(resp);
		// die daten validieren können wir leider nicht :(
	}
	
	 @Test(expected=EncodingException.class)
	 public void testInvalidType() throws EncodingException {
		JAXBEncoder<String> enc = new JAXBEncoder<String>();
		enc.encode("This should not work");	
	 }

}
