package model;

import java.io.Serializable;
import java.util.List;

public class EFileList implements Serializable {
    private List<EFile> files;

    public List<EFile> getFiles() {
        return files;
    }

    public void setFiles(List<EFile> files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "EFileList{" +
                "files=" + files +
                '}';
    }
}
