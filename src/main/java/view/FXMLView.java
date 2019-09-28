package view;

/*
 * This class contains references to fxml resources
 * when a new fxml file is created, it must be referenced here
 * */

public enum FXMLView {

    LOGIN {
        public String getTitle() {
            return "Login Screen";
        }

        public String getFxmlFile() {
            return "fxml/login.fxml";
        }
    };

    //returns the title to display on screen
    public abstract String getTitle();

    //return the file path to use for fxml loader
    public abstract String getFxmlFile();
}

