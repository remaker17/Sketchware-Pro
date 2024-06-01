package mod.remaker.settings.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.Preference;

import com.sketchware.remod.R;

import mod.remaker.settings.ExperimentalSettingsActivity;
import mod.remaker.settings.PreferenceFragment;
import mod.remaker.settings.PreferenceContentFragment;

public class TopSettingsFragmentContent extends PreferenceContentFragment {
    private static final String KEY_MOD = "mod";

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preference_top, rootKey);
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        PreferenceFragment fragment = switch (preference.getKey()) {
            case KEY_MOD -> new ModSettingsFragment();
            default -> null;
        };
        return switchFragment(fragment);
    }

    private boolean switchFragment(PreferenceFragment fragment) {
        if (fragment != null && requireActivity() instanceof ExperimentalSettingsActivity activity) {
            activity.switchFragment(fragment, /* addToBackStack= */ true);
            return true;
        } else {
            return false;
        }
    }
}
