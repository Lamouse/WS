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
}
