package mod.remaker.settings;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.sketchware.remod.R;

import java.util.List;

import mod.remaker.settings.fragment.TopSettingsFragment;
import mod.remaker.util.ThemeUtils;

public class ExperimentalSettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeUtils.enableEdgeToEdgeProperly(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experimental_settings);
        switchFragment(new TopSettingsFragment(), false);
    }

    public void switchFragment(PreferenceFragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment activeFragment = getSupportFragmentManager().findFragmentById(R.id.settings_container);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        if (activeFragment != null) {
            transaction.hide(activeFragment);
        }
        transaction.add(R.id.settings_container, fragment);
        transaction.commit();
    }
}
