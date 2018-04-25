package container;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import login.loginController;

import java.io.IOException;


public class startContainer {

    public startContainer(String id, String name, String type) throws IOException {


        container_controller.Userid = id;
        container_controller.Username = name;
        container_controller.Usertype = type;

        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/Fxml/main_screen.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/styles/tab_style.css").toExternalForm());

        stage.setScene(scene);
        stage.setResizable(false);
        // for fade out
//        stage.initStyle(StageStyle.TRANSPARENT); //Removes window decorations
        scene.setFill(Color.TRANSPARENT); //Makes scene background transparent

        stage.initModality(Modality.APPLICATION_MODAL);

        stage.showAndWait();

    }


}
