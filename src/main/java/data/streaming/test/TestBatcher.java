package data.streaming.test;

/*import java.text.DateFormat;
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

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;*/

public class TestBatcher {


	public static void main(String... args) throws Exception {

		/*Map<String, Integer> map = new HashMap<String, Integer>();

		MongoClientURI uri = new MongoClientURI("mongodb://jiji:jiji@ds141766.mlab.com:41766/si1718-jf-conferences2");
		MongoClient client = new MongoClient(uri);
		MongoDatabase db = client.getDatabase(uri.getDatabase());
		MongoCollection<Document> docResearchers = db.getCollection("conferences");
		MongoCollection<Document> tweets = db.getCollection("tweets");
		MongoCollection<Document> batch = db.getCollection("batch");

		List<Document> conferencesDocuments = (List<Document>) docResearchers.find().into(new ArrayList<Document>());

		Set<String> tags = new HashSet<String>();

		// Recorremos todos los keywords de las conferencias
		for (Document document : conferencesDocuments) {
			List<String> keywords = (List<String>) Arrays.asList(((String) document.get("keywords")).split(","));
			if (keywords.size() > 0) {

				for (String s : keywords) {
					tags.add(s);
				}
			}

		}

		// Generamos un array con los keywords

		String[] tagNames = new String[tags.size()];
		int contador = 0;
		for (String s : tags) {
			tagNames[contador] = s;
			contador = contador + 1;
		}

		// Iteramos los keywords

		for (String s : tagNames) {
			BasicDBObject regexQuery = new BasicDBObject();
			regexQuery.put("text", new BasicDBObject("$regex", s).append("$options", "i"));

			List<Document> tweetsDocuments = (List<Document>) tweets.find(regexQuery).into(new ArrayList<Document>());

			Integer numTweets = tweetsDocuments.size();
			
			map.put(s, numTweets);

		}

		List<Document> documents = new ArrayList<>();

		// Cogemos la fecha actual
		Date date = Calendar.getInstance().getTime();

		// Display a date in day, month, year format
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String today = formatter.format(date);

		for (Map.Entry<String, Integer> entry : map.entrySet()) {

			Document docu = new Document().append("keyword", entry.getKey()).append("date", today).append("numTweets",
					entry.getValue());
			documents.add(docu);
		}
		// Guardamos en la BD
		batch.insertMany(documents);*/

		
		 Scheduling scheduling = new Scheduling(); 
		// scheduling.beepForAnHour();
		 
	}

}

