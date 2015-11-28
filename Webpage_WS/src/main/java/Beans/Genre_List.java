package Beans;

import Webservice.MyOntology;
import Webservice.MyOntologyService;

import java.util.ArrayList;
import java.util.List;

public class Genre_List {

    public List<String> getGenrelist(String arg) {
        MyOntologyService service = new MyOntologyService();
        MyOntology proxy = service.getMyOntologyPort();

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
        MyOntologyService service = new MyOntologyService();
        MyOntology proxy = service.getMyOntologyPort();
        proxy.addIemToLastClicks(localName);
    }

    public List<String> getLastClicks() {
        MyOntologyService service = new MyOntologyService();
        MyOntology proxy = service.getMyOntologyPort();
        return proxy.getLastClicks();
    }
}
