package util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.FXMLView;

import java.io.IOException;
import java.util.Objects;


/*
 * Stage manager allows you to change the scene by simply using a fxml view
 */
public enum StageManager {
    INSTANCE;
    FXMLLoader loader;
    Stage primaryStage;

    public void setLoader(FXMLLoader loader) {
        this.loader = loader;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void switchScene(FXMLView view) throws IOException {
        prepareScene(view);
        primaryStage.show();
    }

    private void prepareScene(FXMLView view) throws IOException {
        String fxmlFile = view.getFxmlFile();
        String title = view.getTitle();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(fxmlFile)));
        primaryStage.setTitle(title);
        primaryStage.setScene(new Scene(root));
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
    }
}
