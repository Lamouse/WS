package example;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.log4j.varia.NullAppender;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.util.*;


@WebService()
public class MyOntology {
    private static final String namespace = "http://www.semanticweb.org/ontology/SemanticIMDB#";
    private static final String sparqlPrefix = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
            "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
            "PREFIX my: <http://www.semanticweb.org/ontology/SemanticIMDB#>\n" +
            "\n";
    private static final int numPage = 5;
    private static ArrayList<String> lastClicks;
    private static OntModel model;

    // classes
    private static OntClass movie;
    private static OntClass tv_show;
    private static OntClass actor;
    private static OntClass director;
    private static OntClass writer;

    // data properties
    private static Property hasBiography;
    private static Property hasBirthDate;
    private static Property hasCover;
    private static Property hasID;
    private static Property hasName;
    private static Property hasNumberSeason;
    private static Property hasPhoto;
    private static Property hasPlot;
    private static Property hasRating;
    private static Property hasRuntime;
    private static Property hasSeasonYears;
    private static Property hasTitle;
    private static Property hasYear;
    // object properties
    private static Property hasGenre;
    private static Property isActorIn;
    private static Property isDirectorIn;
    private static Property isWriterIn;

    public MyOntology() {
        org.apache.log4j.BasicConfigurator.configure(new NullAppender());
        model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
        model.read("PopulatedSemanticIMDB.owl", "RDF/XML");

        // get classes
        movie = model.getOntClass(namespace + "Movie");
        tv_show = model.getOntClass(namespace + "TV_Show");
        actor = model.getOntClass(namespace + "Actor");
        director = model.getOntClass(namespace + "Director");
        writer = model.getOntClass(namespace + "Writer");

        // get properties
        // data properties
        hasBiography = model.createProperty(namespace, "hasBiography");
        hasBirthDate = model.createProperty(namespace, "hasBirthDate");
        hasCover = model.createProperty(namespace, "hasCover");
        hasID = model.createProperty(namespace, "hasID");
        hasName = model.createProperty(namespace, "hasName");
        hasNumberSeason = model.createProperty(namespace, "hasNumberSeason");
        hasPhoto = model.createProperty(namespace, "hasPhoto");
        hasPlot = model.createProperty(namespace, "hasPlot");
        hasRating = model.createProperty(namespace, "hasRating");
        hasRuntime = model.createProperty(namespace, "hasRuntime");
        hasSeasonYears = model.createProperty(namespace, "hasSeasonYears");
        hasTitle = model.createProperty(namespace, "hasTitle");
        hasYear = model.createProperty(namespace, "hasYear");
        // object properties
        hasGenre = model.createProperty(namespace, "hasGenre");
        isActorIn = model.createProperty(namespace, "isActorIn");
        isDirectorIn = model.createProperty(namespace, "isDirectorIn");
        isWriterIn = model.createProperty(namespace, "isWriterIn");

        lastClicks = new ArrayList<String>();
    }

    @WebMethod
    public ArrayList<String> getListOfGenresIn(String item) {
        ArrayList<String> result = new ArrayList<String>();

        if(item.equals("Movie") || item.equals("TV_Show")) {
            String sparqlQuery = sparqlPrefix +
                    "SELECT DISTINCT ?genre\n" +
                    "\tWHERE {\n" +
                    "\t\t?genre rdf:type my:Genre.\n" +
                    "\t\t?genre my:isGenreOf ?item.\n" +
                    "\t\t?item rdf:type my:"+item+".\n" +
                    "\t}\n" +
                    "\tORDER BY ?genre";

            Query query = QueryFactory.create(sparqlQuery);
            QueryExecution qe = QueryExecutionFactory.create(query, model);
            ResultSet results = qe.execSelect();

            while (results.hasNext()) {
                QuerySolution qs = results.nextSolution();
                RDFNode temp = qs.get("genre");
                result.add(temp.asNode().getLocalName());
            }

            qe.close();
        }

        return result;
    }

