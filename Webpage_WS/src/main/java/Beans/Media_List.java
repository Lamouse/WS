package Beans;

import Webservice.MyOntology;
import Webservice.MyOntologyService;

import java.util.List;


public class Media_List {
    private MyOntologyService service;
    private MyOntology proxy;

    public void setInitialize() {
        service = new MyOntologyService();
        proxy = service.getMyOntologyPort();
    }

    public List<String> getMovielist(String category, String genre, String page) {
        int page1 = 0;
        if(page != null){
            page1 = Integer.parseInt(page);
        }

        List<String> result = proxy.getListMediabyGenre(category, genre, page1);

        return result;
    }

    public String getTitle(String localName) {
        String result = proxy.getMediaTitle(localName);
        return result;
    }

    public int getDate(String localName) {
        int result = proxy.getMovieDate(localName);
        return result;
    }

    public String getSeasonDate(String localname) {
        String result = proxy.getTVShowSeasonDate(localname);
        return result;
    }

    public String getGenre(String localName) {
        List<String> temp_result = proxy.getMediaGenre(localName);
        return String.join(", ", temp_result);
    }

    public int getRuntime(String localName) {
        int result = proxy.getMediaRuntime(localName);
        return result;
    }

    public float getRating(String localName) {
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

    public String getCover(String localName) {
        String result = proxy.getMediaCover(localName);
        return result;
    }

    public String getPlot(String localName) {
        String result = proxy.getMediaPlot(localName);
        return result;
    }

    public String getPersonName(String localName) {
        String result = proxy.getPersonName(localName);
        return result;
    }

    public List<String> getPerson(String kind, String localName) {
        List<String> result = proxy.getMediaPerson(kind, localName);
        return result;
    }

    public int getNumSeason(String localName) {
        int result = proxy.getTVShowNumSeason(localName);
        return result;
    }
}
