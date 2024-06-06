package mod.remaker.settings.model;

import static mod.remaker.util.SettingsConstants.BACKUP_DIRECTORY;
import static mod.remaker.util.SettingsUtils.getDefaultValue;

import java.util.Objects;

public record ItemBackupDirectory(String title, String path) {
    public static final ItemBackupDirectory DEFAULT_DIRECTORY =
        new ItemBackupDirectory("Default directory", (String) getDefaultValue(BACKUP_DIRECTORY));

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemBackupDirectory directory = (ItemBackupDirectory) o;
        return Objects.equals(title, directory.title) &&
            Objects.equals(path, directory.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, path);
    }
}
