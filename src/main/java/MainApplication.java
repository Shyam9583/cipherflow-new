import bean.UserBean;
import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.EFileList;
import util.ListPreferences;
import util.StageManager;
import util.UserPreferences;
import view.FXMLView;

import java.io.IOException;

public class MainApplication extends Application {

    private StageManager stageManager;
    private UserPreferences userPreferences;

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

    private void showInitialScene() throws IOException {
        String savedUserId = userPreferences.getUserID();
        if (savedUserId == null) {
            stageManager.switchScene(FXMLView.LOGIN);
        } else {
            stageManager.switchScene(FXMLView.MAIN);
        }
    }


    public void init() {
        stageManager = StageManager.INSTANCE;
        userPreferences = UserPreferences.INSTANCE;
        ListPreferences listPreferences = ListPreferences.INSTANCE;
        listPreferences.setListPreferences();
        userPreferences.setUserPreferences();
        UserBean userBean = UserBean.INSTANCE;
        EFileList eFileList = listPreferences.getList();
        assert eFileList != null;
        if (eFileList.getFiles() == null) {
            userBean.setFileList(new EFileList());
        } else {
            userBean.setFileList(eFileList);
        }
    }
}
