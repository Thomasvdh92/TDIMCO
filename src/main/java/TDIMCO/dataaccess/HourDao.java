package TDIMCO.dataaccess;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * Created by Thomas on 6-4-2018.
 */
public class HourDao {

    public static void main(String[] args) {

        String uri = "mongodb+srv://TDIMCO_admin:P%40ssw0rd@tdimco-wlvuy.mongodb.net/test";

        MongoClientURI mongoClientURI = new MongoClientURI(uri);
        MongoClient mongoClient = new MongoClient(mongoClientURI);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("TDIMCO");
        MongoCollection<Document> collection = mongoDatabase.getCollection("CollectionTest");

        Document document = new Document("Name", "Hour");
        document.append("Size","Five");

        collection.insertOne(document);

    }
}
