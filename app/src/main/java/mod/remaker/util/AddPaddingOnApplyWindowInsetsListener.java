package mod.remaker.util;

import android.graphics.Insets;
import android.view.View;
import android.view.View.OnApplyWindowInsetsListener;
import android.view.ViewGroup;
import android.view.WindowInsets;

import androidx.annotation.NonNull;

public class AddPaddingOnApplyWindowInsetsListener implements OnApplyWindowInsetsListener {
    private final int insetsTypeMask;

    /**
     * @param insetsTypeMask Bit mask of {@link WindowInsets.Type}s to query the insets for.
     */
    public AddPaddingOnApplyWindowInsetsListener(int insetsTypeMask) {
        this.insetsTypeMask = insetsTypeMask;
    }

    @NonNull
    @Override
    public WindowInsets onApplyWindowInsets(@NonNull View v, @NonNull WindowInsets insets) {
        Insets toApply = insets.getInsets(insetsTypeMask);

        int pl = v.getPaddingLeft();
        int pt = v.getPaddingTop();
        int pr = v.getPaddingRight();
        int pb = v.getPaddingBottom();

        v.setPadding(toApply.left + pl, toApply.top + pt, toApply.right + pr,
            toApply.bottom + pb);

        return insets;
    }
}