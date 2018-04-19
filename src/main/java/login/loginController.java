package login;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;


public class loginController implements Initializable{
    @FXML
    public TextField phone_text_sign_in;

    @FXML
    public TextField pass_text_sign_in;

    @FXML
    public ComboBox<String> type_sign_in;

    @FXML
    public Button sign_in_button;

    @FXML
    public TextField name_text_sign_up;

    @FXML
    public TextField phone_text_sign_up;

    @FXML
    public TextField address_text_sign_up;

    @FXML
    public TextField pass_text_sign_up;

    @FXML
    public ComboBox<String> type_sign_up;

    @FXML
    public Button sign_up_button;

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






