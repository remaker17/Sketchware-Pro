package mod.remaker.settings.fragment;

import android.content.Context;
import android.os.Bundle;

import mod.remaker.settings.PreferenceContentFragment;
import mod.remaker.settings.PreferenceFragment;

public class ModSettingsFragment extends PreferenceFragment {
    @Override
    public String getTitle(Context context) {
        return "Mod Settings";
    }

    @Override
    public PreferenceContentFragment createContentFragment() {
        return new ModSettingsFragmentContent();
    }
}
