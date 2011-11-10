package de.uniluebeck.itm.vs1112.uebung2.aufgabe211;

import de.uniluebeck.itm.vs1112.uebung2.Decoder;
import de.uniluebeck.itm.vs1112.uebung2.DecodingException;

/**
 * A JAXBDecoder decodes a serialized object stored in an XML document into plain old Java objects (POJOs) annotated
 * with JAXB annotations. The annotated classes can either be generated from the XML Schema document or manually
 * created and annotated.
 * <p/>
 * Furthermore, a JAXBDecoder is generic in the sense that it can decode to all types of JAXB annotated classes and is
 * not bound to a specific XML Schema.
 *
 * @param <T>
 * 		the type of the deserialized object
 */
public class JAXBDecoder<T> implements Decoder<T> {

	private Class<T> clazz;

	public JAXBDecoder(final Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public T decode(final byte[] bytes) throws DecodingException {
		return null;  // TODO implement
	}
}
