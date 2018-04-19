package dialog;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;

/**
 *
 * @author programmer
 */
public class dialog {

    public dialog(Alert.AlertType type, String title, String content) {

        Alert dialog = new Alert(type);
        dialog.setResizable(false);
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/styles/css.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");
        dialog.setContentText(content);
        dialog.showAndWait();
    }
}
