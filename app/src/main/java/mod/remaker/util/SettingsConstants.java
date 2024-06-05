package mod.remaker.util;

import java.io.File;

import mod.agus.jcoderz.lib.FileUtil;

public class SettingsConstants {
    public static final String ALWAYS_SHOW_BLOCKS = "always-show-blocks";
    public static final String BACKUP_DIRECTORY = "backup-dir";
    public static final String ROOT_AUTO_INSTALL_PROJECTS = "root-auto-install-projects";
    public static final String ROOT_AUTO_OPEN_AFTER_INSTALLING = "root-auto-open-after-installing";
    public static final String BACKUP_FILENAME = "backup-filename";
    public static final String BACKUP_DIRECTORIES = "backup-dirs";
    public static final String LEGACY_CODE_EDITOR = "legacy-ce";
    public static final String SHOW_BUILT_IN_BLOCKS = "built-in-blocks";
    public static final String SHOW_EVERY_SINGLE_BLOCK = "show-every-single-block";
    public static final String USE_NEW_VERSION_CONTROL = "use-new-version-control";
    public static final String USE_ASD_HIGHLIGHTER = "use-asd-highlighter";
    public static final String SKIP_MAJOR_CHANGES_REMINDER = "skip-major-changes-reminder";
    public static final String BLOCKMANAGER_DIRECTORY_PALETTE_FILE_PATH = "palletteDir";
    public static final String BLOCKMANAGER_DIRECTORY_BLOCK_FILE_PATH = "blockDir";

    public static final File SETTINGS_FILE = new File(FileUtil.getExternalStorageDir(), ".sketchware/data/settings.json");

    private SettingsConstants() {
    }
}
