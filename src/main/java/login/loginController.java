package login;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import container.container_controller;
import container.startContainer;
import dialog.dialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mongo.login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class loginController implements Initializable {
    @FXML
    private TextField phone_text_sign_in;

    @FXML
    private TextField pass_text_sign_in;

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
    void sign_in_button_action(ActionEvent event) throws IOException {

        DBObject object = login.selectuser_by_Phone_password(phone_text_sign_in.getText().toString()
                , pass_text_sign_in.getText().toString());
        //  System.out.println(object);

        if (phone_text_sign_in.getText().trim().isEmpty()
                && pass_text_sign_in.getText().trim().isEmpty()) {
            dialog dialog = new dialog(Alert.AlertType.WARNING, "error", "login data is required");
        } else if (object == null) {
            dialog dialog = new dialog(Alert.AlertType.WARNING, "error", " user not found");

        } else {


//close this stage
            ((Stage) type_sign_up.getScene().getWindow()).close();
//open another Stage
            String Id = object.get("_id").toString();
            String name = object.get("name").toString();
            String type = object.get("type").toString();

            System.out.println(Id+"\n"+name + "\n"+type);



            startContainer startContainer = new startContainer(Id,name,type);


        }


    }


    @FXML
    void sign_up_button_action(ActionEvent event) {


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


    }


}







