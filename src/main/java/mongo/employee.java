package mongo;


import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

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
    }

