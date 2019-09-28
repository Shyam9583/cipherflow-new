package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import util.StageManager;
import view.FXMLView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    public Button closeButton;
    @FXML
    public Button minButton;
    @FXML
    public Button createButton;
    @FXML
    public TextField uidField;
    @FXML
    public PasswordField passField;
    @FXML
    public CheckBox rememberBox;
    @FXML
    public Button signInButton;

    private StageManager stageManager;

    public void closeWindow(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void minimizeWindow(ActionEvent actionEvent) {
        stageManager.minimize();
    }

    public void gotoRegister(ActionEvent actionEvent) throws IOException {
        stageManager.switchScene(FXMLView.LOGIN);
    }

    public void gotoMain(ActionEvent actionEvent) throws IOException {
        stageManager.switchScene(FXMLView.LOGIN);
    }

    public void initialize(URL location, ResourceBundle resources) {
        stageManager = StageManager.INSTANCE;
    }
}
