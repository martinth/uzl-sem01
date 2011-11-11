package de.uniluebeck.itm.vs1112.uebung2.aufgabe211;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import de.uniluebeck.itm.vs1112.uebung2.Encoder;
import de.uniluebeck.itm.vs1112.uebung2.EncodingException;

/**
 * A JAXBEncoder encodes a serialized object stored in plain old Java objects annotated with JAXB annotations to an XML
 * document. The annotated classes can either be generated from the XML Schema document or manually created and
 * annotated.
 * <p/>
 * Furthermore, a JAXBEncoder is generic in the sense that it can encode to all types of JAXB annotated classes and is
 * not bound to a specific XML Schema.
 *
 * @param <T>
 * 		the type of the deserialized object
 */
public class JAXBEncoder<T> implements Encoder<T> {

	@Override
	public byte[] encode(final T object) throws EncodingException {
		try {
			JAXBContext ctx = JAXBContext.newInstance("de.uniluebeck.itm.vs1112.uebung2.aufgabe211.xml");
			ctx.createMarshaller().marshal(object, System.out);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new byte[0];  // TODO implement
	}
}
