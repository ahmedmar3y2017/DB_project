package mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class mongoConnection {





    public static void main(String[] args) {

        // create Connection
        MongoClient mongoClient = new MongoClient("localhost", 27017);

        // mongo db
        DB database = mongoClient.getDB("myMongoDb");


        mongoClient.getDatabaseNames().forEach(System.out::println);

        // create  Collections
        DBCollection user_collection = database.createCollection("user", null);
        DBCollection employee_collection = database.createCollection("employee", null);
        DBCollection subblier_collection = database.createCollection("subblier", null);
        DBCollection product_collection = database.createCollection("product", null);
        DBCollection oreder_collection = database.createCollection("order", null);



        // insert customer into user
        BasicDBObject customer = new BasicDBObject();
        customer.put("name", "ahmed");
        customer.put("password","987654321");
        customer.put("phone","11111");
        customer.put("address","turkey");
        customer.put("type","customer");
        user_collection.insert(customer);

        // insert admin into user
        BasicDBObject admin = new BasicDBObject();
        admin.put("name", "ali");
        admin.put("password","9370276");
        admin.put("phone","2638549");
        admin.put("address","cairo");
        admin.put("type","admin");
        user_collection.insert(admin);





    }


}
