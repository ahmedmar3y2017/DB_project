package mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class mongoConnection {

    public static DB database;


    public static MongoClient CreateConnection() {

        MongoClient mongoClient = new MongoClient("localhost", 27017);

        return mongoClient;


    }

    public static DB getDatabase() {

        if (database == null) {
            database = CreateConnection().getDB("myMongoDb");
        }
        return database;
    }




}
