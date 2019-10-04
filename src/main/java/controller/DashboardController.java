package controller;

import bean.CipherBean;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import model.EFile;
import util.StageManager;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    static ObservableList<EFile> observableList;
    @FXML
    public Button browseButton;
    @FXML
    public Button decryptButton;
    @FXML
    public Button encryptButton;
    @FXML
    public TableView<EFile> encryptionTable;
    @FXML
    public TableColumn<EFile, String> fileName;
    @FXML
    public TableColumn<EFile, String> fileLocation;
    @FXML
    public TableColumn<EFile, String> encDate;
    @FXML
    public Button decryptTableButton;
    @FXML
    public TextField fileUrlField;
    private CipherBean cipherBean;
    private StageManager stageManager;
    private List<String> observableUrl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cipherBean = CipherBean.INSTANCE;
        stageManager = StageManager.INSTANCE;
        observableList = MainController.eFileObservableList;
        encryptionTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        encryptionTable.setItems(observableList);
    }

    public void decryptSelectionFromTable(ActionEvent actionEvent) {
        List<EFile> selectedFiles = encryptionTable.getSelectionModel().getSelectedItems();
        selectedFiles.forEach(item -> removeFileFromEncryptionList(item.getFilePath()));
    }

    private void removeFileFromEncryptionList(String filePath) {
        observableUrl = new ArrayList<>();
        new ArrayList<>(observableList).forEach(item -> observableUrl.add(item.getFilePath()));
        int idx = observableUrl.indexOf(filePath);
        if (idx != -1) {
            observableList.remove(idx);
        }
    }

    public void browseFiles(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.setTitle("Selected one or more files using <Ctrl>");
        List<File> browsedFiles = fileChooser.showOpenMultipleDialog(stageManager.getPrimaryStage());
        List<String> browsedFilePaths = new ArrayList<>();
        if (browsedFiles != null) {
            browsedFiles.forEach(item -> browsedFilePaths.add(item.getAbsolutePath()));
            StringBuilder stringBuilder = new StringBuilder();
            browsedFilePaths.forEach(item -> stringBuilder.append(item).append(","));
            fileUrlField.setText(stringBuilder.substring(0, stringBuilder.length() - 1));
        }
    }

    public void decryptFiles(ActionEvent actionEvent) {
    }

    public void encryptFiles(ActionEvent actionEvent) {
        String[] urlList = fileUrlField.getText().split(",");
        if (isValidUrlList(urlList)) {
            observableUrl = new ArrayList<>();
            new ArrayList<>(observableList).forEach(item -> observableUrl.add(item.getFilePath()));
            for (String path : urlList) {
                addFileToEncryptionList(path);
            }
        } else showAlert("Please Enter Valid File Path!");
    }

    private void addFileToEncryptionList(String path) {
        if (!observableUrl.contains(path)) {
            File file = new File(path);
            observableList.add(new EFile(file.getAbsolutePath(), new Date(System.currentTimeMillis())));
        }
    }

    private boolean isValidUrlList(String[] strings) {
        for (String string : strings) {
            File file = new File(string);
            if (!file.exists()) return false;
        }
        return true;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("invalid Input");
        alert.setContentText(message);
        alert.showAndWait();

    }
}
