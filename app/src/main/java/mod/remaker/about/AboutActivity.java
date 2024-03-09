package mod.remaker.about;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import com.google.android.material.tabs.TabLayoutMediator;
import com.sketchware.remod.databinding.AboutBinding;

import mod.hey.studios.util.Helper;
import mod.remaker.about.adapter.AboutPagerAdapter;

public class AboutActivity extends AppCompatActivity {
    private AboutBinding binding;
    private AboutPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        binding = AboutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeTopBar();
        initializePager();
    }

    private void initializeTopBar() {
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));
    }

    private void initializePager() {
        pagerAdapter = new AboutPagerAdapter(this);

        binding.viewPager.setAdapter(pagerAdapter);
        binding.viewPager.setOffscreenPageLimit(pagerAdapter.getItemCount());

        new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) ->
            tab.setText(pagerAdapter.getTitle(position))
        ).attach();
    }
}
