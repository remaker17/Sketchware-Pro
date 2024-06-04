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
import com.sketchware.remod.databinding.PreferenceFragmentBinding;

public abstract class PreferenceFragment extends Fragment {
    protected abstract String getTitle(Context context);

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
        Fragment contentFragment = getContentFragment();
        View contentView = onCreateContentView(inflater, container);

        binding.toolbar.setNavigationOnClickListener(this::onNavigationClick);
        binding.toolbar.setTitle(getTitle(requireContext()));

        if (contentFragment != null) {
            getChildFragmentManager().beginTransaction()
                .addToBackStack(null)
                .add(binding.preferenceContentContainer.getId(), contentFragment)
                .commit();
        } else if (contentView != null) {
            if (contentView.getParent() != null) {
                ViewGroup parent = (ViewGroup) contentView.getParent();
                parent.removeView(contentView);
            }
            binding.preferenceContentContainer.addView(contentView);
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

    protected Fragment getContentFragment() {
        return null;
    }

    protected View onCreateContentView(LayoutInflater inflater, ViewGroup container) {
        return null;
    }
}
