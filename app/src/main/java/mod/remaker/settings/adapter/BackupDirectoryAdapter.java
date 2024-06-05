package mod.remaker.settings.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

import com.sketchware.remod.databinding.ItemBackupDirectoryBinding;

import mod.hasrat.lib.CommonViewBindingAdapter;
import mod.remaker.settings.model.ItemBackupDirectory;

public class BackupDirectoryAdapter extends CommonViewBindingAdapter<ItemBackupDirectory> {
    public interface OnBackupDirectoryClickListener {
        void onDirectoryClick(ItemBackupDirectory item);
    }

    private OnBackupDirectoryClickListener listener;

    @NonNull
    @Override
    public ViewBinding getViewBinding(LayoutInflater inflater, ViewGroup parent) {
        return ItemBackupDirectoryBinding.inflate(inflater, parent, false);
    }

    @Override
    public void onBindView(@NonNull ViewBinding binding, int position) {
        if (binding instanceof ItemBackupDirectoryBinding directoryBinding) {
            ItemBackupDirectory directory = getItem(position);
            directoryBinding.title.setText(directory.title());
            directoryBinding.path.setText(directory.path());
            // directoryBinding.indicator.setChecked(directory.isChecked());
            directoryBinding.getRoot().setOnClickListener(v -> listener.onDirectoryClick(directory));
        }
    }

    public void setOnBackupDirectoryClickListener(OnBackupDirectoryClickListener listener) {
        this.listener = listener;
    }
}
