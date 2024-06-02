package mod.remaker.settings.fragment;

import static mod.remaker.util.SettingsConstants.SETTINGS_FILE;
import static mod.remaker.util.SettingsConstants.BACKUP_FILENAME;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.SwitchPreferenceCompat;

import com.sketchware.remod.R;

import java.util.HashMap;

import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hilal.saif.activities.tools.ConfigActivity;
import mod.remaker.settings.PreferenceContentFragment;
import mod.remaker.settings.PreferenceFragment;
import mod.remaker.util.SettingsConstants;
import mod.remaker.util.SettingsUtils;

public class ModSettingsFragment extends PreferenceFragment {
    @Override
    public String getTitle(Context context) {
        return "Mod Settings";
    }

    @Override
    public PreferenceContentFragment createContentFragment() {
        return new ModSettingsFragmentContent();
    }

    public static class ModSettingsFragmentContent extends PreferenceContentFragment {
        @Override
        public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
            setPreferencesFromResource(R.xml.preference_mod, rootKey);
        }

        @Override
        public boolean onPreferenceTreeClick(Preference preference) {
            if (preference instanceof SwitchPreferenceCompat switchPreference) {
                ConfigActivity.changeSetting(preference.getKey(), switchPreference.isChecked());
            }
            return false;
        }

        @Override
        public void onSetupPreference(@NonNull Preference preference) {
            String keyName = preference.getKey();
            HashMap<String, Object> settingMap = ConfigActivity.getSettings();

            if (preference instanceof SwitchPreferenceCompat switchPreference) {
                if (ConfigActivity.isSettingAvailable(keyName)) {
                    Object value = settingMap.get(keyName);
                    if (value == null) {
                        ConfigActivity.removeSetting(keyName);
                    } else {
                        if (value instanceof Boolean) {
                            switchPreference.setChecked((boolean) value);
                        } else {
                            SketchwareUtil.toastError("Detected invalid value for preference \""
                                    + preference.getTitle() + "\". Restoring defaults");
                            ConfigActivity.removeSetting(keyName);
                        }
                    }
                } else {
                    boolean defaultValue = (boolean) SettingsUtils.getDefaultValue(keyName);
                    ConfigActivity.changeSetting(keyName, defaultValue);
                    switchPreference.setChecked(defaultValue);
                }
            }

            if (preference.getKey().equals(BACKUP_FILENAME) && preference instanceof EditTextPreference editTextPreference) {
                editTextPreference.setDialogMessage("This defines how SWB backup files get named.\n" +
                    "Available variables:\n" +
                    " - $projectName - Project name\n" +
                    " - $versionCode - App version code\n" +
                    " - $versionName - App version name\n" +
                    " - $pkgName - App package name\n" +
                    " - $timeInMs - Time during backup in milliseconds\n" +
                    "\n" +
                    "Additionally, you can format your own time like this using Java's date formatter syntax:\n" +
                    "$time(yyyy-MM-dd'T'HHmmss)\n");
            }
        }
    }
}