    @WebMethod
    public ArrayList<String> getListMediabyGenre(String item, String genre, int page) {
        ArrayList<String> result = new ArrayList<String>();
        String sparqlQuery;

        if(item.equals("Movie") || item.equals("TV_Show")) {
            if ("All".equals(genre)) {
                sparqlQuery = sparqlPrefix +
                        "SELECT ?movie\n" +
                        "\tWHERE {\n" +
                        "\t\t?movie rdf:type my:"+item+".\n" +
                        "\t\t?movie my:hasTitle ?title.\n" +
                        "\t\t?movie my:hasRating ?rating.\n" +
                        "\t}\n" +
                        "\tORDER BY DESC(?rating) ?title\n" +
                        "\tOFFSET " + (page*this.numPage) + "\n" +
                        "\tLIMIT "+numPage;
            } else {
                sparqlQuery = sparqlPrefix +
                        "SELECT ?movie\n" +
                        "\tWHERE {\n" +
                        "\t\t?movie rdf:type my:"+item+".\n" +
                        "\t\t?movie my:hasGenre my:" + genre + ".\n" +
                        "\t\t?movie my:hasTitle ?title.\n" +
                        "\t\t?movie my:hasRating ?rating.\n" +
                        "\t}\n" +
                        "\tORDER BY DESC(?rating) ?title\n" +
                        "\tOFFSET " + (page*this.numPage) + "\n" +
                        "\tLIMIT "+numPage;
            }

            Query query = QueryFactory.create(sparqlQuery);
            QueryExecution qe = QueryExecutionFactory.create(query, model);
            ResultSet results = qe.execSelect();

            while (results.hasNext()) {
                QuerySolution qs = results.nextSolution();
                RDFNode temp = qs.get("movie");
                result.add(temp.asNode().getLocalName());
            }

            qe.close();
        }
        return result;
    }

    @WebMethod
    public ArrayList<String> getListPersonbyKind(String kind, int page, String prefix) {
        ArrayList<String> result = new ArrayList<String>();
        String sparqlQuery;

        if ("All".equals(kind)) {
            sparqlQuery = sparqlPrefix +
                    "SELECT DISTINCT ?person\n" +
                    "\tWHERE {\n" +
                    "\t\t?subclasse rdfs:subClassOf my:Person.\n" +
                    "\t\t?person rdf:type ?subclasse.\n" +
                    "\t\t?person my:hasName ?name\n";
        }
        else {
            sparqlQuery = sparqlPrefix +
                    "SELECT DISTINCT ?person\n" +
                    "\tWHERE {\n" +
                    "\t\t?person rdf:type my:"+kind+".\n" +
                    "\t\t?person my:hasName ?name\n";
        }

        if(!"".equals(prefix))
            sparqlQuery += "\t\tFILTER(REGEX(STR(?name), \"^"+prefix+"\")).\n";

        sparqlQuery += "\t}\n" +
                "\tORDER BY ?name\n" +
                "\tOFFSET "+(page*numPage)+"\n" +
                "\tLIMIT "+numPage;

        Query query = QueryFactory.create(sparqlQuery);
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();

        while (results.hasNext()) {
            QuerySolution qs = results.nextSolution();
            RDFNode temp = qs.get("person");
            result.add(temp.asNode().getLocalName());
        }

        qe.close();

        return result;
    }

    @WebMethod
    public ArrayList<String> getMediaGenre(String localname) {
        ArrayList<String> result = new ArrayList<String>();
        String sparqlQuery = sparqlPrefix +
                "SELECT ?genre\n" +
                "\tWHERE {\n" +
                "\t\tmy:"+localname+" my:hasGenre ?genre.\n" +
                "\t}" +
                "\tORDER BY ?genre";

        Query query = QueryFactory.create(sparqlQuery);
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();

        while (results.hasNext()) {
            QuerySolution qs = results.nextSolution();
            RDFNode temp = qs.get("genre");
            result.add(temp.asNode().getLocalName());
        }

        qe.close();

        return result;
    }

