package data.streaming.utils;

import java.io.IOException;

import org.bson.Document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import data.streaming.dto.TweetDTO;

public class Utils {
	
	
	//public static final String[] TAGNAMES = { "#OTDirecto8D", "#InmaculadaConcepcion" };
	private static final ObjectMapper mapper = new ObjectMapper();

	public static TweetDTO createTweetDTO(String json) {
		TweetDTO result = null;

		try {
			result = mapper.readValue(json, TweetDTO.class);
		} catch (IOException e) {

		}
		return result;
	}

	public static Boolean isValid(String x) {
		Boolean result = true;
		
		if(createTweetDTO(x) == null)
			result = false;
		
		return result;
	}
	
	public static TweetDTO insertBD(TweetDTO t) {
		//MongoClientURI uri = new MongoClientURI("mongodb://jihaneF:jihaneF@ds151355.mlab.com:51355/si1718-jf-conferences");
		MongoClientURI uri = new MongoClientURI("mongodb://jiji:jiji@ds141766.mlab.com:41766/si1718-jf-conferences2");
		MongoClient client = new MongoClient(uri);
		MongoDatabase db = client.getDatabase(uri.getDatabase());
		MongoCollection<Document> batch = db.getCollection("tweets");
		
		Document userData = new Document().append("idStr", t.getUser().getIdStr()).append("name", t.getUser().getName()).append("screenName", t.getUser().getScreenName()).append("friends", t.getUser().getFriends()).append("followers", t.getUser().getFollowers());
		Document tweet = new Document().append("creationDate", t.getCreatedAt()).append("language", t.getLanguage()).append("text", t.getText()).append("userData", userData);
		batch.insertOne(tweet);
		
		client.close();
		
		return t;
	}
	
}
