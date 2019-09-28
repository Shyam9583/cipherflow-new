package util;

import java.util.prefs.Preferences;

public enum UserPreferences {

    INSTANCE;

    final String key = "UserID";

    Preferences userPreferences;

    public String getUserID() {
        return userPreferences.get(key, null);
    }

    public void setUserID(String id) {
        userPreferences.put(key, id);
    }

    public void setUserPreferences() {
        this.userPreferences = Preferences.userRoot().node(this.getClass().getName());
    }

    public boolean removeUserID() {
        String value = userPreferences.get(key, null);
        if (value != null) {
            userPreferences.remove(key);
            return true;
        }
        return false;
    }
}