    @WebMethod
    public List<String> getMediaPerson(String kind, String localName) {
        ArrayList<String> result = new ArrayList<String>();
        String sparqlQuery = sparqlPrefix +
                "SELECT ?person\n" +
                "\tWHERE {\n" +
                "\t\tmy:"+localName+" my:has"+kind+" ?person.\n" +
                "\t}\n" +
                "\tORDER BY ?person";

        Query query = QueryFactory.create(sparqlQuery);
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();

        while (results.hasNext()) {
            QuerySolution qs = results.nextSolution();
            RDFNode temp = qs.get("person");
            result.add(temp.asNode().getLocalName());
        }

        qe.close();

        return result;
    }

    @WebMethod
    public List<String> getPersonMedia(String kind, String localName) {
        ArrayList<String> result = new ArrayList<String>();
        String sparqlQuery = sparqlPrefix +
                "SELECT ?media\n" +
                "\tWHERE {\n" +
                "\t\tmy:"+localName+" my:is"+kind+"In ?media.\n" +
                "\t\t?media my:hasTitle ?name.\n" +
                "\t}\n" +
                "\tORDER BY ?name";

        Query query = QueryFactory.create(sparqlQuery);
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();

        while (results.hasNext()) {
            QuerySolution qs = results.nextSolution();
            RDFNode temp = qs.get("media");
            result.add(temp.asNode().getLocalName());
        }

        qe.close();

        return result;
    }

    @WebMethod
    public String getMediaTitle(String localname) {
        Individual individual = model.getIndividual(namespace+localname);
        Literal literal = individual.getPropertyValue(hasTitle).asLiteral();
        return literal.getString();
    }

    @WebMethod
    public int getMovieDate(String localname) {
        Individual individual = model.getIndividual(namespace+localname);
        Literal literal = individual.getPropertyValue(hasYear).asLiteral();
        return literal.getInt();
    }

    @WebMethod
    public String getTV_ShowSeasonDate(String localname) {
        Individual individual = model.getIndividual(namespace+localname);
        Literal literal = individual.getPropertyValue(hasSeasonYears).asLiteral();
        return literal.getString();
    }

    @WebMethod
    public int getTVShowNumSeason(String localname) {
        Individual individual = model.getIndividual(namespace+localname);
        Literal literal = individual.getPropertyValue(hasNumberSeason).asLiteral();
        return literal.getInt();
    }

    @WebMethod
    public float getMediaRating(String localname) {
        Individual individual = model.getIndividual(namespace+localname);
        Literal literal = individual.getPropertyValue(hasRating).asLiteral();
        return literal.getFloat();
    }

    @WebMethod
    public int getMediaRuntime(String localname) {
        Individual individual = model.getIndividual(namespace+localname);
        Literal literal = individual.getPropertyValue(hasRuntime).asLiteral();
        return literal.getInt();
    }

    @WebMethod
    public String getMediaPlot(String localname) {
        Individual individual = model.getIndividual(namespace+localname);
        Literal literal = individual.getPropertyValue(hasPlot).asLiteral();
        return literal.getString();
    }

    @WebMethod
    public String getMediaCover(String localname) {
        Individual individual = model.getIndividual(namespace+localname);
        Literal literal = individual.getPropertyValue(hasCover).asLiteral();
        String result = literal.getString();
        if (result.isEmpty())
            result = "sorry.jpg";
        return result;
    }
    @WebMethod
    public String getPersonName(String localname) {
        Individual individual = model.getIndividual(namespace+localname);
        Literal literal = individual.getPropertyValue(hasName).asLiteral();
        return literal.getString();
    }

    @WebMethod
    public String getPersonBirthDate(String localname) {
        Individual individual = model.getIndividual(namespace+localname);
        Literal literal = individual.getPropertyValue(hasBirthDate).asLiteral();
        return literal.getString();
    }

