package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public Button dashboard;
    public Button about;
    public Button logout;
    public Button closeButton;
    public Button minButton;
    public Button profile;
    public Button learn;
    public AnchorPane content;
    public Text pageName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void closeWindow(ActionEvent actionEvent) {
    }

    public void minimizeWindow(ActionEvent actionEvent) {
    }

    public void gotoProfile(ActionEvent actionEvent) {
    }

    public void gotoDashboard(ActionEvent actionEvent) {
    }

    public void gotoLearn(ActionEvent actionEvent) {
    }

    public void gotoAbout(ActionEvent actionEvent) {
    }

    public void logout(ActionEvent actionEvent) {
    }
}
