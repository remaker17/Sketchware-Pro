package mod.remaker.settings;

import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION;

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

public class DirectoryManager {
    private static DirectoryManager instance;
    private ContentResolver contentResolver;

    public static DirectoryManager getInstance(Context context) {
        if (instance == null) {
            instance = new DirectoryManager(context);
        }
        return instance;
    }

    private DirectoryManager(Context context) {
        contentResolver = context.getContentResolver();
    }

    public ArrayList<ItemBackupDirectory> getBackupDirectories() {
        return ConfigActivity.getCustomBackupDirectories();
            // .stream().filter(this::isWriteable)
            // .collect(Collectors.toCollection(ArrayList::new));
    }

    public void addBackupDirectory(ItemBackupDirectory directory) {
        ConfigActivity.addCustomBackupDirectory(directory);
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

    public String getDisplayName(File file) {
        if (file.getAbsolutePath().contains(".sketchware")) {
            return "Default directory";
        } else {
            return file.getName();
        }
    }

    private boolean isWriteable(ItemBackupDirectory directory) {
        File file = new File(directory.path());
        return file != null ? file.canWrite() : false;
    }
}
