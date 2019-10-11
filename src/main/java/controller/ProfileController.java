package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    @FXML
    public Label fullNameLabel;
    @FXML
    public Label emailLabel;
    @FXML
    public Label userIDLabel;

    @FXML
    public Button nameChangeButton;
    @FXML
    public Button passwordChangeButton;
    @FXML
    public Button emailChangeButton;

    public void changeName(ActionEvent actionEvent) {
    }

    public void changePassword(ActionEvent actionEvent) {
    }

    public void changeEmail(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
