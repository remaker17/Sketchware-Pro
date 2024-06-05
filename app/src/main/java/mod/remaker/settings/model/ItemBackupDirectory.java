package mod.remaker.settings.model;

import static mod.remaker.util.SettingsConstants.BACKUP_DIRECTORY;
import static mod.remaker.util.SettingsUtils.getDefaultValue;

public record ItemBackupDirectory(String title, String path, boolean isChecked) {
    public static final ItemBackupDirectory DEFAULT_DIRECTORY =
        new ItemBackupDirectory("Default directory", (String) getDefaultValue(BACKUP_DIRECTORY), false);
}
