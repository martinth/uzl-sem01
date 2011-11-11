package de.uniluebeck.itm.vs1112.uebung2.aufgabe211;

import java.io.ByteArrayInputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

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
		try {
			ByteArrayInputStream inStream = new ByteArrayInputStream(bytes);
			JAXBContext ctx = JAXBContext.newInstance("de.uniluebeck.itm.vs1112.uebung2.aufgabe211.xml");
			Object obj = ctx.createUnmarshaller().unmarshal(inStream);
			if (obj.getClass() != this.clazz ) {
				throw new DecodingException("Unmarshalled object had wrong classtype");
			}
			return (T) obj;
		} catch (JAXBException e) {
			throw new DecodingException(e);
		} catch (ClassCastException e) {
			throw new DecodingException(e);
		}
	}
}
