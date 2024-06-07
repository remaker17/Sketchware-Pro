package mod.remaker.settings.fragment;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static mod.remaker.util.SettingsConstants.BACKUP_DIRECTORY;

import android.content.Context;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;

import com.sketchware.remod.R;
import com.sketchware.remod.databinding.FragmentChangeBackupDirectoryBinding;

import java.io.File;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import mod.SketchwareUtil;
import mod.hilal.saif.activities.tools.ConfigActivity;
import mod.remaker.settings.BackupDirectoryManager;
import mod.remaker.settings.PreferenceFragment;
import mod.remaker.settings.adapter.BackupDirectoryAdapter;
import mod.remaker.settings.adapter.BackupDirectoryAdapter.OnBackupDirectoryClickListener;
import mod.remaker.settings.model.ItemBackupDirectory;
import mod.remaker.util.SettingsContracts.PickDirectory;
import mod.remaker.util.SettingsUtils;

public class ChangeBackupDirectoryFragment extends PreferenceFragment implements OnBackupDirectoryClickListener {
    private ActivityResultLauncher<Uri> pickFileTreeLauncher =
        registerForActivityResult(new PickDirectory(), this::onCustomDirectoryPicked);

    private ActivityResultLauncher<String> permissionRequestLauncher =
        registerForActivityResult(SettingsUtils.getPermissionContract(), isGranted -> {
            if (isGranted) {
                refreshDirectories();
                pickFileTreeLauncher.launch(null);
            } else {
                SketchwareUtil.toastError("No permission to write files.");
            }
        });

    private BackupDirectoryAdapter adapter;
    private BackupDirectoryManager directoryManager;
    private BackupDirectorySelectListener onBackupDirectorySelectListener;

    public interface BackupDirectorySelectListener {
        void onBackupDirectorySelect(ItemBackupDirectory directory);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        directoryManager = BackupDirectoryManager.getInstance(context);
    }

    @Override
    public String getTitle(Context context) {
        return "Backup directory";
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container) {
        FragmentChangeBackupDirectoryBinding binding = FragmentChangeBackupDirectoryBinding.inflate(inflater, container, false);
        adapter = new BackupDirectoryAdapter();
        adapter.setOnBackupDirectoryClickListener(this);

        binding.recyclerView.setAdapter(adapter);
        refreshDirectories();

        return binding.getRoot();
    }

    @Override
    public int getScrollTargetViewId() {
        return R.id.recycler_view;
    }

    @Override
    public void onDirectoryClick(ItemBackupDirectory item) {
        if (TextUtils.isEmpty(item.path())) {
            permissionRequestLauncher.launch(WRITE_EXTERNAL_STORAGE);
        } else {
            if (onBackupDirectorySelectListener != null) {
                onBackupDirectorySelectListener.onBackupDirectorySelect(item);
            }

            directoryManager.changeDefaultBackupDirectory(item);
        }
    }

    public void setOnBackupDirectorySelectListener(BackupDirectorySelectListener listener) {
        onBackupDirectorySelectListener = listener;
    }

    private void refreshDirectories() {
        new Thread(() -> {
            ArrayList<ItemBackupDirectory> backupDirectories = directoryManager.getBackupDirectories();
            backupDirectories.add(new ItemBackupDirectory("Choose another directory", null));

            getActivity().runOnUiThread(() -> adapter.setItems(backupDirectories));
        }).start();
    }

    private void onCustomDirectoryPicked(Uri uri) {
        if (uri == null) {
            return;
        }

        new Thread(() -> {
            directoryManager.takePermissions(uri);
            File directory = Objects.requireNonNull(directoryManager.resolveUri(getContext(), uri),
                "Cannot resolve file name of \"" + uri + "\"");

            try {
                if (!directory.canWrite()) {
                    throw new AccessDeniedException(directory.toString());
                }
            } catch (AccessDeniedException e) {
                throw new IllegalStateException(e);
            }

            ItemBackupDirectory backupDirectory = new ItemBackupDirectory(directory.getName(), directory.getAbsolutePath());
            directoryManager.addBackupDirectory(backupDirectory, (success, message) -> {
                getActivity().runOnUiThread(() -> {
                    if (!TextUtils.isEmpty(message)) {
                        if (success) {
                            SketchwareUtil.toast(message);
                        } else {
                            SketchwareUtil.toastError(message);
                        }
                    }
                });
            });
            directoryManager.changeDefaultBackupDirectory(backupDirectory);

            getActivity().runOnUiThread(() -> {
                if (onBackupDirectorySelectListener != null) {
                    onBackupDirectorySelectListener.onBackupDirectorySelect(backupDirectory);
                }

                refreshDirectories();
            });
        }).start();
    }
}
