package mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import java.util.List;

public class product {

    static DBCollection dbCollection = mongoConnection.getDatabase().getCollection("products");


    public static BasicDBObject insertproduct (BasicDBObject basicDBObject) {

        dbCollection.insert(basicDBObject);
        return basicDBObject;
    }
    // select products
    public static List<DBObject> selectproducts() {
        DBCursor dbObjects = dbCollection.find();
        return dbObjects.toArray();
    }


    }

