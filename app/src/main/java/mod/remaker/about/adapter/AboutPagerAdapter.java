package mod.remaker.about.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import mod.remaker.about.fragment.TeamFragment;
import mod.remaker.about.fragment.ChangelogFragment;
import mod.remaker.about.fragment.MajorChangesFragment;

public class AboutPagerAdapter extends FragmentStateAdapter {
    private final Fragment[] fragments = new Fragment[] {new TeamFragment(), new ChangelogFragment(), new MajorChangesFragment()};
    private final String[] titles = new String[] {"The team", "Changelog", "Major changes"};

    public AboutPagerAdapter(FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments[position];
    }

    @Override
    public int getItemCount() {
        return fragments.length;
    }

    public Fragment getFragment(int position) {
        if (position <= fragments.length) {
            return fragments[position];
        }
        return null;
    }

    public String getTitle(int position) {
        if (position <= titles.length) {
            return titles[position];
        }
        return "Unknown";
    }
}