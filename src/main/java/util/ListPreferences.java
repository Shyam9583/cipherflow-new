package util;

import model.SavedFileList;

import java.io.*;
import java.util.prefs.Preferences;

public enum ListPreferences {

    INSTANCE;

    final String key = "List";
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

    public boolean removeList() {
        byte[] stored = preferences.getByteArray(key, null);
        if (stored != null) {
            preferences.remove(key);
            return true;
        }
        return false;
    }

    public void setListPreferences() {
        this.preferences = Preferences.userRoot().node(this.getClass().getName());
    }
}
