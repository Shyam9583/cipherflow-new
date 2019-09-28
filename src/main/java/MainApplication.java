import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.ListPreferences;
import util.StageManager;
import util.UserPreferences;
import view.FXMLView;

import java.io.IOException;

public class MainApplication extends Application {

    private StageManager stageManager;

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

    private void showInitialScene() throws IOException {
        stageManager.switchScene(FXMLView.LOGIN);
    }

    public void init() {
        stageManager = StageManager.INSTANCE;
        UserPreferences userPreferences = UserPreferences.INSTANCE;
        ListPreferences listPreferences = ListPreferences.INSTANCE;
        listPreferences.setListPreferences();
        userPreferences.setUserPreferences();
    }
}
