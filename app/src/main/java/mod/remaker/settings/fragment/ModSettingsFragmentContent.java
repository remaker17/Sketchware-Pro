package mod.remaker.settings.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreferenceCompat;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.sketchware.remod.R;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.Helper;
import mod.jbk.util.LogUtil;
import mod.remaker.settings.PreferenceContentFragment;

public class ModSettingsFragmentContent extends PreferenceContentFragment {
    public static final File SETTINGS_FILE = new File(FileUtil.getExternalStorageDir(), ".sketchware/data/settings.json");

    public static final String SETTING_ALWAYS_SHOW_BLOCKS = "always-show-blocks";
    public static final String SETTING_BACKUP_DIRECTORY = "backup-dir";
    public static final String SETTING_ROOT_AUTO_INSTALL_PROJECTS = "root-auto-install-projects";
    public static final String SETTING_ROOT_AUTO_OPEN_AFTER_INSTALLING = "root-auto-open-after-installing";
    public static final String SETTING_BACKUP_FILENAME = "backup-filename";
    public static final String SETTING_LEGACY_CODE_EDITOR = "legacy-ce";
    public static final String SETTING_SHOW_BUILT_IN_BLOCKS = "built-in-blocks";
    public static final String SETTING_SHOW_EVERY_SINGLE_BLOCK = "show-every-single-block";
    public static final String SETTING_USE_NEW_VERSION_CONTROL = "use-new-version-control";
    public static final String SETTING_USE_ASD_HIGHLIGHTER = "use-asd-highlighter";
    public static final String SETTING_SKIP_MAJOR_CHANGES_REMINDER = "skip-major-changes-reminder";
    public static final String SETTING_BLOCKMANAGER_DIRECTORY_PALETTE_FILE_PATH = "palletteDir";
    public static final String SETTING_BLOCKMANAGER_DIRECTORY_BLOCK_FILE_PATH = "blockDir";

    private HashMap<String, Object> setting_map = new HashMap<>();

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preference_mod, rootKey);

        if (FileUtil.isExistFile(SETTINGS_FILE.getAbsolutePath())) {
            setting_map = readSettings();
            if (!setting_map.containsKey(SETTING_SHOW_BUILT_IN_BLOCKS) || !setting_map.containsKey(SETTING_ALWAYS_SHOW_BLOCKS)) {
                restoreDefaultSettings(setting_map);
            }
        } else {
            restoreDefaultSettings(setting_map);
        }

        PreferenceScreen screen = getPreferenceScreen();
        int count = screen.getPreferenceCount();
        for (int i = 0; i < count; i++) {
            Preference preference = screen.getPreference(i);
            String keyName = preference.getKey();

            if (preference instanceof SwitchPreferenceCompat switchPreference) {
                if (setting_map.containsKey(keyName)) {
                    Object value = setting_map.get(keyName);
                    if (value == null) {
                        /* Nulls aren't great */
                        setting_map.remove(keyName);
                    } else {
                        if (value instanceof Boolean) {
                            switchPreference.setChecked((boolean) value);
                        } else {
                            SketchwareUtil.toastError("Detected invalid value for preference \""
                                    + preference.getTitle() + "\". Restoring defaults");
                            setting_map.remove(keyName);
                            FileUtil.writeFile(SETTINGS_FILE.getAbsolutePath(), new Gson().toJson(setting_map));
                        }
                    }
                } else {
                    setting_map.put(keyName, getDefaultValue(keyName));
                    FileUtil.writeFile(SETTINGS_FILE.getAbsolutePath(), new Gson().toJson(setting_map));
                    switchPreference.setChecked((boolean) getDefaultValue(keyName));
                }
            }
        }
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        if (preference instanceof SwitchPreferenceCompat switchPreference) {
            setSetting(preference.getKey(), switchPreference.isChecked());
        }
        return false;
    }

    public static Object getDefaultValue(String key) {
        switch (key) {
            case SETTING_ALWAYS_SHOW_BLOCKS:
            case SETTING_LEGACY_CODE_EDITOR:
            case SETTING_ROOT_AUTO_INSTALL_PROJECTS:
            case SETTING_SHOW_BUILT_IN_BLOCKS:
            case SETTING_SHOW_EVERY_SINGLE_BLOCK:
            case SETTING_USE_NEW_VERSION_CONTROL:
            case SETTING_USE_ASD_HIGHLIGHTER:
                return false;

            case SETTING_BACKUP_DIRECTORY:
                return "/.sketchware/backups/";

            case SETTING_ROOT_AUTO_OPEN_AFTER_INSTALLING:
                return true;

            case SETTING_BLOCKMANAGER_DIRECTORY_PALETTE_FILE_PATH:
                return "/.sketchware/resources/block/My Block/palette.json";

            case SETTING_BLOCKMANAGER_DIRECTORY_BLOCK_FILE_PATH:
                return "/.sketchware/resources/block/My Block/block.json";

            default:
                throw new IllegalArgumentException("Unknown key '" + key + "'!");
        }
    }

    public static void setSetting(String key, Object value) {
        HashMap<String, Object> settings = readSettings();
        settings.put(key, value);
        FileUtil.writeFile(SETTINGS_FILE.getAbsolutePath(), new Gson().toJson(settings));
    }

    @NonNull
    private static HashMap<String, Object> readSettings() {
        HashMap<String, Object> settings;

        if (SETTINGS_FILE.exists()) {
            Exception toLog;

            try {
                settings = new Gson().fromJson(FileUtil.readFile(SETTINGS_FILE.getAbsolutePath()), Helper.TYPE_MAP);
                if (settings != null) {
                    return settings;
                }

                toLog = new NullPointerException("settings == null");
                // fall-through to shared error handler
            } catch (JsonParseException e) {
                toLog = e;
                // fall-through to shared error handler
            }

            SketchwareUtil.toastError("Couldn't parse Mod Settings! Restoring defaults.");
            LogUtil.e("ConfigActivity", "Failed to parse Mod Settings.", toLog);
        }

        settings = new HashMap<>();
        restoreDefaultSettings(settings);

        return settings;
    }

    private static void restoreDefaultSettings(HashMap<String, Object> settings) {
        settings.clear();

        List<String> keys = Arrays.asList(SETTING_ALWAYS_SHOW_BLOCKS,
                SETTING_BACKUP_DIRECTORY,
                SETTING_LEGACY_CODE_EDITOR,
                SETTING_ROOT_AUTO_INSTALL_PROJECTS,
                SETTING_ROOT_AUTO_OPEN_AFTER_INSTALLING,
                SETTING_SHOW_BUILT_IN_BLOCKS,
                SETTING_SHOW_EVERY_SINGLE_BLOCK,
                SETTING_USE_NEW_VERSION_CONTROL,
                SETTING_USE_ASD_HIGHLIGHTER,
                SETTING_BLOCKMANAGER_DIRECTORY_PALETTE_FILE_PATH,
                SETTING_BLOCKMANAGER_DIRECTORY_BLOCK_FILE_PATH);

        for (String key : keys) {
            settings.put(key, getDefaultValue(key));
        }
        FileUtil.writeFile(SETTINGS_FILE.getAbsolutePath(), new Gson().toJson(settings));
    }
}
