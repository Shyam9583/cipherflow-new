package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EFile {

    private StringProperty fileName;
    private StringProperty filePath;
    private StringProperty lastEncrypted;

    public EFile(String filePath, Date lastEncrypted) {
        int idx = filePath.lastIndexOf("/") + 1;
        if (idx < filePath.length()) {
            this.fileName = new SimpleStringProperty(filePath.substring(idx));
        } else this.fileName = new SimpleStringProperty("");
        this.filePath = new SimpleStringProperty(filePath);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        this.lastEncrypted = new SimpleStringProperty(dateFormat.format(lastEncrypted));
    }

    public EFile() {

    }

    public String getFileName() {
        return fileName.getValue();
    }

    public StringProperty fileNameProperty() {
        return fileName;
    }

    public String getFilePath() {
        return filePath.getValue();
    }

    public void setFilePath(String filePath) {
        this.filePath.set(filePath);
        int idx = filePath.lastIndexOf("/") + 1;
        if (idx < filePath.length()) {
            this.fileName.set(filePath.substring(idx));
        } else this.fileName.set("");
    }

    public StringProperty filePathProperty() {
        return filePath;
    }

    public String getLastEncrypted() {
        return lastEncrypted.getValue();
    }

    public void setLastEncrypted(Date lastEncrypted) {
        Locale locale = new Locale("en", "IN");
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
        this.lastEncrypted.set(dateFormat.format(lastEncrypted));
    }

    public StringProperty lastEncryptedProperty() {
        return lastEncrypted;
    }

    @Override
    public String toString() {
        return "EFile{" +
                "fileName=" + fileName.get() +
                ", filePath=" + filePath.get() +
                ", lastEncrypted=" + lastEncrypted.get() +
                '}';
    }
}
