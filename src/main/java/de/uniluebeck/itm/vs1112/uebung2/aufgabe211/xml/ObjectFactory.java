//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.11.10 at 09:20:32 AM CET 
//


package de.uniluebeck.itm.vs1112.uebung2.aufgabe211.xml;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the de.uniluebeck.itm.vs1112.uebung2.aufgabe211.xml package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SpeiseplanRequest_QNAME = new QName("http://itm.uniluebeck.de/vs1112/uebung2/aufgabe211/xml", "speiseplanRequest");
    private final static QName _SpeiseplanResponse_QNAME = new QName("http://itm.uniluebeck.de/vs1112/uebung2/aufgabe211/xml", "speiseplanResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: de.uniluebeck.itm.vs1112.uebung2.aufgabe211.xml
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SpeiseplanResponse }
     * 
     */
    public SpeiseplanResponse createSpeiseplanResponse() {
        return new SpeiseplanResponse();
    }

    /**
     * Create an instance of {@link SpeiseplanRequest }
     * 
     */
    public SpeiseplanRequest createSpeiseplanRequest() {
        return new SpeiseplanRequest();
    }

    /**
     * Create an instance of {@link Speiseplan }
     * 
     */
    public Speiseplan createSpeiseplan() {
        return new Speiseplan();
    }

    /**
     * Create an instance of {@link Speise }
     * 
     */
    public Speise createSpeise() {
        return new Speise();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SpeiseplanRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://itm.uniluebeck.de/vs1112/uebung2/aufgabe211/xml", name = "speiseplanRequest")
    public JAXBElement<SpeiseplanRequest> createSpeiseplanRequest(SpeiseplanRequest value) {
        return new JAXBElement<SpeiseplanRequest>(_SpeiseplanRequest_QNAME, SpeiseplanRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SpeiseplanResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://itm.uniluebeck.de/vs1112/uebung2/aufgabe211/xml", name = "speiseplanResponse")
    public JAXBElement<SpeiseplanResponse> createSpeiseplanResponse(SpeiseplanResponse value) {
        return new JAXBElement<SpeiseplanResponse>(_SpeiseplanResponse_QNAME, SpeiseplanResponse.class, null, value);
    }

}
