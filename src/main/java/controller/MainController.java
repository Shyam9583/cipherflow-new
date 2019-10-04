package controller;

import bean.UserBean;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.EFile;
import model.SavedFile;
import model.SavedFileList;
import util.ListPreferences;
import util.StageManager;
import util.UserPreferences;
import view.FXMLView;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    static ObservableList<EFile> eFileObservableList;
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
    private ListPreferences listPreferences;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userBean = UserBean.INSTANCE;
        stageManager = StageManager.INSTANCE;
        userPreferences = UserPreferences.INSTANCE;
        listPreferences = ListPreferences.INSTANCE;
        savedToObservable();
        pageName.setText(userBean.getFirstName() + "'s " + FXMLView.DASHBOARD.getTitle());
        try {
            setContent(FXMLView.DASHBOARD);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void savedToObservable() {
        List<EFile> eFiles = new ArrayList<>();
        userBean.getFileList().getFiles().forEach(item -> eFiles.add(new EFile(item.getSavedFilePath(), item.getSavedLastEncrypted())));
        eFileObservableList = FXCollections.observableList(eFiles);
    }

    private void setContent(FXMLView view) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(view.getFxmlFile())));
        content.getChildren().setAll(parent);
    }

    public void closeWindow(ActionEvent actionEvent) {
        saveList();
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
        saveList();
        userPreferences.removeUserID();
        stageManager.switchScene(FXMLView.LOGIN);
    }

    private void saveList() {
        eFileObservableList = DashboardController.observableList;
        ArrayList<SavedFile> savedFiles = new ArrayList<>();
        eFileObservableList.forEach(item -> {
            try {
                savedFiles.add(new SavedFile(item.getFilePath(), item.getLastEncrypted()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        userBean.setFileList(new SavedFileList(savedFiles));
        listPreferences.setList(userBean.getFileList());
    }
}
