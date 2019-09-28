import bean.CipherBean;
import bean.UserBean;
import javafx.application.Application;
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
    private ListPreferences listPreferences;
    private UserBean userBean;
    private CipherBean cipherBean;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.initStyle(StageStyle.UNDECORATED);
        stageManager.setPrimaryStage(primaryStage);
        stageManager.setLoader(new FXMLLoader());
        showInitialScene();
    }

    private void showInitialScene() throws IOException, NoSuchPaddingException, NoSuchAlgorithmException {
        String savedUserId = userPreferences.getUserID();
        if (savedUserId == null) {
            stageManager.switchScene(FXMLView.LOGIN);
        } else {
            UserService userService = new UserServiceImplimentation();
            User user = userService.getUser(savedUserId);
            if (user == null) {
                userPreferences.removeUserID();
            } else {
                setUserInfo(user);
                stageManager.switchScene(FXMLView.MAIN);
            }
        }

    }

    private void setUserInfo(User user) throws NoSuchPaddingException, NoSuchAlgorithmException {
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

    public void init() {
        stageManager = StageManager.INSTANCE;
        userPreferences = UserPreferences.INSTANCE;
        listPreferences = ListPreferences.INSTANCE;
        userBean = UserBean.INSTANCE;
        cipherBean = CipherBean.INSTANCE;
        listPreferences.setListPreferences();
        userPreferences.setUserPreferences();
    }
}
