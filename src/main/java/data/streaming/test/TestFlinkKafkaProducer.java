
package data.streaming.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer010;
import org.apache.flink.streaming.connectors.twitter.TwitterSource;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;
import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import data.streaming.aaux.ValidTagsTweetEndpoIntinitializer;
import data.streaming.utils.LoggingFactory;

public class TestFlinkKafkaProducer {

	private static final Integer PARALLELISM = 2;

	public static void main(String... args) throws Exception {

		TwitterSource twitterSource = new TwitterSource(LoggingFactory.getTwitterCredentias());

		// Creating Mongo Client
		//MongoClientURI uri = new MongoClientURI("mongodb://jihaneF:jihaneF@ds151355.mlab.com:51355/si1718-jf-conferences");
		MongoClientURI uri = new MongoClientURI("mongodb://jiji:jiji@ds141766.mlab.com:41766/si1718-jf-conferences2");
		MongoClient client = new MongoClient(uri);
		MongoDatabase db = client.getDatabase(uri.getDatabase());
		MongoCollection<Document> docResearchers = db.getCollection("conferences");
		
		BasicDBObject regexQuery = new BasicDBObject();
		
    	regexQuery.put("keywords.0", new BasicDBObject("$exists", "true"));

		List<Document> documents = (List<Document>) docResearchers.find(regexQuery).into(new ArrayList<Document>());
		
		client.close();
		
		Set<String> tags = new HashSet<String>();
		
		// Recorremos todos los keywords de las conferencias
		
        for(Document document : documents){
        	List<String> keywords = (List<String>) Arrays.asList(((String)document.get("keywords")).split(","));
            if(keywords.size() > 0) {
            	for(String s:keywords) {
	                tags.add(s);
                }
            }
            	   
        }
        
        //Generamos un array con los keywords
        
        String[] tagNames = new String[tags.size()];
        int count = 0;
        for(String s:tags) {
        	tagNames[count] = s;
        	count = count + 1;
        }
	
		// Establecemos el filtro
		twitterSource.setCustomEndpointInitializer(new ValidTagsTweetEndpoIntinitializer(tagNames));

		// set up the execution environment
		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

		env.setParallelism(PARALLELISM);

		// Añadimos la fuente y generamos el stream como la salida de las llamadas
		// asíncronas para salvar los datos en MongoDB
		DataStream<String> stream = env.addSource(twitterSource);

		Properties props = LoggingFactory.getCloudKarafkaCredentials();

		FlinkKafkaProducer010.FlinkKafkaProducer010Configuration<String> config = FlinkKafkaProducer010
				.writeToKafkaWithTimestamps(stream, props.getProperty("CLOUDKARAFKA_TOPIC").trim(), new SimpleStringSchema(),
						props);
		config.setWriteTimestampToKafka(false);
		config.setLogFailuresOnly(false);
		config.setFlushOnCheckpoint(true);

		stream.print();

		env.execute("Twitter Streaming Producer");
	}

}
