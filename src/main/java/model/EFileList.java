package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EFileList implements Serializable {

    private static final long serialVersionUID = 4545454545454545455L;

    private List<EFile> files;

    public EFileList(ArrayList<EFile> eFiles) {
    }

    public EFileList() {
        this.files = new ArrayList<>();
    }

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
