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
        replaceFragment(new TopSettingsFragment());
    }

    public void replaceFragment(PreferenceFragment fragment) {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().addToBackStack(null);
        Fragment lastFragment = null;
        if (fragments != null && !fragments.isEmpty() && fragments.size() > 0) {
            lastFragment = fragments.get(fragments.size() - 1);
        }
        if (lastFragment != null) {
            transaction.hide(lastFragment);
        }
        transaction.add(R.id.settings_container, fragment);
        transaction.commit();
    }
}
