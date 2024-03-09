package mod.remaker.util;

import android.content.Context;
import android.content.res.Configuration;

import androidx.annotation.NonNull;

public class ThemeUtils {
    private ThemeUtils() {
    }

    public static boolean isDarkTheme(@NonNull Context context) {
        return (context.getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
    }
}
