package model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SavedFile implements Serializable {
    private static final long serialVersionUID = 1234567890123456789L;

    private String savedFileName;
    private String savedFilePath;
    private Date savedLastEncrypted;

    public SavedFile(String savedFilePath, String savedLastEncrypted) throws ParseException {
        int idx = savedFilePath.lastIndexOf("/") + 1;
        if (idx < savedFilePath.length()) {
            this.savedFileName = savedFilePath.substring(idx);
        } else this.savedFileName = "";
        this.savedFilePath = savedFilePath;
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        this.savedLastEncrypted = dateFormat.parse(savedLastEncrypted);
    }

    public SavedFile() {

    }

    public String getSavedFileName() {
        return savedFileName;
    }

    public String getSavedFilePath() {
        return savedFilePath;
    }

    public Date getSavedLastEncrypted() {
        return savedLastEncrypted;
    }
}
