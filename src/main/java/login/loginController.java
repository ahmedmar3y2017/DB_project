package login;

import com.mongodb.BasicDBObject;
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
import java.util.ResourceBundle;


public class loginController implements Initializable {
    @FXML
    private TextField phone_text_sign_in;

    @FXML
    private TextField pass_text_sign_in;

    @FXML
    private ComboBox<String> type_sign_in;

    @FXML
    private Button sign_in_button;

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


    }

    @FXML
    void sign_up_button_action(ActionEvent event) {


        if (name_text_sign_up.getText().trim().isEmpty()
                || phone_text_sign_up.getText().trim().isEmpty()
                || address_text_sign_up.getText().trim().isEmpty()
                || pass_text_sign_up.getText().trim().isEmpty()
                || type_sign_up.getSelectionModel().getSelectedItem().equals(null)

                ) {
            dialog dialog = new dialog(Alert.AlertType.WARNING, "error", "enter all data");

        } else {

            // insert into emp database

            BasicDBObject basicDBObject = new BasicDBObject();
            basicDBObject.put("name", name_text_sign_up.getText());
            basicDBObject.put("address", address_text_sign_up.getText());
            basicDBObject.put("phone", phone_text_sign_up.getText());
            basicDBObject.put("pass", pass_text_sign_up.getText());
            basicDBObject.put("type", type_sign_up.getSelectionModel().getSelectedItem());


            BasicDBObject basicDBObject2 = login.insertuser(basicDBObject);

            dialog dialog = new dialog(Alert.AlertType.CONFIRMATION, "error", "new user added");

        }

        name_text_sign_up.setText("");
        phone_text_sign_up.setText("");
        pass_text_sign_up.setText("");
        address_text_sign_up.setText("");
        type_sign_up.setValue(null);
    }

    ObservableList<String> users = FXCollections.observableArrayList();


        @Override
        public void initialize (URL url, ResourceBundle rb){
            users.add("user");
            users.add("admin");
            type_sign_up.setItems(users);

        }

    }







