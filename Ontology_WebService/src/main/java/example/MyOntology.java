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
import java.util.ArrayList;
import java.util.List;


@WebService()
public class MyOntology {
    private static final String namespace = "http://www.semanticweb.org/ontology/SemanticIMDB#";
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
            String sparqlQuery = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                    "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                    "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                    "PREFIX my: <http://www.semanticweb.org/ontology/SemanticIMDB#>\n" +
                    "\n" +
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
                sparqlQuery = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                        "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                        "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                        "PREFIX my: <http://www.semanticweb.org/ontology/SemanticIMDB#>\n" +
                        "\n" +
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
                sparqlQuery = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                        "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                        "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                        "PREFIX my: <http://www.semanticweb.org/ontology/SemanticIMDB#>\n" +
                        "\n" +
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
            sparqlQuery = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                    "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                    "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                    "PREFIX my: <http://www.semanticweb.org/ontology/SemanticIMDB#>\n" +
                    "\n" +
                    "SELECT DISTINCT ?person\n" +
                    "\tWHERE {\n" +
                    "\t\t?subclasse rdfs:subClassOf my:Person.\n" +
                    "\t\t?person rdf:type ?subclasse.\n" +
                    "\t\t?person my:hasName ?name\n";
        }
        else {
            sparqlQuery = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                    "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                    "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                    "PREFIX my: <http://www.semanticweb.org/ontology/SemanticIMDB#>\n" +
                    "\n" +
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
        String sparqlQuery = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "PREFIX my: <http://www.semanticweb.org/ontology/SemanticIMDB#>\n" +
                "\n" +
                "SELECT ?genre\n" +
                "\tWHERE {\n" +
                "\t\tmy:tt0012349 my:hasGenre ?genre.\n" +
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
        String sparqlQuery = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "PREFIX my: <http://www.semanticweb.org/ontology/SemanticIMDB#>\n" +
                "\n" +
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
        String sparqlQuery = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "PREFIX my: <http://www.semanticweb.org/ontology/SemanticIMDB#>\n" +
                "\n" +
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

    public static void main(String[] argv) {
        Object implementor = new MyOntology ();
        String address = "http://localhost:9000/Ontology";
        Endpoint.publish(address, implementor);

        System.out.println("WebService is running...");
    }
}
