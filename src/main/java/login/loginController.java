package login;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import dialog.dialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import mongo.employee;
import mongo.login;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class loginController implements Initializable {
    @FXML
    private TextField phone_text_sign_in;

    @FXML
    private TextField pass_text_sign_in;

    @FXML
    private ComboBox<String> type_sign_in;

    @FXML
    private TextField name_text_sign_up;

    @FXML
    private TextField phone_text_sign_up;

    @FXML
    private TextField address_text_sign_up;

    @FXML
    private TextField pass_text_sign_up;

    @FXML
    private ComboBox type_sign_up;


    @FXML
    void sign_in_button_action(ActionEvent event) {

        List<DBObject> list = null;
        List<DBObject> list2 = null;


        if (phone_text_sign_in.getText().trim().isEmpty()
                || pass_text_sign_in.getText().trim().isEmpty()
                || type_sign_in.getSelectionModel().getSelectedItem().equals(null)
                ) {

            dialog dialog = new dialog(Alert.AlertType.WARNING, "error", "all login data is required");

        } else {
            list = login.selectuser_by_type("user");
            list2 = login.selectuser_by_type("admin");
            // System.out.println(list + "\n " + list2);
        }

        /////////////////////////////////////////////////////////////////////////

        for (int i = 0; i < list.size(); i++) {

            DBObject object = list.get(i);

            if (phone_text_sign_in.getText().equals(object.get("phone"))
                    && pass_text_sign_in.getText().equals(object.get("pass"))
                    && type_sign_in.getSelectionModel().getSelectedItem().equals("user")) {
                System.out.println("user login");
            }
        }

        //////////////////////////////////////////////////////////////////////////

        for (int i = 0; i < list2.size(); i++) {
            DBObject object = list2.get(i);
            if (phone_text_sign_in.getText().equals(object.get("phone"))
                    && pass_text_sign_in.getText().equals(object.get("pass"))
                    && type_sign_in.getSelectionModel().getSelectedItem().equals("admin")) {
                System.out.println("admin login");
            }
        }
        phone_text_sign_in.setText("");
        pass_text_sign_in.setText("");
        type_sign_in.setValue(null);


    }


    @FXML
    void sign_up_button_action(ActionEvent event) {

        List<DBObject> list = login.selectuser_by_type("user");
        List<DBObject> list2 = login.selectuser_by_type("admin");

        // updated

        if (name_text_sign_up.getText().trim().isEmpty()
                || phone_text_sign_up.getText().trim().isEmpty()
                || address_text_sign_up.getText().trim().isEmpty()
                || pass_text_sign_up.getText().trim().isEmpty()
                || type_sign_up.getSelectionModel().getSelectedItem().equals(null)

                ) {
            dialog dialog = new dialog(Alert.AlertType.WARNING, "error", "enter all data");

        } else {
// check phone
            DBObject ObjectUser = login.selectuser_byPhone(phone_text_sign_up.getText());
            if (ObjectUser == null) {


                BasicDBObject basicDBObject = new BasicDBObject();
                basicDBObject.put("name", name_text_sign_up.getText());
                basicDBObject.put("address", address_text_sign_up.getText());
                basicDBObject.put("phone", phone_text_sign_up.getText());
                basicDBObject.put("pass", pass_text_sign_up.getText());
                basicDBObject.put("type", type_sign_up.getSelectionModel().getSelectedItem());


                BasicDBObject basicDBObject2 = login.insertuser(basicDBObject);

                dialog dialog = new dialog(Alert.AlertType.CONFIRMATION, "error", "new user added");

                name_text_sign_up.setText("");
                phone_text_sign_up.setText("");
                pass_text_sign_up.setText("");
                address_text_sign_up.setText("");
                type_sign_up.setValue(null);


            } else {
                // to focus textField
                phone_text_sign_up.requestFocus();
                dialog dialog = new dialog(Alert.AlertType.WARNING, "error", "phone already existed");

            }


        }


    }

    ObservableList<String> users = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        users.add("user");
        users.add("admin");
        type_sign_up.setItems(users);
        type_sign_in.setItems(users);

    }


}







