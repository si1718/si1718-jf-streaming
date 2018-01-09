package data.streaming.test;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import data.streaming.dto.ConferenceRating;


public class Scheduling {

	public static void main(String... args) throws Exception {

		Map<String, Integer> map = new HashMap<String, Integer>();

		MongoClientURI uri = new MongoClientURI("mongodb://jiji:jiji@ds141766.mlab.com:41766/si1718-jf-conferences2");
		MongoClient client = new MongoClient(uri);
		MongoDatabase db = client.getDatabase(uri.getDatabase());
		MongoCollection<Document> docResearchers = db.getCollection("conferences");

		BasicDBObject regexQuery1 = new BasicDBObject();

		//regexQuery1.put("keywords.0", new BasicDBObject("$exists", "true"));

		List<Document> conferencesDocuments = (List<Document>) docResearchers.find()
				.into(new ArrayList<Document>());

		client.close();

		Set<String> tags = new HashSet<String>();

		// Recorrer todos las keywords de las conferencias
		for (Document document : conferencesDocuments) {
			List<String> keywords = (List<String>) Arrays.asList(((String) document.get("keywords")).split(","));
			//System.out.println("Las keywords: " + keywords.size());

			if (keywords.size() > 0) {
				for (String s : keywords) {
					tags.add(s);
				}
			}

		}
		
		//System.out.println("Traer las keywords de las conferencias");

		// Generar un array con las keywords

		String[] tagNames = new String[tags.size()];
		int count = 0;
		for (String st : tags) {
			tagNames[count] = st;
			count = count + 1;
		}
		//System.out.println("Array de las keywords");
		//System.out.println(tagNames.length);

		// Iterar las keywords

		client = new MongoClient(uri);

		db = client.getDatabase(uri.getDatabase());

		MongoCollection<Document> tweets = db.getCollection("tweets");

		for (String str : tagNames) {
			BasicDBObject regexQuery = new BasicDBObject();
			regexQuery.put("text", new BasicDBObject("$regex", str).append("$options", "i"));

			List<Document> tweetsDocuments = (List<Document>) tweets.find(regexQuery).into(new ArrayList<Document>());

			Integer numTweets = tweetsDocuments.size();

			map.put(str, numTweets);

		}
		System.out.println("Traer numTweets");

		client.close();

		List<Document> documents = new ArrayList<>();

		// Coger la fecha actual
		Date date = Calendar.getInstance().getTime();

		// Format date
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String today = formatter.format(date);

		for (Map.Entry<String, Integer> entry : map.entrySet()) {

			Document d = new Document().append("keyword", entry.getKey()).append("date", today).append("numTweets",
					entry.getValue());
			documents.add(d);
		}
		System.out.println("Date Format");
		//System.out.println(documents.size());

		// Guardar en la BD

		client = new MongoClient(uri);

		db = client.getDatabase(uri.getDatabase());

		MongoCollection<Document> batch = db.getCollection("batch");
		
		batch.deleteMany(new Document()); 

		batch.insertMany(documents);

		client.close();
		
		//Generar Ratings
		List<Document> ratingsD = new ArrayList<>();
		
		List<Document> ratingsDoc = new ArrayList<Document>();
		for(int i=0;i<conferencesDocuments.size();i++) {
			for(int j=1+i;j<conferencesDocuments.size();j++) {
				Document conference1 = conferencesDocuments.get(i);
				Document conference2 = conferencesDocuments.get(j);
				
				//Coger las keywords de 2 conferencias con las que calculamos el rating
				List<String> keywords1 = (List<String>) Arrays.asList(((String) conference1.get("keywords")).split(","));
				List<String> keywords2 = (List<String>) Arrays.asList(((String) conference2.get("keywords")).split(","));
				
				
				Double rating = 0.0;
				Double ratingNormalizado = 0.0;
				
				//Recorrer la lista de keywords de la primera conferencia
				for(String keyR:keywords1) {
					//Si un elemento(keyR) de las keywords1 coincide con un elemento de las keywords2, sumamos uno al rating.
					if(keywords2.contains(keyR)) {
						rating = rating + 1.0;
					}
				}
				
				//Normalizar el rating
				Double norm = (rating/keywords1.size());
				ratingNormalizado = (norm*4.0)+1.0;
				
				
				System.out.println("------------------------------------------------------");
				System.out.println("Conference 1: " + i + " Conference 2: " + j);
				
				// Transformamos a entero
				Integer ratingFinal = new Integer(ratingNormalizado.intValue());
				
				
				
				String idConference1 = (String) conference1.get("idConference");
				String idConference2 = (String) conference2.get("idConference");
				
				//generamos el documento
				Document docRating = new Document();
				docRating.append("idConference1", idConference1);
				docRating.append("idConference2", idConference2);
				docRating.append("rating", ratingFinal);
				//Anadirlo a la lista de documentos
				ratingsD.add(docRating);
				
			}
		}
		
		client = new MongoClient(uri);

		db = client.getDatabase(uri.getDatabase());
	    
	    MongoCollection<Document> ratings = db.getCollection("ratings");
	
	    Bson filter2 = new Document();
		ratings.deleteMany(filter2);
		System.out.println("------------------------------------------------------");
		System.out.println("Insertamos los ratings");
		System.out.println("Tamaño de los ratings: "+ ratingsD.size());
		ratings.insertMany(ratingsD);
		
		client.close();
				
	}

}

