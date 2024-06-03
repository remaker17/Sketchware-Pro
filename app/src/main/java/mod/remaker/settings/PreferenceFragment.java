package mod.remaker.settings;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.transition.MaterialSharedAxis;
import com.sketchware.remod.R;
import com.sketchware.remod.databinding.PreferenceFragmentBinding;

public abstract class PreferenceFragment extends Fragment {
    protected abstract String getTitle(Context context);
    protected abstract PreferenceContentFragment createContentFragment();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnterTransition(new MaterialSharedAxis(MaterialSharedAxis.X, true));
        setReturnTransition(new MaterialSharedAxis(MaterialSharedAxis.X, false));
        setExitTransition(new MaterialSharedAxis(MaterialSharedAxis.X, true));
        setReenterTransition(new MaterialSharedAxis(MaterialSharedAxis.X, false));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        PreferenceFragmentBinding binding = PreferenceFragmentBinding.inflate(inflater, container, false);
        PreferenceContentFragment contentFragment = createContentFragment();

        binding.appBarLayout.setLiftOnScrollTargetViewId(androidx.preference.R.id.recycler_view);
        binding.toolbar.setNavigationOnClickListener(this::onNavigationClick);
        binding.toolbar.setTitle(getTitle(requireContext()));

        if (contentFragment != null) {
            getChildFragmentManager().beginTransaction()
                .addToBackStack(null)
                .add(R.id.preference_content_container, contentFragment)
                .commit();
        }

        return binding.getRoot();
    }

    protected void onNavigationClick(View v) {
        int backStackEntryCount = getParentFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount > 0) {
            getParentFragmentManager().popBackStack();
        } else {
            getActivity().finish();
        }
    }
}
