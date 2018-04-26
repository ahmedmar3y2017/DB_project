package mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;

import java.util.List;

public class order {

    static DBCollection dbCollection = mongoConnection.getDatabase().getCollection("orders");

    public static BasicDBObject insertorder(BasicDBObject basicDBObject) {

        dbCollection.insert(basicDBObject);
        return basicDBObject;
    }

    // select orders
    public static List<DBObject> selectorders() {
        DBCursor dbObjects = dbCollection.find();
        return dbObjects.toArray();

    }

    public static DBCursor getByUserId() {

        BasicDBObject query = new BasicDBObject();
        query.put("user_data.id", new BasicDBObject("$eq", "5adf1f0dfe4aa93a50f986ed"));
        DBCursor cur = dbCollection.find(query);
        return cur;

    }

}
