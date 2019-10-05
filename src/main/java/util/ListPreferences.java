package util;

import model.SavedFileList;

import java.io.*;
import java.util.prefs.Preferences;

public enum ListPreferences {

    INSTANCE;

    String key;
    Preferences preferences;

    public SavedFileList getList() {
        byte[] stored;
        SavedFileList eFileList;
        try {
            stored = preferences.getByteArray(key, null);
            if (stored == null) {
                return null;
            } else {
                ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(stored));
                Object object = objectInputStream.readObject();
                if (object instanceof SavedFileList) {
                    eFileList = (SavedFileList) object;
                    return eFileList;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setList(SavedFileList eFileList) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(eFileList);
            preferences.putByteArray(key, byteArrayOutputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean removeList(String userId) {
        byte[] stored = preferences.getByteArray(userId + "-List", null);
        if (stored != null) {
            preferences.remove(userId + "-List");
            return true;
        }
        return false;
    }

    public boolean removeList() {
        byte[] stored = preferences.getByteArray("List", null);
        if (stored != null) {
            preferences.remove("List");
            return true;
        }
        return false;
    }

    public void setListPreferences(String userId) {
        this.key = userId + "-List";
        this.preferences = Preferences.userRoot().node(this.getClass().getName());
    }

    public void setListPreferences() {
        this.preferences = Preferences.userRoot().node(this.getClass().getName());
    }
}
