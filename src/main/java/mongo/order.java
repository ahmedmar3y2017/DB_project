package mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;

import java.util.List;

public class order {

    static DBCollection dbCollection = mongoConnection.getDatabase().getCollection("orders");

    public static BasicDBObject insertorder (BasicDBObject basicDBObject) {

        dbCollection.insert(basicDBObject);
        return basicDBObject;
    }

    // select orders
    public static List<DBObject> selectorders() {
        DBCursor dbObjects = dbCollection.find();
        return dbObjects.toArray();

    }

    public static BasicDBObject deleteorder(String id) {


        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("_id", new ObjectId(id));

        dbCollection.remove(basicDBObject);

        return basicDBObject;

    }
}
