package controller;

import bean.CipherBean;
import bean.UserBean;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
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
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    public Button dashboard;
    @FXML
    public Button about;
    @FXML
    public Button logout;
    @FXML
    public Button closeButton;
    @FXML
    public Button minButton;
    @FXML
    public Button profile;
    @FXML
    public Button learn;
    @FXML
    public AnchorPane content;
    @FXML
    public Text pageName;

    private StageManager stageManager;
    private UserPreferences userPreferences;
    private UserBean userBean;
    private CipherBean cipherBean;
    private ListPreferences listPreferences;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userBean = UserBean.INSTANCE;
        stageManager = StageManager.INSTANCE;
        userPreferences = UserPreferences.INSTANCE;
        cipherBean = CipherBean.INSTANCE;
        listPreferences = ListPreferences.INSTANCE;
        if (userBean.getUserID() == null) {
            UserService userService = new UserServiceImplimentation();
            User user = userService.getUser(userPreferences.getUserID());
            try {
                setUserInformation(user);
            } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        pageName.setText(userBean.getFirstName() + "'s " + FXMLView.DASHBOARD.getTitle());
        try {
            setContent(FXMLView.DASHBOARD);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setUserInformation(User user) throws NoSuchPaddingException, NoSuchAlgorithmException {
        userBean.setUserID(user.getUserId());
        userBean.setFirstName(user.getFirstName());
        userBean.setLastName(user.getLastName());
        userBean.setEmail(user.getEmail());
        cipherBean.setParameters(user.getSecretKey(), user.getIvKey(), user.getSalt());
        EFileList eFileList = listPreferences.getList();
        if (eFileList == null) {
            userBean.setFileList(new EFileList());
        } else {
            userBean.setFileList(eFileList);
        }
    }

    private void setContent(FXMLView view) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(view.getFxmlFile())));
        content.getChildren().setAll(parent);
    }

    public void closeWindow(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void minimizeWindow(ActionEvent actionEvent) {
        stageManager.minimize();
    }

    public void gotoProfile(ActionEvent actionEvent) throws IOException {
        pageName.setText(userBean.getFirstName() + "'s " + FXMLView.PROFILE.getTitle());
        setContent(FXMLView.PROFILE);
    }

    public void gotoDashboard(ActionEvent actionEvent) throws IOException {
        pageName.setText(userBean.getFirstName() + "'s " + FXMLView.DASHBOARD.getTitle());
        setContent(FXMLView.DASHBOARD);
    }

    public void gotoLearn(ActionEvent actionEvent) throws IOException {
        pageName.setText(userBean.getFirstName() + "'s " + FXMLView.LEARN.getTitle());
        setContent(FXMLView.LEARN);
    }

    public void gotoAbout(ActionEvent actionEvent) throws IOException {
        pageName.setText(userBean.getFirstName() + "'s " + FXMLView.ABOUT.getTitle());
        setContent(FXMLView.ABOUT);
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        userPreferences.removeUserID();
        stageManager.switchScene(FXMLView.LOGIN);
    }
}
