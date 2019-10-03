import bean.CipherBean;
import bean.UserBean;
import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
import java.security.NoSuchAlgorithmException;

public class MainApplication extends Application {

    private StageManager stageManager;
    private UserPreferences userPreferences;
    private UserBean userBean;
    private CipherBean cipherBean;
    private ListPreferences listPreferences;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setOnCloseRequest(Event::consume);
        stageManager.setPrimaryStage(primaryStage);
        stageManager.setLoader(new FXMLLoader());
        showInitialScene();
    }

    private void showInitialScene() throws IOException, NoSuchAlgorithmException, NoSuchPaddingException {
        String savedUserId = userPreferences.getUserID();
        if (savedUserId == null) {
            stageManager.switchScene(FXMLView.LOGIN);
        } else {
            stageManager.switchScene(FXMLView.WELCOME);
            setUserInfo();
            stageManager.switchScene(FXMLView.MAIN);
        }
    }

    private void setUserInfo() throws NoSuchPaddingException, NoSuchAlgorithmException {
        UserService userService = new UserServiceImplimentation();
        User user = userService.getUser(userPreferences.getUserID());
        userBean.setUserID(user.getUserId());
        userBean.setFirstName(user.getFirstName());
        userBean.setLastName(user.getLastName());
        userBean.setEmail(user.getEmail());
        cipherBean.setParameters(user.getSecretKey(), user.getIvKey(), user.getSalt());
        EFileList savedList = listPreferences.getList();
        assert savedList != null;
        if (savedList.getFiles() == null) {
            userBean.setFileList(new EFileList());
        } else userBean.setFileList(savedList);
    }


    public void init() {
        stageManager = StageManager.INSTANCE;
        userPreferences = UserPreferences.INSTANCE;
        listPreferences = ListPreferences.INSTANCE;
        listPreferences.setListPreferences();
        userPreferences.setUserPreferences();
        userBean = UserBean.INSTANCE;
        cipherBean = CipherBean.INSTANCE;
    }
}
