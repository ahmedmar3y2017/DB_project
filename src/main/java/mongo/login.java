package mongo;

import com.mongodb.*;

import java.util.List;

public class login {
    MongoClient mongoClient = new MongoClient("localhost", 27017);


    static DBCollection dbCollection = mongoConnection.getDatabase().getCollection("users");


    // insert into database
    public static BasicDBObject insertuser (BasicDBObject basicDBObject) {

        dbCollection.insert(basicDBObject);
        return basicDBObject;
    }


    // select All users
    public static List<DBObject> selectusers() {
        DBCursor dbObjects = dbCollection.find();
        return dbObjects.toArray();
    }



    public static List<DBObject> selectuser_by_type(String name) {
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("type", name);
        DBCursor cursor = dbCollection.find(searchQuery);
        List<DBObject> retreived = null;

        while (cursor.hasNext()) {
            retreived = cursor.toArray();
        }
        return retreived;

    }


}