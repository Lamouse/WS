package Beans;

import Webservice.MyOntology;
import Webservice.MyOntologyService;

import java.util.List;


public class Person_List {
    private MyOntologyService service;
    private MyOntology proxy;

    public void setInitialize() {
        service = new MyOntologyService();
        proxy = service.getMyOntologyPort();
    }

    public List<String> getPersonlist(String kind, String page, String prefix) {
        int page1 = 0;
        if(page != null){
            page1 = Integer.parseInt(page);
        }

        List<String> result = proxy.getListPersonbyKind(kind, page1, prefix);
        return result;
    }

    public String getName(String tempPerson) {
        return proxy.getPersonName(tempPerson);
    }

    public String getJobs(String tempPerson) {
        List<String> temp_result = proxy.getPersonJob(tempPerson);
        return String.join(", ", temp_result);
    }

    public String getBirthDate(String tempPerson) {
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
        return proxy.getPersonPhoto(localName);
    }

    public String getBiography(String localName) {
        return proxy.getPersonBiography(localName);

    }

    public List<String> getMedia(String kind, String localName) {
        List<String> result = proxy.getPersonMedia(kind, localName);
        return result;
    }

    public String getMediaName(String localName) {
        String result = proxy.getMediaTitle(localName);
        return result;
    }
}
