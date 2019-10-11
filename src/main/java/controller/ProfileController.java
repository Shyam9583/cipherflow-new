package controller;

import bean.CipherBean;
import bean.UserBean;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.User;
import service.UserService;
import service.UserServiceImplimentation;

import java.net.URL;
import java.util.Optional;
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

    private UserBean userBean;
    private CipherBean cipherBean;
    private UserService userService;

    private StringProperty nameProperty;
    private StringProperty emailProperty;

    public void changeName(ActionEvent actionEvent) {
        String result = showDialog("Full Name", "[A-Za-z]+\\s+[A-Za-z]+");
        if (result != null) {
            if (result.equals("$$invalid$$")) showAlert();
            else {
                User user = userService.getUser(userBean.getUserID());
                nameProperty.set(result);
                String[] subName = result.split(" ");
                userBean.setFirstName(subName[0]);
                userBean.setLastName(subName[1]);
                user.setFirstName(userBean.getFirstName());
                user.setLastName(userBean.getLastName());
                userService.updateUser(user);
            }
        }
    }

    public void changePassword(ActionEvent actionEvent) {
        String result = showDialog("Password", "[A-Za-z0-9!@#$%^&*]{8,}");
        if (result != null) {
            if (result.equals("$$invalid$$")) showAlert();
            else {
                User user = userService.getUser(userBean.getUserID());
                user.setPassword(cipherBean.getSecurePassword(result));
                userService.updateUser(user);
            }
        }
    }

    public void changeEmail(ActionEvent actionEvent) {
        String result = showDialog("Email", "[^@]+@[^\\.]+\\..+");
        if (result != null) {
            if (result.equals("$$invalid$$")) showAlert();
            else {
                User user = userService.getUser(userBean.getUserID());
                emailProperty.set(result);
                userBean.setEmail(result);
                user.setEmail(result);
                userService.updateUser(user);
            }
        }
    }

    private String showDialog(String field, String pattern) {
        Dialog dialog = new TextInputDialog();
        dialog.setTitle("Change " + field);
        dialog.setHeaderText("Enter the new value below");
        dialog.setOnCloseRequest((event) -> dialog.close());

        Optional input = dialog.showAndWait();

        if (input.isPresent()) {
            if (patternValidation(input.toString(), pattern)) {
                return "$$invalid$$";
            } else return (String) input.get();
        } else return null;
    }

    private boolean patternValidation(String text, String pattern) {
        return !text.matches(pattern);
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Input");
        alert.setContentText("the entered value is invalid");
        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userBean = UserBean.INSTANCE;
        cipherBean = CipherBean.INSTANCE;
        userService = new UserServiceImplimentation();
        userIDLabel.setText("@" + userBean.getUserID());
        nameProperty = new SimpleStringProperty(userBean.getFirstName() + " " + userBean.getLastName());
        emailProperty = new SimpleStringProperty(userBean.getEmail());
        fullNameLabel.textProperty().bind(nameProperty);
        emailLabel.textProperty().bind(emailProperty);
    }
}
