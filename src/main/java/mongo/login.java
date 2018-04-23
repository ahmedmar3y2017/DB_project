package mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class login {
    MongoClient mongoClient = new MongoClient("localhost", 27017);


    static DBCollection dbCollection = mongoConnection.getDatabase().getCollection("users");


    // insert into database
    public static BasicDBObject insertuser (BasicDBObject basicDBObject) {

        dbCollection.insert(basicDBObject);
        return basicDBObject;
    }

}