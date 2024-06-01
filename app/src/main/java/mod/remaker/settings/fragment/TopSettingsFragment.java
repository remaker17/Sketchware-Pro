package mod.remaker.settings.fragment;

import android.os.Bundle;

import mod.remaker.settings.PreferenceContentFragment;
import mod.remaker.settings.PreferenceFragment;

public class TopSettingsFragment extends PreferenceFragment {
    @Override
    public String getTitle() {
        return "Sketchware Settings";
    }

    @Override
    public PreferenceContentFragment createContentFragment() {
        return new TopSettingsFragmentContent();
    }
}
