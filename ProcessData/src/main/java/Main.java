import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.*;
import org.apache.jena.rdf.model.Property;
import org.apache.log4j.varia.NullAppender;
import org.json.*;

import java.io.*;
import java.util.Iterator;

public class Main {
    private static final String namespace = "http://www.semanticweb.org/ontology/SemanticIMDB#";
    private static OntModel model;

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
    private static Property hasSerieYears;
    private static Property hasTitle;
    private static Property hasYear;
    // object properties
    private static Property hasGenre;
    private static Property isActorIn;
    private static Property isDirectorIn;
    private static Property isWriterIn;

    private static void create_movie(String filePath) {
        try {
            FileReader reader = new FileReader(filePath);
            BufferedReader br = new BufferedReader(reader);
            String s, result = "";
            while((s = br.readLine()) != null) {
                result += s;
            }
            reader.close();

            JSONObject obj = new JSONObject(result);
            JSONObject subObj;

            Iterator<?> iteKeys = obj.keys();
            Iterator<?> iteSubKeys;

            String movieID;
            String movieProperty;

            while( iteKeys.hasNext() ) {
                movieID = (String) iteKeys.next();
                subObj = (JSONObject) (obj.get(movieID));

                System.out.println(subObj.get("hasTitle"));
                System.out.println(subObj.get("hasYear"));
                System.out.println(subObj.get("hasRuntime"));
                System.out.println(subObj.get("hasRating"));
                System.out.println(subObj.get("hasPlot"));
                System.out.println(subObj.get("hasID"));
                System.out.println(subObj.get("hasCover"));
                System.out.println(subObj.get("hasGenre"));
                /*iteSubKeys = subObj.keys();
                while( iteSubKeys.hasNext() ) {
                    movieProperty = (String)iteSubKeys.next();

                    System.out.println(movieProperty+" "+subObj.get(movieProperty));
                }*/
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        org.apache.log4j.BasicConfigurator.configure(new NullAppender());

        // jena don't support OWL files
        model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        model.read("SemanticIMDB.owl", "RDF/XML");

        // get classes
        OntClass movie = model.getOntClass(namespace + "Movie");
        OntClass person = model.getOntClass(namespace + "Person");
        OntClass tv_show = model.getOntClass(namespace + "TV_Show");

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
        hasSerieYears = model.createProperty(namespace, "hasSerieYears");
        hasTitle = model.createProperty(namespace, "hasTitle");
        hasYear = model.createProperty(namespace, "hasYear");
        // object properties
        hasGenre = model.createProperty(namespace, "hasGenre");
        isActorIn = model.createProperty(namespace, "isActorIn");
        isDirectorIn = model.createProperty(namespace, "isDirectorIn");
        isWriterIn = model.createProperty(namespace, "isWriterIn");

        // json files
        String dic_movies = "dic_movies.json";
        String dic_people = "dic_people.json";
        String dic_tv_shows = "dic_tv_shows.json";

        create_movie(dic_movies);
        //create_person(dic_people, "Person");


        // finally save the changes
        OutputStream out = null;
        try {
            out = new FileOutputStream("PopulatedSemanticIMDB.owl");
            model.write(out, "RDF/XML");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
