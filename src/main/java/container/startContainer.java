package container;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by ahmed mar3y on 24/04/2018.
 */
public class startContainer {

    public startContainer() throws IOException {

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
