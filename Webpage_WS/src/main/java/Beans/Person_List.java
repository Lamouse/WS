package Beans;

import Webservice.MyOntology;
import Webservice.MyOntologyService;

import java.util.List;

/**
 * Created by ASUS on 28/11/2015.
 */
public class Person_List {

    public List<String> getPersonlist(String kind, String page, String prefix) {
        MyOntologyService service = new MyOntologyService();
        MyOntology proxy = service.getMyOntologyPort();

        int page1 = 0;
        if(page != null){
            page1 = Integer.parseInt(page);
        }

        List<String> result = proxy.getListPersonbyKind(kind, page1, prefix);
        return result;
    }

    public String getName(String tempPerson) {
        MyOntologyService service = new MyOntologyService();
        MyOntology proxy = service.getMyOntologyPort();
        return proxy.getPersonName(tempPerson);
    }

    public String getJobs(String tempPerson) {
        MyOntologyService service = new MyOntologyService();
        MyOntology proxy = service.getMyOntologyPort();
        List<String> temp_result = proxy.getPersonJob(tempPerson);
        return String.join(", ", temp_result);
    }

    public String getBirthDate(String tempPerson) {
        MyOntologyService service = new MyOntologyService();
        MyOntology proxy = service.getMyOntologyPort();
        return proxy.getPersonBirthDate(tempPerson);
    }

    public int decPage(String page) {
        int page1 = 0;
        if(page != null){
            page1 = Integer.parseInt(page);
            page1 -= 1;
        }
        return page1<0 ? 0 : page1;
    }

    public int incPage(String page) {
        int page1 = 0;
        if(page != null){
            page1 = Integer.parseInt(page);
            page1 += 1;
        }
        return page1;
    }

    public String getPhoto(String localName) {
        MyOntologyService service = new MyOntologyService();
        MyOntology proxy = service.getMyOntologyPort();
        return proxy.getPersonPhoto(localName);
    }

    public String getBiography(String localName) {
        MyOntologyService service = new MyOntologyService();
        MyOntology proxy = service.getMyOntologyPort();
        return proxy.getPersonBiography(localName);

    }

    public List<String> getMedia(String kind, String localName) {
        MyOntologyService service = new MyOntologyService();
        MyOntology proxy = service.getMyOntologyPort();
        List<String> result = proxy.getPersonMedia(kind, localName);
        return result;
    }

    public String getMediaName(String localName) {
        MyOntologyService service = new MyOntologyService();
        MyOntology proxy = service.getMyOntologyPort();
        String result = proxy.getMediaTitle(localName);
        return result;
    }
}
