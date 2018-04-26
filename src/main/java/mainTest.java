import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import mongo.order;
import mongo.subblier;

import java.text.SimpleDateFormat;
import java.util.Date;

public class mainTest {

    public static void main(String[] args) {
//        BasicDBObject user = new BasicDBObject();
//        user.put("id","5adf1f0dfe4aa93a50f986ed");
//        user.put("name" , "ahmed");
//        user.put("phone" , "123456");
//        user.put("address" ,"tanta");
//
//
//        BasicDBObject basicDBObject = new BasicDBObject();
//        basicDBObject.put("name", "Order1");
//        basicDBObject.put("amount", 50);
//        basicDBObject.put("cost",100);
//        basicDBObject.put("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
//        basicDBObject.put("user_data",user);
//
//
//
//        BasicDBObject basicDBObject2 = order.insertorder(basicDBObject);
//


        // ----- select By User Id -----------
        System.out.println(order.getByUserId("5adf1f0dfe4aa93a50f986ed").size());

                /*.forEach(ee->{
            System.out.println(ee.get("name"));


        });
*/
    }

}
