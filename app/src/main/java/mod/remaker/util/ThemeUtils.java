package mod.remaker.util;

import static android.graphics.Color.TRANSPARENT;

import android.content.res.Configuration;
import android.view.View;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;
import androidx.activity.SystemBarStyle;
import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.google.android.material.color.MaterialColors;

public class ThemeUtils {
    public static final int DEFAULT_DARK_SCRIM = EdgeToEdge.getDefaultDarkScrim();
    public static final SystemBarStyle DEFAULT_STATUS_BAR_STYLE = SystemBarStyle.auto(
        TRANSPARENT, TRANSPARENT);

    private ThemeUtils() {
    }

    public static @ColorInt int getColor(@NonNull View view, @AttrRes int resourceId) {
        return MaterialColors.getColor(view, resourceId);
    }

    /**
     * A method without enforcing contrast, magic based on `EdgeToEdge.kt`
     *
     * @param activity The activity to which edge-to-edge will be enabled.
     */
    public static void enableEdgeToEdgeProperly(ComponentActivity activity) {
        if ((activity.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) ==
                Configuration.UI_MODE_NIGHT_YES) {
            EdgeToEdge.enable(activity, DEFAULT_STATUS_BAR_STYLE,
                SystemBarStyle.dark(TRANSPARENT));
        } else {
            EdgeToEdge.enable(activity, DEFAULT_STATUS_BAR_STYLE,
                SystemBarStyle.light(TRANSPARENT, DEFAULT_DARK_SCRIM));
        }
    }
}
