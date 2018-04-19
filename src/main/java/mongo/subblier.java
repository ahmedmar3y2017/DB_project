package mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

public class subblier {

    static DBCollection dbCollection = mongoConnection.getDatabase().getCollection("subbliers");


    // select All SUpplier
    public static List<DBObject> selectAllSubbliser() {
        DBCursor dbObjects = dbCollection.find();
        return dbObjects.toArray();
    }


    // insert into database
    public static BasicDBObject insertSupplier(BasicDBObject basicDBObject) {

        dbCollection.insert(basicDBObject);
        return basicDBObject;
    }

    // update Method
    public static BasicDBObject updateSupplier(String id, BasicDBObject basicDBObject) {

        BasicDBObject basicDBObject1 = new BasicDBObject();
        basicDBObject1.put("_id", new ObjectId(id));

        BasicDBObject basicDBObjectUpdated = new BasicDBObject();
        basicDBObjectUpdated.put("$set", basicDBObject);

        dbCollection.update(basicDBObject1, basicDBObjectUpdated);

        return basicDBObjectUpdated;


    }

    public static BasicDBObject deleteSupplier(String id) {


        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("_id", new ObjectId(id));

        dbCollection.remove(basicDBObject);

        return basicDBObject;

    }

    // select by Id
    public static DBObject selectSupplierById(String id) {

        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("_id", new ObjectId(id));

        DBObject dbObjectRetrieved = dbCollection.findOne(basicDBObject);

        return dbObjectRetrieved;


    }


    // select by Name
    public static DBObject selectSupplierByName(String name) {

        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("name", name);

        DBObject dbObjectRetrieved = dbCollection.findOne(basicDBObject);

        return dbObjectRetrieved;


    }




}
