package mod.remaker.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.elevation.SurfaceColors;
import com.sketchware.remod.R;

import mod.remaker.util.ThemeUtils;

public class SurfaceGroupView extends FrameLayout {
    private final MaterialCardView contentContainer;
    private final TextView header;
    private final boolean layoutInflated;

    public SurfaceGroupView(@NonNull Context context) {
        this(context, null);
    }

    public SurfaceGroupView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SurfaceGroupView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SurfaceGroupView, defStyleAttr, 0);
        String headerText = a.getString(R.styleable.SurfaceGroupView_headerText);
        boolean useDefaultBackgroundColor = a.getBoolean(R.styleable.SurfaceGroupView_useDefaultBackgroundColor, true);

        a.recycle();

        LayoutInflater.from(context).inflate(R.layout.surface_group, this);
        layoutInflated = true;

        contentContainer = findViewById(R.id.content_container);
        header = findViewById(R.id.header);

        setInitialBackgroundColor(context, useDefaultBackgroundColor);
        setHeaderText(headerText);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (layoutInflated) {
            contentContainer.addView(child, index, params);
        } else {
            super.addView(child, index, params);
        }
    }

    public void setHeaderText(String text) {
        header.setText(text);
    }

    private void setInitialBackgroundColor(Context context, boolean useDefaultBackgroundColor) {
        if (!useDefaultBackgroundColor) {
            return;
        }
        int color = defineBackgroundColor(context);
        contentContainer.setCardBackgroundColor(color);
    }

    private @ColorInt int defineBackgroundColor(Context context) {
        if (ThemeUtils.isDarkTheme(context)) {
            return SurfaceColors.SURFACE_1.getColor(context);
        } else {
            return SurfaceColors.SURFACE_2.getColor(context);
        }
    }
}
