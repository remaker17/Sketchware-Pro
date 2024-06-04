package mod.remaker.settings.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sketchware.remod.databinding.FragmentChangeBackupFilenameFormatBinding;

import mod.remaker.settings.PreferenceFragment;

public class ChangeBackupFilenameFormatFragment extends PreferenceFragment {
    @Override
    public String getTitle(Context context) {
        return "Backup directory";
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container) {
        FragmentChangeBackupFilenameFormatBinding binding = FragmentChangeBackupFilenameFormatBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
