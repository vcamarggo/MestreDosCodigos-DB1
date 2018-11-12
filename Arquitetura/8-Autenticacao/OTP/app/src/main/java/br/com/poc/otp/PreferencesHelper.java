package br.com.poc.otp;

import android.content.Context;

import com.pddstudio.preferences.encrypted.EncryptedPreferences;

public class PreferencesHelper {

    private static final String PREFERENCES_FILE_NAME = "otp_preferences";
    private static final String PREFERENCES_PASS = "8#fv(x5A0+$klzX.";

    public static final String SEED = "seed";
    public static final String HAS_TOKEN = "has_token";
    public static final String AGENCY = "agencia";
    public static final String ACCOUNT = "conta";
    public static final String ACCOUNT_ID = "conta_id";
    public static final String NAME = "name";

    public static void saveStringPreference(Context context, String key, String value) {
        new EncryptedPreferences.Builder(context)
                .withPreferenceName(PREFERENCES_FILE_NAME)
                .withEncryptionPassword(PREFERENCES_PASS)
                .build()
                .edit()
                .putString(key, value)
                .apply();
    }

    public static void saveLongPreference(Context context, String key, Long value) {
        new EncryptedPreferences.Builder(context)
                .withPreferenceName(PREFERENCES_FILE_NAME)
                .withEncryptionPassword(PREFERENCES_PASS)
                .build()
                .edit()
                .putLong(key, value)
                .apply();
    }

    public static void saveBooleanPreference(Context context, String key, Boolean value) {
        new EncryptedPreferences.Builder(context)
                .withPreferenceName(PREFERENCES_FILE_NAME)
                .withEncryptionPassword(PREFERENCES_PASS)
                .build()
                .edit()
                .putBoolean(key, value)
                .apply();
    }

    public static String getStringPreference(Context context, String key) {
        return new EncryptedPreferences.Builder(context)
                .withPreferenceName(PREFERENCES_FILE_NAME)
                .withEncryptionPassword(PREFERENCES_PASS)
                .build()
                .getString(key, "");
    }


    public static Long getLongPreference(Context context, String key) {
        return new EncryptedPreferences.Builder(context)
                .withPreferenceName(PREFERENCES_FILE_NAME)
                .withEncryptionPassword(PREFERENCES_PASS)
                .build()
                .getLong(key, 0L);
    }

    public static Boolean getBoolean(Context context, String key) {
        return new EncryptedPreferences.Builder(context)
                .withPreferenceName(PREFERENCES_FILE_NAME)
                .withEncryptionPassword(PREFERENCES_PASS)
                .build()
                .getBoolean(key, false);
    }


}
