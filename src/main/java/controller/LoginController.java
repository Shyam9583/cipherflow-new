package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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

    public void closeWindow(ActionEvent actionEvent) {
    }

    public void minimizeWindow(ActionEvent actionEvent) {
    }

    public void gotoRegister(ActionEvent actionEvent) {
    }

    public void gotoSignIn(ActionEvent actionEvent) {
    }

    public void initialize(URL location, ResourceBundle resources) {

    }
}
