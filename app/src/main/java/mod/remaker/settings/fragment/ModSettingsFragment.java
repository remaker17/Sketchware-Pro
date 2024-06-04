package mod.remaker.settings.fragment;

import static mod.remaker.util.SettingsConstants.SETTINGS_FILE;
import static mod.remaker.util.SettingsConstants.BACKUP_DIRECTORY;
import static mod.remaker.util.SettingsConstants.BACKUP_FILENAME;
import static mod.remaker.util.SettingsConstants.ROOT_AUTO_INSTALL_PROJECTS;
import static mod.remaker.util.SettingsConstants.ROOT_AUTO_OPEN_AFTER_INSTALLING;
import static mod.remaker.util.SettingsUtils.getDefaultValue;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.SwitchPreferenceCompat;

import com.sketchware.remod.R;
import com.topjohnwu.superuser.Shell;

import java.util.HashMap;

import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hilal.saif.activities.tools.ConfigActivity;
import mod.remaker.settings.ExperimentalSettingsActivity;
import mod.remaker.settings.PreferenceContentFragment;
import mod.remaker.settings.PreferenceFragment;
import mod.remaker.settings.preference.M3EditTextPreference;

public class ModSettingsFragment extends PreferenceFragment {
    private static final String RESET_BACKUP_FILENAME_FORMAT = "reset-backup-filename";

    @Override
    public String getTitle(Context context) {
        return "Mod Settings";
    }

    @Override
    public Fragment getContentFragment() {
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

            if (preference.getKey().equals(RESET_BACKUP_FILENAME_FORMAT)) {
                ConfigActivity.removeSetting(BACKUP_FILENAME);
                SketchwareUtil.toast("Reset to default value complete.");
            }

            if (preference.getKey().equals(BACKUP_DIRECTORY)) {
                switchFragment(new ChangeBackupFilenameFormatFragment());
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
                    boolean defaultValue = Boolean.parseBoolean((String) getDefaultValue(keyName));
                    ConfigActivity.changeSetting(keyName, defaultValue);
                    switchPreference.setChecked(defaultValue);
                }
            }

            if (preference.getKey().equals(ROOT_AUTO_INSTALL_PROJECTS) || preference.getKey().equals(ROOT_AUTO_OPEN_AFTER_INSTALLING)) {
                Shell shell = Shell.getShell();
                if (!shell.isRoot()) {
                    preference.setEnabled(false);
                    preference.setSummary("Not available for non-rooted devices.");
                }
            }

            // if (preference.getKey().equals(BACKUP_DIRECTORY) && preference instanceof M3EditTextPreference editTextPreference) {
                // editTextPreference.setText(ConfigActivity.getBackupPath());
                // editTextPreference.setHelperText("Directory inside /Internal storage/, e.g. sketchware/backups");
                // editTextPreference.setOnPreferenceChangeListener((pref, value) -> {
                    // ConfigActivity.changeSetting(BACKUP_DIRECTORY, value);
                    // SketchwareUtil.toast("Saved");
                    // return true;
                // });
            // }

            if (preference.getKey().equals(BACKUP_FILENAME) && preference instanceof M3EditTextPreference editTextPreference) {
                editTextPreference.setText(ConfigActivity.getBackupFileName());
                editTextPreference.setHelperText(
                    "Available variables:\n" +
                    " - $projectName - Project name\n" +
                    " - $versionCode - App version code\n" +
                    " - $versionName - App version name\n" +
                    " - $pkgName - App package name\n" +
                    " - $timeInMs - Time during backup in milliseconds\n" +
                    "\n" +
                    "Additionally, you can format your own time like this using Java's date formatter syntax:\n" +
                    "$time(yyyy-MM-dd'T'HHmmss)\n");
                editTextPreference.setOnPreferenceChangeListener((pref, value) -> {
                    ConfigActivity.changeSetting(BACKUP_FILENAME, value);
                    SketchwareUtil.toast("Saved");
                    return true;
                });
            }
        }

        private boolean switchFragment(PreferenceFragment fragment) {
            if (fragment != null && requireActivity() instanceof ExperimentalSettingsActivity activity) {
                activity.switchFragment(fragment, /* addToBackStack= */ true);
                return true;
            }
            return false;
        }
    }
}
