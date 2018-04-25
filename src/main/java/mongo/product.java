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

    public static DBObject selectby_productId(String id) {

        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("_id", new ObjectId(id));

        DBObject dbObjectRetrieved = dbCollection.findOne(basicDBObject);

        return dbObjectRetrieved;

    }

    public static List<DBObject> selectproduct_byname(String name) {
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("name", name);
        DBCursor cursor = dbCollection.find(searchQuery);
        List<DBObject> retreived = null;

        while (cursor.hasNext()) {
            retreived = cursor.toArray();
        }
      return retreived;

    }

    public static List<DBObject> selectproductby_subblierid(String id) {
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("product_subblier_id", id);
        DBCursor cursor = dbCollection.find(searchQuery);
        List<DBObject> retreived = null;

        while (cursor.hasNext()) {
            retreived = cursor.toArray();
        }
        return retreived;

    }

    public static List<DBObject> selectproductby_employeeid(String id) {
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("product_employee_id", id);
        DBCursor cursor = dbCollection.find(searchQuery);
        List<DBObject> retreived = null;

        while (cursor.hasNext()) {
            retreived = cursor.toArray();
        }
        return retreived;

    }


    public static List<DBObject> selectproductby_arrdate(String name) {
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put( "arr_date", name);
        DBCursor cursor = dbCollection.find(searchQuery);
        List<DBObject> retreived = null;

        while (cursor.hasNext()) {
            retreived = cursor.toArray();
        }
        return retreived;

    }





    public static List<DBObject> selectproductby_reqdate(String name) {
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("req_date", name);
        DBCursor cursor = dbCollection.find(searchQuery);
        List<DBObject> retreived = null;

        while (cursor.hasNext()) {
            retreived = cursor.toArray();
        }
        return retreived;

    }



}

