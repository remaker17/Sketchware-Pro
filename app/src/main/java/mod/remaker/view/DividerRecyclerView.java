package mod.remaker.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.divider.MaterialDividerItemDecoration;
import com.google.android.material.resources.MaterialResources;
import com.sketchware.remod.R;

public class DividerRecyclerView extends RecyclerView {
    private int dividerInsetEnd;
    private int dividerInsetStart;
    private int dividerThickness;
    private MaterialDividerItemDecoration decoration;

    public DividerRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public DividerRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DividerRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DividerRecyclerView, defStyleAttr, 0);
        dividerInsetEnd = a.getDimensionPixelOffset(R.styleable.DividerRecyclerView_dividerInsetEnd, 0);
        dividerInsetStart = a.getDimensionPixelOffset(R.styleable.DividerRecyclerView_dividerInsetStart, 0);
        dividerThickness = a.getDimensionPixelSize(R.styleable.DividerRecyclerView_dividerThickness, getResources().getDimensionPixelSize(R.dimen.material_divider_thickness));

        initializeItemDecoration(context);
        setDividerColor(
            MaterialResources.getColorStateList(
                context, a, R.styleable.DividerRecyclerView_dividerColor).getDefaultColor());

        a.recycle();
        addItemDecoration(decoration);
    }

    public void setDividerColor(int dividerColor) {
        if (decoration == null) {
            return;
        }
        decoration.setDividerColor(dividerColor);
    }

    private void initializeItemDecoration(Context context) {
        decoration = new MaterialDividerItemDecoration(context, MaterialDividerItemDecoration.VERTICAL);
        decoration.setDividerInsetEnd(dividerInsetEnd);
        decoration.setDividerInsetStart(dividerInsetStart);
        decoration.setDividerThickness(dividerThickness);
        decoration.setLastItemDecorated(false);
    }
}
