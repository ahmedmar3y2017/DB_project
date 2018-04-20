package mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;

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


    public static BasicDBObject updateproduct(String id, BasicDBObject basicDBObject) {

        BasicDBObject basicDBObject1 = new BasicDBObject();
        basicDBObject1.put("_id", new ObjectId(id));

        BasicDBObject basicDBObjectUpdated = new BasicDBObject();
        basicDBObjectUpdated.put("$set", basicDBObject);

        dbCollection.update(basicDBObject1, basicDBObjectUpdated);

        return basicDBObjectUpdated;


    }

    public static BasicDBObject deleteproduct(String id) {


        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("_id", new ObjectId(id));

        dbCollection.remove(basicDBObject);

        return basicDBObject;

    }


    }

