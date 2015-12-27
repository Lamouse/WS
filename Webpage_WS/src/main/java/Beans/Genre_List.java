package Beans;

import Webservice.MyOntology;
import Webservice.MyOntologyService;

import java.util.List;


public class Genre_List {
    private MyOntologyService service;
    private MyOntology proxy;

    public void setInitialize() {
        service = new MyOntologyService();
        proxy = service.getMyOntologyPort();
    }

    public List<String> getGenrelist(String arg) {
        List<String> result = proxy.getListOfGenresIn(arg);

        return result;
    }

    public String getPageLink(String localName) {
        if(localName.startsWith("tt"))
            return "detail_movie.jsp?movie=";
        else if(localName.startsWith("ts"))
            return "detail_tvshow.jsp?tvshow=";
        return "detail_celebs.jsp?celeb=";
    }

    public void addLastClicks(String localName) {
        proxy.addIemToLastClicks(localName);
    }

    public List<String> getLastClicks() {
        return proxy.getLastClicks();
    }

    public List<String> getRecommendation() {
        return proxy.getRecommendation();
    }

    public List<String> getResults(String q) {
        return proxy.getResults(q);
    }
}
