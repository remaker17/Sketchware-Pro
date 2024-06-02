package mod.remaker.util;

import static mod.remaker.util.SettingsConstants.SETTINGS_FILE;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.Helper;
import mod.jbk.util.LogUtil;
import mod.remaker.settings.PreferenceContentFragment;
import mod.remaker.settings.PreferenceFragment;
import mod.remaker.util.SettingsConstants;
import mod.remaker.util.SettingsUtils;

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
}