    @WebMethod
    public String getPersonPhoto(String localname) {
        Individual individual = model.getIndividual(namespace+localname);
        Literal literal = individual.getPropertyValue(hasPhoto).asLiteral();
        String result = literal.getString();
        if (result.isEmpty())
            result = "sorry.jpg";
        return result;
    }

    @WebMethod
    public String getPersonBiography(String localname) {
        Individual individual = model.getIndividual(namespace+localname);
        Literal literal = individual.getPropertyValue(hasBiography).asLiteral();
        return literal.getString();
    }

    @WebMethod
    public List<String> getPersonJob(String localname) {
        List<String> result = new ArrayList<String>();

        Individual individual = model.getIndividual(namespace+localname);

        if(individual.hasRDFType(actor))
            result.add("Actor");
        if(individual.hasRDFType(director))
            result.add("Director");
        if(individual.hasRDFType(writer))
            result.add("Writer");

        return result;
    }

    @WebMethod
    public void addIemToLastClicks(String local_name) {
        if(lastClicks.contains(local_name))
            lastClicks.remove(local_name);
        lastClicks.add(local_name);

        while (lastClicks.size() > 30)
            lastClicks.remove(0);
    }

    @WebMethod
    public List<String> getLastClicks() {
        return lastClicks;
    }

    @WebMethod
    public List<String> getResults(String search) {
        List<String> result = new ArrayList<String>();
        List<String> searchClasses = new ArrayList<String>();
        List<String> searchProperty = new ArrayList<String>();
        List<String> searchValues;
        String temp1, temp2;

        //String text = "tv_show \"walkind dead\" actor";
        // 1-parsed word
        List<String> words = getWords(search);
        searchValues = new ArrayList<String>(words);

        // 2-compare with classes
        List<String> temp_classes = getClasses();
        for(String word: words) {
            temp1 = word.toLowerCase();
            for(String cl: temp_classes) {
                temp2 = cl.toLowerCase();
                if(temp1.replace("_"," ").equals(temp2.replace("_", " "))) {
                    searchClasses.add(cl);
                    if(searchValues.contains(word))
                        searchValues.remove(word);
                }
            }
        }
        if(searchClasses.isEmpty()){
            searchClasses = temp_classes;
        }

        // 3-compare with properties
        List<String> temp_properties = getProperties(searchClasses);
        for(String word: words) {
            temp1 = word.toLowerCase();
            for(String cl: temp_properties) {
                temp2 = cl.toLowerCase();
                if(temp2.contains(temp1)) {
                    searchProperty.add(cl);
                    if(searchValues.contains(word))
                        searchValues.remove(word);
                }
            }
        }

        // 4-compare with values and get results
        List<String> temp_individuals = getIndividuals(searchClasses);
        for(String individual : temp_individuals) {
            if(searchIndividual(individual, searchProperty, searchValues))
                result.add(individual);
        }

        // finally in case of multiple results
        // 5-first compare all results, e.g if two person then search if there are a link (media) between them
        // and use the number of links for order
        if (result.size() > 1) {
            result = searchLinksAndSort(result);
        }


        System.out.println(searchClasses);
        System.out.println(searchProperty);
        System.out.println(searchValues);
        System.out.println(result);

        return result;
    }

    private List<String> getWords(String text) {
        List<String> result = new ArrayList<String>();
        String temp = "";

        int start = 0;
        int temp_index;
        int index = text.indexOf("\"", start);
        while(index != -1) {
            temp_index = text.indexOf("\"", index+1);
            if(temp_index == -1)
                break;

            temp += text.substring(start, index);
            result.add(text.substring(index+1, temp_index));

            start = temp_index+1;
            index = text.indexOf("\"", start);
        }
        temp += text.substring(start);

        for (String word: temp.split(" ")) {
            if(!word.isEmpty())
                result.add(word);
        }

        return result;
    }

