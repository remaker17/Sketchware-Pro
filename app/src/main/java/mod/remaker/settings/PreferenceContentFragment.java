package mod.remaker.settings;

import static mod.remaker.util.ThemeUtils.updateEdgeToEdgePadding;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragmentCompat;
import androidx.recyclerview.widget.RecyclerView;

import mod.remaker.util.ThemeUtils;

public abstract class PreferenceContentFragment extends PreferenceFragmentCompat {
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setBackgroundColor(ThemeUtils.getColor(view, android.R.attr.colorBackground));
        setupRecyclerView(view);
    }

    private void setupRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(androidx.preference.R.id.recycler_view);
        updateEdgeToEdgePadding(recyclerView);
    }
}
