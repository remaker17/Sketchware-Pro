package mod.remaker.settings.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.sketchware.remod.R;

import mod.remaker.settings.PreferenceContentFragment;

public class TopSettingsFragmentContent extends PreferenceContentFragment {
    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preference_top, rootKey);
    }
}
