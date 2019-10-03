package controller;

import bean.CipherBean;
import bean.UserBean;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import model.EFileList;
import model.User;
import service.UserService;
import service.UserServiceImplimentation;
import util.ListPreferences;
import util.StageManager;
import util.UserPreferences;
import view.FXMLView;

import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
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
    @FXML
    public Label uidwarning;
    @FXML
    public Label passwarning;
    @FXML
    public Label loginwarning;
    @FXML
    public ImageView loading;

    private StageManager stageManager;
    private UserService userService;
    private User user;
    private UserBean userBean;
    private CipherBean cipherBean;
    private ListPreferences listPreferences;
    private UserPreferences userPreferences;

    public void closeWindow(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void minimizeWindow(ActionEvent actionEvent) {
        stageManager.minimize();
    }

    public void gotoRegister(ActionEvent actionEvent) throws IOException {
        stageManager.switchScene(FXMLView.REGISTRATION);
    }

    public void gotoMain(ActionEvent actionEvent) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException {
        String userId = uidField.getText();
        String password = passField.getText();
        boolean remember = rememberBox.isSelected();
        if (emptyValidation(userId, password)) {
            if (authenticate(userId, password)) {
                setUserInfo(remember);
                stageManager.switchScene(FXMLView.MAIN);
            }
        }
    }

    private void setUserInfo(boolean isChecked) {
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
        if (isChecked) {
            userPreferences.setUserID(userBean.getUserID());
        }
        EFileList savedList = listPreferences.getList();
        assert savedList != null;
        if (savedList.getFiles() == null) {
            userBean.setFileList(new EFileList());
        } else userBean.setFileList(savedList);
    }

    private boolean emptyValidation(String userId, String password) {
        if (userId.isEmpty() | userId.equals("")) {
            cleanUpWarning();
            uidwarning.setText("User ID can't be empty");
            return false;
        }
        if (password.isEmpty() | password.equals("")) {
            cleanUpWarning();
            passwarning.setText("Password can't be empty");
            return false;
        }
        return true;
    }

    private boolean authenticate(String uid, String password) throws NoSuchPaddingException, NoSuchAlgorithmException {
        boolean result = false;
        User u = userService.getUser(uid);
        if (u == null) {
            cleanUpWarning();
            loginwarning.setText("UserID is not registered! Please Sign Up !");
        } else {
            cipherBean.setParameters(u.getSecretKey(), u.getIvKey(), u.getSalt());
            if (cipherBean.getSecurePassword(password).equals(u.getPassword())) {
                user = u;
                result = true;
            } else {
                cleanUpWarning();
                loginwarning.setText("Password is incorrect!");
            }
            loading.setImage(null);
        }
        return result;
    }

    public void initialize(URL location, ResourceBundle resources) {
        stageManager = StageManager.INSTANCE;
        userBean = UserBean.INSTANCE;
        cipherBean = CipherBean.INSTANCE;
        listPreferences = ListPreferences.INSTANCE;
        userPreferences = UserPreferences.INSTANCE;
        userService = new UserServiceImplimentation();
        user = new User();
    }

    private void cleanUpWarning() {
        uidwarning.setText("");
        passwarning.setText("");
        loginwarning.setText("");
    }
}
