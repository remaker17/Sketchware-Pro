package mod.remaker.settings;

import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
import static mod.remaker.util.SettingsConstants.BACKUP_DIRECTORY;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import mod.hilal.saif.activities.tools.ConfigActivity;
import mod.remaker.settings.model.ItemBackupDirectory;
import mod.remaker.util.UriUtils;

public class BackupDirectoryManager {
    private static BackupDirectoryManager instance;
    private ContentResolver contentResolver;

    public static synchronized BackupDirectoryManager getInstance(Context context) {
        if (instance == null) {
            instance = new BackupDirectoryManager(context);
        }
        return instance;
    }

    public interface BackupDirectoryAddListener {
        void onBackupDirectoryAdd(boolean success, String message);
    }

    private BackupDirectoryManager(Context context) {
        contentResolver = context.getContentResolver();
    }

    public ArrayList<ItemBackupDirectory> getBackupDirectories() {
        return ConfigActivity.getCustomBackupDirectories();
    }

    public void addBackupDirectory(ItemBackupDirectory directory) {
        addBackupDirectory(directory, null);
    }

    public void addBackupDirectory(ItemBackupDirectory directory, BackupDirectoryAddListener listener) {
        if (getBackupDirectories().contains(directory)) {
            listener.onBackupDirectoryAdd(false, "This directory already is in list.");
            return;
        }

        ConfigActivity.addCustomBackupDirectory(directory);
        if (listener != null) {
            listener.onBackupDirectoryAdd(true, null);
        }
    }

    public void changeDefaultBackupDirectory(ItemBackupDirectory directory) {
        ConfigActivity.changeSetting(BACKUP_DIRECTORY, directory.path());
    }

    public void takePermissions(Uri uri) {
        contentResolver.takePersistableUriPermission(uri, FLAG_GRANT_READ_URI_PERMISSION | FLAG_GRANT_WRITE_URI_PERMISSION);
    }

    public File resolveUri(Context context, Uri uri) {
        if (uri.getScheme().equals("file")) {
            return new File(uri.getPath());
        } else {
            return UriUtils.resolveFile(context, uri);
        }
    }

    // somehow it returns false
    private boolean isWriteable(ItemBackupDirectory directory) {
        File file = new File(directory.path());
        return file != null ? file.canWrite() : false;
    }
}
