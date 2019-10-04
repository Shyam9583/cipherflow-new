package bean;

import model.SavedFileList;

public enum UserBean {
    INSTANCE;
    String userID;
    String firstName;
    String lastName;
    String email;
    SavedFileList fileList;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public SavedFileList getFileList() {
        return fileList;
    }

    public void setFileList(SavedFileList fileList) {
        this.fileList = fileList;
    }

}
