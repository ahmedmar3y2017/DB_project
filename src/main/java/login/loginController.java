package login;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


public class loginController implements Initializable{
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
    private Button sign_up_button;

    @FXML
    void sign_in_button_action(ActionEvent event) {

    }

    @FXML
    void sign_up_button_action(ActionEvent event) {

    }

    ObservableList<String> users = FXCollections.observableArrayList("customer", "admin");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        type_sign_in.setItems(users);
    }

}






