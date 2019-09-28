package bean;

import model.EFileList;

public enum UserBean {
    INSTANCE;
    String userID;
    String firstName;
    String lastName;
    String email;
    EFileList fileList;

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

    public EFileList getFileList() {
        return fileList;
    }

    public void setFileList(EFileList fileList) {
        this.fileList = fileList;
    }

}
