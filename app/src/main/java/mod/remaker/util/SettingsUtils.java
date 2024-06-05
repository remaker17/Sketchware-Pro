package mod.remaker.util;

import static mod.remaker.util.SettingsContracts.RequestStorageManagerPermission;

import android.os.Build;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission;

import java.util.Arrays;
import java.util.List;

import mod.remaker.util.SettingsConstants;

public class SettingsUtils {
    public static final List<String> SETTINGS_KEYS = Arrays.asList(
        SettingsConstants.ALWAYS_SHOW_BLOCKS,
        SettingsConstants.BACKUP_DIRECTORY,
        SettingsConstants.LEGACY_CODE_EDITOR,
        SettingsConstants.ROOT_AUTO_INSTALL_PROJECTS,
        SettingsConstants.ROOT_AUTO_OPEN_AFTER_INSTALLING,
        SettingsConstants.SHOW_BUILT_IN_BLOCKS,
        SettingsConstants.SHOW_EVERY_SINGLE_BLOCK,
        SettingsConstants.USE_NEW_VERSION_CONTROL,
        SettingsConstants.USE_ASD_HIGHLIGHTER,
        SettingsConstants.BLOCKMANAGER_DIRECTORY_PALETTE_FILE_PATH,
        SettingsConstants.BLOCKMANAGER_DIRECTORY_BLOCK_FILE_PATH
    );

    public static Object getDefaultValue(String key) {
        switch (key) {
            case SettingsConstants.ALWAYS_SHOW_BLOCKS:
            case SettingsConstants.LEGACY_CODE_EDITOR:
            case SettingsConstants.ROOT_AUTO_INSTALL_PROJECTS:
            case SettingsConstants.SHOW_BUILT_IN_BLOCKS:
            case SettingsConstants.SHOW_EVERY_SINGLE_BLOCK:
            case SettingsConstants.USE_NEW_VERSION_CONTROL:
            case SettingsConstants.USE_ASD_HIGHLIGHTER:
                return false;

            case SettingsConstants.BACKUP_DIRECTORY:
                return "/.sketchware/backups/";

            case SettingsConstants.ROOT_AUTO_OPEN_AFTER_INSTALLING:
                return true;

            case SettingsConstants.BLOCKMANAGER_DIRECTORY_PALETTE_FILE_PATH:
                return "/.sketchware/resources/block/My Block/palette.json";

            case SettingsConstants.BLOCKMANAGER_DIRECTORY_BLOCK_FILE_PATH:
                return "/.sketchware/resources/block/My Block/block.json";

            default:
                throw new IllegalArgumentException("Unknown key '" + key + "'!");
        }
    }

    public static ActivityResultContract<String, Boolean> getPermissionContract() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return new RequestStorageManagerPermission();
        } else {
            return new RequestPermission();
        }
    }
}
