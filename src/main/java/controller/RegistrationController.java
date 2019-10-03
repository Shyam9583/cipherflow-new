package controller;

import bean.CipherBean;
import bean.UserBean;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.EFileList;
import model.User;
import service.UserService;
import service.UserServiceImplimentation;
import util.GeneratorClass;
import util.ListPreferences;
import util.StageManager;
import util.UserPreferences;
import view.FXMLView;

import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable {
    @FXML
    public Button closeButton;
    @FXML
    public Button minButton;
    @FXML
    public Button backButton;
    @FXML
    public TextField uidField;
    @FXML
    public TextField fname;
    @FXML
    public TextField email;
    @FXML
    public TextField lname;
    @FXML
    public PasswordField passField;
    @FXML
    public PasswordField cPassField;
    @FXML
    public Button signUpButton;
    @FXML
    public Button resetButton;
    @FXML
    public Label uidwarning;
    @FXML
    public Label emailwarning;
    @FXML
    public Label lnamewarning;
    @FXML
    public Label fnamewarning;
    @FXML
    public Label cpasswarning;
    @FXML
    public Label passwarning;

    private StageManager stageManager;
    private UserPreferences userPreferences;
    private ListPreferences listPreferences;
    private User user;
    private UserService userService;
    private UserBean userBean;
    private CipherBean cipherBean;

    public void closeWindow(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void minimizeWindow(ActionEvent actionEvent) {
        stageManager.minimize();
    }

    public void gotoLogin(ActionEvent actionEvent) throws IOException {
        stageManager.switchScene(FXMLView.LOGIN);
    }

    public void signUp(ActionEvent actionEvent) throws NoSuchAlgorithmException, NoSuchPaddingException, IOException {
        if (validateInputs()) {
            User savedUser = userService.getUser(uidField.getText());
            if (userService.getUser(uidField.getText()) == null) {
                setUserInformation();
                userService.createUser(user);
                setLocalData();
                stageManager.switchScene(FXMLView.MAIN);
            } else {
                cleanUpWarning();
                uidwarning.setText("userId already exists");
            }
        }
    }

    private void setLocalData() {
        Platform.runLater(() -> {
            user = userService.getUser(uidField.getText());
            userBean.setUserID(user.getUserId());
            userBean.setFirstName(user.getFirstName());
            userBean.setLastName(user.getLastName());
            userBean.setEmail(user.getEmail());
            try {
                cipherBean.setParameters(user.getSecretKey(), user.getIvKey(), user.getSalt());
            } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
                e.printStackTrace();
            }
            userPreferences.setUserID(userBean.getUserID());
            EFileList savedList = listPreferences.getList();
            assert savedList != null;
            if (savedList.getFiles() == null) {
                userBean.setFileList(new EFileList());
            } else userBean.setFileList(savedList);
            try {
                stageManager.switchScene(FXMLView.MAIN);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void setUserInformation() throws NoSuchPaddingException, NoSuchAlgorithmException {
        user.setUserId(uidField.getText());
        user.setFirstName(fname.getText());
        user.setLastName(lname.getText());
        user.setEmail(email.getText());
        user.setSecretKey(GeneratorClass.GenerateKey());
        user.setIvKey(GeneratorClass.GenerateIV());
        user.setSalt(GeneratorClass.GenerateSalt());
        cipherBean.setParameters(user.getSecretKey(), user.getIvKey(), user.getSalt());
        user.setPassword(cipherBean.getSecurePassword(passField.getText()));
    }

    private boolean validateInputs() {
        boolean result = false;
        if (emptyValidation()) {
            return false;
        } else {
            if (inputValidation()) {
                if (!passField.getText().equals(cPassField.getText())) {
                    cleanUpWarning();
                    passwarning.setText("passwords don't match");
                    cpasswarning.setText("passwords don't match");
                } else {
                    result = true;
                }
            }
        }
        return result;
    }

    private boolean inputValidation() {
        if (patternValidation(uidField.getText(), "[A-Za-z0-9]+")) {
            cleanUpWarning();
            uidwarning.setText("only letters and numbers allowed");
            return false;
        }
        if (patternValidation(passField.getText(), "[A-Za-z0-9!@#$%^&*]{8,}")) {
            cleanUpWarning();
            passwarning.setText("size should be 8 or more characters");
            return false;
        }
        if (patternValidation(cPassField.getText(), "[A-Za-z0-9!@#$%^&*]{8,}")) {
            cleanUpWarning();
            cpasswarning.setText("size should be 8 or more characters");
            return false;
        }
        if (patternValidation(fname.getText(), "[A-Za-z]+")) {
            cleanUpWarning();
            fnamewarning.setText("only letters allowed");
            return false;
        }
        if (patternValidation(lname.getText(), "[A-Za-z]+")) {
            cleanUpWarning();
            lnamewarning.setText("only letters allowed");
            return false;
        }
        if (patternValidation(email.getText(), "[^@]+@[^\\.]+\\..+")) {
            cleanUpWarning();
            emailwarning.setText("invalid email id");
            return false;
        }
        return true;
    }

    private boolean patternValidation(String text, String pattern) {
        return !text.matches(pattern);
    }

    private boolean emptyValidation() {

        String uidValues = uidField.getText();
        String passwordValue = passField.getText();
        String cPasswordValue = cPassField.getText();
        String fNameValue = fname.getText();
        String lNameValue = lname.getText();
        String emailValue = email.getText();

        if (uidValues.isEmpty() | uidValues.equals("")) {
            cleanUpWarning();
            uidwarning.setText("Field can't be empty");
            return true;
        }
        if (passwordValue.isEmpty() | passwordValue.equals("")) {
            cleanUpWarning();
            passwarning.setText("Field can't be empty");
            return true;
        }
        if (cPasswordValue.isEmpty() | cPasswordValue.equals("")) {
            cleanUpWarning();
            cpasswarning.setText("Field can't be empty");
            return true;
        }
        if (fNameValue.isEmpty() | fNameValue.equals("")) {
            cleanUpWarning();
            fnamewarning.setText("Field can't be empty");
            return true;
        }
        if (lNameValue.isEmpty() | lNameValue.equals("")) {
            cleanUpWarning();
            lnamewarning.setText("Field can't be empty");
            return true;
        }
        if (emailValue.isEmpty() | emailValue.equals("")) {
            cleanUpWarning();
            emailwarning.setText("Field can't be empty");
            return true;
        }
        return false;
    }

    public void reset(ActionEvent actionEvent) {
        uidField.clear();
        passField.clear();
        cPassField.clear();
        fname.clear();
        lname.clear();
        email.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stageManager = StageManager.INSTANCE;
        userPreferences = UserPreferences.INSTANCE;
        listPreferences = ListPreferences.INSTANCE;
        userBean = UserBean.INSTANCE;
        cipherBean = CipherBean.INSTANCE;
        userService = new UserServiceImplimentation();
        user = new User();
    }

    private void cleanUpWarning() {
        uidwarning.setText("");
        passwarning.setText("");
        cpasswarning.setText("");
        fnamewarning.setText("");
        lnamewarning.setText("");
        emailwarning.setText("");
    }
}