    private List<String> getClasses() {
        List<String> result = new ArrayList<String>();

        String sparqlQuery = sparqlPrefix +
                "SELECT DISTINCT ?class\n" +
                "WHERE {\n" +
                "\t?class a owl:Class.\n" +
                "\t\tFILTER(REGEX(STR(?class), \"^"+namespace+"\")).\n" +
                "}";

        Query query = QueryFactory.create(sparqlQuery);
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();

        while (results.hasNext()) {
            QuerySolution qs = results.nextSolution();
            RDFNode temp = qs.get("class");
            result.add(temp.asNode().getLocalName());
        }

        qe.close();

        return result;
    }

    private List<String> getProperties(List<String> searchClasses) {
        List<String> result = new ArrayList<String>();

        for(String word : searchClasses) {
            String sparqlQuery = sparqlPrefix +
                    "SELECT DISTINCT ?property\n" +
                    "\tWHERE {\n" +
                    "\t\t?instance a my:"+word+".\n" +
                    "\t\t?instance ?property ?obj.\n" +
                    "\t}";

            Query query = QueryFactory.create(sparqlQuery);
            QueryExecution qe = QueryExecutionFactory.create(query, model);
            ResultSet results = qe.execSelect();

            while (results.hasNext()) {
                QuerySolution qs = results.nextSolution();
                RDFNode temp = qs.get("property");

                if(!result.contains(temp.asNode().getLocalName()))
                    result.add(temp.asNode().getLocalName());
            }

            qe.close();
        }

        return result;
    }

    private List<String> getIndividuals(List<String> searchClasses) {
        List<String> result = new ArrayList<String>();

        for(String word : searchClasses) {
            String sparqlQuery = sparqlPrefix +
                    "SELECT ?instance\n" +
                    "WHERE {\n" +
                    "\t?instance a my:"+word+".\n" +
                    "}";

            Query query = QueryFactory.create(sparqlQuery);
            QueryExecution qe = QueryExecutionFactory.create(query, model);
            ResultSet results = qe.execSelect();

            while (results.hasNext()) {
                QuerySolution qs = results.nextSolution();
                RDFNode temp = qs.get("instance");

                if(!result.contains(temp.asNode().getLocalName()))
                    result.add(temp.asNode().getLocalName());
            }

            qe.close();
        }

        return result;
    }

    private boolean searchIndividulaDataProperty(String individual, List<String> searchProperty, List<String> searchValues) {
        if(individual.startsWith("nm") || individual.startsWith("tt") || individual.startsWith("ts")) {
            String sparqlQuery = sparqlPrefix +
                    "SELECT DISTINCT ?property ?value\n" +
                    "\tWHERE {\n" +
                    "\t\t?property a owl:DatatypeProperty.\n" +
                    "\t\tmy:" + individual + " ?property ?value.\n" +
                    "\t}";

            Query query = QueryFactory.create(sparqlQuery);
            QueryExecution qe = QueryExecutionFactory.create(query, model);
            ResultSet results = qe.execSelect();

            String value;
            while (results.hasNext()) {
                QuerySolution qs = results.nextSolution();
                RDFNode temp2 = qs.get("property");

                // ignore plot and biography
                if (!(temp2.equals(hasPlot) || temp2.equals(hasBiography))) {
                    RDFNode temp = qs.get("value");
                    value = "" + temp.asLiteral().getValue();
                    value = value.toLowerCase().replace("_", " ");
                    for (String searchValue : searchValues) {
                        if (value.contains(searchValue.toLowerCase().replace("_", " ")))
                            return true;
                    }
                }
            }
            qe.close();
        }
        else{
            // Genre
            String value = individual.toLowerCase();
            for (String searchValue : searchValues) {
                if (value.contains(searchValue.toLowerCase()))
                    return true;
            }
        }

        return false;
    }

    private List<String> getObjectProperties(String individual) {
        List<String> result = new ArrayList<String>();

        String sparqlQuery = sparqlPrefix +
                "SELECT DISTINCT ?property ?value\n" +
                "\tWHERE {\n" +
                "\t\t?property a owl:ObjectProperty.\n" +
                "\t\tmy:"+individual+" ?property ?value.\n" +
                "\t}";

        Query query = QueryFactory.create(sparqlQuery);
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();
        while (results.hasNext()) {
            QuerySolution qs = results.nextSolution();
            RDFNode temp = qs.get("value");

            result.add(temp.asNode().getLocalName());
        }
        qe.close();

        return result;
    }

