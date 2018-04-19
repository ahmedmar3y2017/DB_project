import com.mongodb.BasicDBObject;
import mongo.subblier;

public class mainTest {

    public static void main(String[] args) {

        BasicDBObject basicDBObject=new BasicDBObject();
        basicDBObject.put("name", "Hend");
        basicDBObject.put("address", "ss");
        basicDBObject.put("phone", "123");


        System.out.println(subblier.updateSupplier("5ad8d641368ea6150cb08186",basicDBObject));

        System.out.println(subblier.selectAllSubbliser());

    }

}
