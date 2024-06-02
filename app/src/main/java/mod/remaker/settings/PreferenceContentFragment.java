package mod.remaker.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;
import androidx.recyclerview.widget.RecyclerView;

import mod.remaker.settings.preference.M3EditTextPreferenceFragment;
import mod.remaker.util.ThemeUtils;

public abstract class PreferenceContentFragment extends PreferenceFragmentCompat {
    private static final String M3_DIALOG_FRAGMENT_TAG = "androidx.preference.PreferenceFragment.DIALOG";

    protected abstract void onSetupPreference(@NonNull Preference preference);

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setBackgroundColor(ThemeUtils.getColor(view, android.R.attr.colorBackground));

        PreferenceScreen screen = getPreferenceScreen();
        for (int i = 0; i < screen.getPreferenceCount(); i++) {
            Preference preference = screen.getPreference(i);
            setupPreference(preference);
        }
    }

    @Override
    public void onDisplayPreferenceDialog(Preference preference) {
        // check if dialog is already showing
        if (getParentFragmentManager().findFragmentByTag(M3_DIALOG_FRAGMENT_TAG) != null) {
            return;
        }

        if (preference instanceof EditTextPreference) {
            M3EditTextPreferenceFragment fragment = M3EditTextPreferenceFragment.newInstance(preference.getKey());
            fragment.setTargetFragment(this, 0);
            fragment.show(getParentFragmentManager(), M3_DIALOG_FRAGMENT_TAG);
        } else {
            super.onDisplayPreferenceDialog(preference);
        }
    }

    private void setupPreference(@NonNull Preference preference) {
        if (!preference.isVisible()) {
            return;
        }
        if (preference instanceof PreferenceCategory category) {
            for (int i = 0; i < category.getPreferenceCount(); i++) {
                Preference pref = category.getPreference(i);
                setupPreference(pref);
            }
            return;
        }
        onSetupPreference(preference);
    }
}