    private boolean searchIndividulaObjectProperty(String individual, List<String> searchProperty, List<String> searchValues) {
        List<String> results = getObjectProperties(individual);

        for(String result : results){
            if(searchIndividulaDataProperty(result, searchProperty, searchValues))
                return true;
        }
        return false;
    }

    private boolean searchIndividual(String individual, List<String> searchProperty, List<String> searchValues) {
        if(searchIndividulaDataProperty(individual, searchProperty, searchValues))
            return true;

        if(searchIndividulaObjectProperty(individual, searchProperty, searchValues))
            return true;

        return false;
    }

    private List<String> searchLinksAndSort(List<String> temp_results) {
        System.out.println("Results1: "+ temp_results);

        List<String> result = new ArrayList<String>();
        TreeMap<String, Integer> hashResults = new TreeMap<String, Integer>();
        HashMap<String, List<String>> listObjectProperties = new HashMap<String, List<String>>();

        // filter results
        for(String temp_result : temp_results) {
            if(temp_result.startsWith("nm") || temp_result.startsWith("tt") || temp_result.startsWith("ts")) {
                hashResults.put(temp_result, 0);
            }
        }

        // get all objectProperties
        Set<String> keys = hashResults.keySet();
        for (String key : keys) {
            listObjectProperties.put(key, getObjectProperties(key));
        }

        // check commons items
        List<String> newResults = new ArrayList<String>();
        List<String> temp_list;
        List<String> temp_list2;
        String[] keysArray = keys.toArray(new String[keys.size()]);
        for(int i=0; i<keysArray.length/2; i++) {
            temp_list = listObjectProperties.get(keysArray[i]);
            for(int y=i+1; y<keysArray.length; y++) {
                temp_list2 = listObjectProperties.get(keysArray[y]);
                for(String tempString : temp_list2) {
                    if(!hashResults.keySet().contains(tempString) && temp_list.contains(tempString)) {
                        hashResults.put(tempString, 0);
                        newResults.add(tempString);
                    }
                }
            }
        }

        // if there are commons items get their object properties
        for(String tempString : newResults) {
            listObjectProperties.put(tempString, getObjectProperties(tempString));
        }

        // finally get the frequency of each item
        keys = hashResults.keySet();
        keysArray = keys.toArray(new String[keys.size()]);
        int count;
        for(int i=0; i<keysArray.length/2; i++) {
            temp_list = listObjectProperties.get(keysArray[i]);
            for(int y=i+1; y<keysArray.length; y++) {
                temp_list2 = listObjectProperties.get(keysArray[y]);
                for(String tempString : temp_list2) {
                    if(temp_list.contains(tempString)) {
                        count = hashResults.containsKey(tempString) ? hashResults.get(tempString) : 0;
                        hashResults.put(tempString, count+1);
                    }
                }
            }
        }

        // return the itens order by frequency
        Map<String, Integer> map = sortByValues(hashResults);

        Iterator ittwo = map.entrySet().iterator();
        while (ittwo.hasNext()) {
            Map.Entry pairs = (Map.Entry)ittwo.next();
            System.out.println(pairs.getKey() + " = " + pairs.getValue());
            result.add((String) pairs.getKey());
            ittwo.remove();
        }

        System.out.println("Results1: "+ result);

        return result;
    }

    private static <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
        Comparator<K> valueComparator =  new Comparator<K>() {
            public int compare(K k1, K k2) {
                int compare = map.get(k2).compareTo(map.get(k1));
                if (compare == 0) return 1;
                else return compare;
            }
        };
        Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }

    public static void main(String[] argv) {
        Object implementor = new MyOntology ();
        String address = "http://localhost:9000/Ontology";
        Endpoint.publish(address, implementor);

        System.out.println("WebService is running...");
    }
}

