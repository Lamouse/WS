package Beans;

import Webservice.MyOntology;
import Webservice.MyOntologyService;

import java.util.List;

/**
 * Created by ASUS on 28/11/2015.
 */
public class Media_List {

    public List<String> getMovielist(String category, String genre, String page) {
        MyOntologyService service = new MyOntologyService();
        MyOntology proxy = service.getMyOntologyPort();

        int page1 = 0;
        if(page != null){
            page1 = Integer.parseInt(page);
        }

        List<String> result = proxy.getListMediabyGenre(category, genre, page1);

        return result;
    }

    public String getTitle(String localName) {
        MyOntologyService service = new MyOntologyService();
        MyOntology proxy = service.getMyOntologyPort();
        String result = proxy.getMediaTitle(localName);
        return result;
    }

    public int getDate(String localName) {
        MyOntologyService service = new MyOntologyService();
        MyOntology proxy = service.getMyOntologyPort();
        int result = proxy.getMovieDate(localName);
        return result;
    }

    public String getSeasonDate(String localname) {
        MyOntologyService service = new MyOntologyService();
        MyOntology proxy = service.getMyOntologyPort();
        String result = proxy.getTVShowSeasonDate(localname);
        return result;
    }

    public String getGenre(String localName) {
        MyOntologyService service = new MyOntologyService();
        MyOntology proxy = service.getMyOntologyPort();
        List<String> temp_result = proxy.getMediaGenre(localName);
        return String.join(", ", temp_result);
    }

    public int getRuntime(String localName) {
        MyOntologyService service = new MyOntologyService();
        MyOntology proxy = service.getMyOntologyPort();
        int result = proxy.getMediaRuntime(localName);
        return result;
    }

    public float getRating(String localName) {
        MyOntologyService service = new MyOntologyService();
        MyOntology proxy = service.getMyOntologyPort();
        float result = proxy.getMediaRating(localName);
        return result;
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
}
