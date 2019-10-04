package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SavedFileList implements Serializable {

    private static final long serialVersionUID = 2234567890123456789L;

    private List<SavedFile> files;

    public SavedFileList(ArrayList<SavedFile> eFiles) {
        this.files = eFiles;
    }

    public SavedFileList() {
        this.files = new ArrayList<>();
    }

    public List<SavedFile> getFiles() {
        return files;
    }

    public void setFiles(List<SavedFile> files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "SavedFileList{" +
                "files=" + files +
                '}';
    }
}
