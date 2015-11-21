import org.apache.jena.ontology.Individual;
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


    private static void printStatements(OntModel model) {
        // print out the predicate, subject and object of each statement
        StmtIterator iter = model.listStatements();
        while (iter.hasNext()) {
            Statement stmt      = iter.nextStatement();  // get next statement
            Resource  subject   = stmt.getSubject();     // get the subject
            Property  predicate = stmt.getPredicate();   // get the predicate
            RDFNode   object    = stmt.getObject();      // get the object

            System.out.print(subject.toString());
            System.out.print(" " + predicate.toString() + " ");
            if (object instanceof Resource) {
                System.out.print(object.toString());
            } else {
                // object is a literal
                System.out.print(" \"" + object.toString() + "\"");
            }

            System.out.println(" .");
        }
    }

    private static void printClasses(OntModel model) {
        // interaction on existing classes
        Iterator<OntClass> it = model.listClasses();
        OntClass class1 = it.next();
        while (it.hasNext()) {
            if (class1.getLocalName() != null)
                System.out.println(class1.getLocalName());
            class1 = it.next();
        }
    }

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

            String movieID;
            JSONArray temp_array;

            while( iteKeys.hasNext() ) {
                movieID = (String) iteKeys.next();
                subObj = (JSONObject) (obj.get(movieID));

                Individual newMovie = model.createIndividual(namespace+movieID, movie);

                newMovie.addLiteral(hasCover, subObj.get("hasCover"));
                newMovie.addLiteral(hasID, subObj.get("hasID"));
                newMovie.addLiteral(hasPlot, subObj.get("hasPlot"));
                newMovie.addLiteral(hasRating, subObj.get("hasRating"));
                newMovie.addLiteral(hasRuntime, subObj.get("hasRuntime"));
                newMovie.addLiteral(hasTitle, subObj.get("hasTitle"));
                newMovie.addLiteral(hasYear, subObj.get("hasYear"));

                temp_array = subObj.getJSONArray("hasGenre");
                for(Object x : temp_array) {
                    newMovie.addProperty(hasGenre, model.getIndividual(namespace+x));
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void create_tv_shows(String filePath) {
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

            String movieID;
            JSONArray temp_array;

            while( iteKeys.hasNext() ) {
                movieID = (String) iteKeys.next();
                subObj = (JSONObject) (obj.get(movieID));

                Individual newMovie = model.createIndividual(namespace+movieID, tv_show);

                newMovie.addLiteral(hasCover, subObj.get("hasCover"));
                newMovie.addLiteral(hasID, subObj.get("hasID"));
                newMovie.addLiteral(hasPlot, subObj.get("hasPlot"));
                newMovie.addLiteral(hasRating, subObj.get("hasRating"));
                newMovie.addLiteral(hasRuntime, subObj.get("hasRuntime"));
                newMovie.addLiteral(hasTitle, subObj.get("hasTitle"));
                newMovie.addLiteral(hasSeasonYears, subObj.get("hasSeasonYears"));
                newMovie.addLiteral(hasNumberSeason, (subObj.get("hasNumberSeasons") instanceof Integer) ? subObj.get("hasNumberSeasons") : 1);

                temp_array = subObj.getJSONArray("hasGenre");
                for(Object x : temp_array) {
                    newMovie.addProperty(hasGenre, model.getIndividual(namespace+x));
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void create_person(String filePath) {
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

            String personID;
            JSONArray temp_array;

            while( iteKeys.hasNext() ) {
                personID = (String) iteKeys.next();
                subObj = (JSONObject) (obj.get(personID));

                Individual newPerson = null;

                temp_array = subObj.getJSONArray("isWriterIn");
                if(temp_array.length()>0) {
                    newPerson = model.createIndividual(namespace+personID, writer);

                    for (Object x : temp_array) {
                        newPerson.addProperty(isWriterIn, model.getIndividual(namespace + x));
                    }
                }

                temp_array = subObj.getJSONArray("isDirectorIn");
                if(temp_array.length()>0) {
                    if (newPerson == null)
                        newPerson = model.createIndividual(namespace+personID, director);
                    else
                        newPerson.addRDFType(director);

                    for (Object x : temp_array) {
                        newPerson.addProperty(isDirectorIn, model.getIndividual(namespace + x));
                    }
                }

                temp_array = subObj.getJSONArray("isActorIn");
                if(temp_array.length()>0) {
                    if (newPerson == null)
                        newPerson = model.createIndividual(namespace+personID, actor);
                    else
                        newPerson.addRDFType(actor);

                    for (Object x : temp_array) {
                        newPerson.addProperty(isActorIn, model.getIndividual(namespace + x));
                    }
                }

                assert newPerson != null;
                newPerson.addLiteral(hasID, subObj.get("hasID"));
                newPerson.addLiteral(hasName, subObj.get("hasName"));
                newPerson.addLiteral(hasBirthDate, subObj.get("hasBirthDate"));
                newPerson.addLiteral(hasBiography, subObj.get("hasBiography"));
                newPerson.addLiteral(hasPhoto, subObj.get("hasPhoto"));
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

        // json files
        String dic_movies = "dic_movies_popular.json";
        String dic_people = "dic_people_popular.json";
        String dic_tv_shows = "dic_tv_shows_popular.json";

        create_movie(dic_movies);
        create_tv_shows(dic_tv_shows);
        create_person(dic_people);

        // printClasses(model);
        // printStatements(model);

        // finally save the changes

        OutputStream out;
        try {
            out = new FileOutputStream("PopulatedSemanticIMDB.owl");
            model.write(out, "RDF/XML");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
