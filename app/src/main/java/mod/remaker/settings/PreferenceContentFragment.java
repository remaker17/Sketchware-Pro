package mod.remaker.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragmentCompat;
import androidx.recyclerview.widget.RecyclerView;

import mod.remaker.util.AddPaddingOnApplyWindowInsetsListener;
import mod.remaker.util.ThemeUtils;

public abstract class PreferenceContentFragment extends PreferenceFragmentCompat {
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setBackgroundColor(ThemeUtils.getColor(view, android.R.attr.colorBackground));
    }

    @Override
    public RecyclerView onCreateRecyclerView(@NonNull LayoutInflater inflater,
            @NonNull ViewGroup parent, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = super.onCreateRecyclerView(inflater, parent, savedInstanceState);
        recyclerView.setClipToPadding(false);
        recyclerView.setOnApplyWindowInsetsListener(new AddPaddingOnApplyWindowInsetsListener(
            WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout()));
        return recyclerView;
    }
}
