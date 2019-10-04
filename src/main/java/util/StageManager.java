package util;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
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
    double xOffSet = 0;
    double yOffSet = 0;

    public void setLoader(FXMLLoader loader) {
        this.loader = loader;
    }

    public void switchScene(FXMLView view) throws IOException {
        prepareScene(view);
        primaryStage.show();
    }

    public Stage getPrimaryStage() {
        return this.primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void prepareScene(FXMLView view) throws IOException {
        String fxmlFile = view.getFxmlFile();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(fxmlFile)));
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), root);
        root.setOnMousePressed(event -> {
            xOffSet = event.getSceneX();
            yOffSet = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffSet);
            primaryStage.setY(event.getScreenY() - yOffSet);
        });
        fadeTransition.setFromValue(0.5);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
        primaryStage.setScene(new Scene(root));
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
    }

    public void minimize() {
        primaryStage.setIconified(true);
    }
}
