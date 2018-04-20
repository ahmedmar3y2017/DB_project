package mongo;


import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;

import java.util.List;

public class employee {
    static DBCollection dbCollection = mongoConnection.getDatabase().getCollection("employees");

    // insert into database
    public static BasicDBObject insertemployee(BasicDBObject basicDBObject) {

        dbCollection.insert(basicDBObject);
        return basicDBObject;
    }


    // select  employees
    public static List<DBObject> selectemployees() {
        DBCursor dbObjects = dbCollection.find();
        return dbObjects.toArray();
    }


    // update employee
    public static BasicDBObject updateemployee(String id, BasicDBObject basicDBObject) {

        BasicDBObject b = new BasicDBObject();
        b.put("_id", new ObjectId(id));

        BasicDBObject bupdated = new BasicDBObject();
        bupdated.put("$set", basicDBObject);

        dbCollection.update(b, bupdated);

        return bupdated;


    }

    public static BasicDBObject deleteemployee(String id) {


        BasicDBObject b = new BasicDBObject();
        b.put("_id", new ObjectId(id));

        dbCollection.remove(b);

        return b;

    }



    }

