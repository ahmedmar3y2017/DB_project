/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dialog;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.util.Optional;

/**
 *
 * @author programmer
 */
public class confirm_dialog {

    public confirm_dialog(String title, String header, String content) throws IOException {

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        Optional<ButtonType> result = alert.showAndWait();


        if (result.get() == ButtonType.OK) {

        } else {
            // ... user chose CANCEL or closed the dialog
        }

    }
    public static Alert ConfirmDialog(String title, String header, String content){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        return  alert;

    }

}
