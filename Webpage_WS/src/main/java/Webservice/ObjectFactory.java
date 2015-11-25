
package Webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the Webservice package. 
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

    private final static QName _AddIemToLastClicks_QNAME = new QName("http://example/", "addIemToLastClicks");
    private final static QName _GetListOfGenresIn_QNAME = new QName("http://example/", "getListOfGenresIn");
    private final static QName _GetLastClicksResponse_QNAME = new QName("http://example/", "getLastClicksResponse");
    private final static QName _GetListOfGenresInResponse_QNAME = new QName("http://example/", "getListOfGenresInResponse");
    private final static QName _AddIemToLastClicksResponse_QNAME = new QName("http://example/", "addIemToLastClicksResponse");
    private final static QName _GetLastClicks_QNAME = new QName("http://example/", "getLastClicks");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: Webservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetLastClicksResponse }
     * 
     */
    public GetLastClicksResponse createGetLastClicksResponse() {
        return new GetLastClicksResponse();
    }

    /**
     * Create an instance of {@link AddIemToLastClicks }
     * 
     */
    public AddIemToLastClicks createAddIemToLastClicks() {
        return new AddIemToLastClicks();
    }

    /**
     * Create an instance of {@link GetListOfGenresIn }
     * 
     */
    public GetListOfGenresIn createGetListOfGenresIn() {
        return new GetListOfGenresIn();
    }

    /**
     * Create an instance of {@link GetListOfGenresInResponse }
     * 
     */
    public GetListOfGenresInResponse createGetListOfGenresInResponse() {
        return new GetListOfGenresInResponse();
    }

    /**
     * Create an instance of {@link AddIemToLastClicksResponse }
     * 
     */
    public AddIemToLastClicksResponse createAddIemToLastClicksResponse() {
        return new AddIemToLastClicksResponse();
    }

    /**
     * Create an instance of {@link GetLastClicks }
     * 
     */
    public GetLastClicks createGetLastClicks() {
        return new GetLastClicks();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddIemToLastClicks }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://example/", name = "addIemToLastClicks")
    public JAXBElement<AddIemToLastClicks> createAddIemToLastClicks(AddIemToLastClicks value) {
        return new JAXBElement<AddIemToLastClicks>(_AddIemToLastClicks_QNAME, AddIemToLastClicks.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetListOfGenresIn }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://example/", name = "getListOfGenresIn")
    public JAXBElement<GetListOfGenresIn> createGetListOfGenresIn(GetListOfGenresIn value) {
        return new JAXBElement<GetListOfGenresIn>(_GetListOfGenresIn_QNAME, GetListOfGenresIn.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLastClicksResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://example/", name = "getLastClicksResponse")
    public JAXBElement<GetLastClicksResponse> createGetLastClicksResponse(GetLastClicksResponse value) {
        return new JAXBElement<GetLastClicksResponse>(_GetLastClicksResponse_QNAME, GetLastClicksResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetListOfGenresInResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://example/", name = "getListOfGenresInResponse")
    public JAXBElement<GetListOfGenresInResponse> createGetListOfGenresInResponse(GetListOfGenresInResponse value) {
        return new JAXBElement<GetListOfGenresInResponse>(_GetListOfGenresInResponse_QNAME, GetListOfGenresInResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddIemToLastClicksResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://example/", name = "addIemToLastClicksResponse")
    public JAXBElement<AddIemToLastClicksResponse> createAddIemToLastClicksResponse(AddIemToLastClicksResponse value) {
        return new JAXBElement<AddIemToLastClicksResponse>(_AddIemToLastClicksResponse_QNAME, AddIemToLastClicksResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLastClicks }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://example/", name = "getLastClicks")
    public JAXBElement<GetLastClicks> createGetLastClicks(GetLastClicks value) {
        return new JAXBElement<GetLastClicks>(_GetLastClicks_QNAME, GetLastClicks.class, null, value);
    }

}
