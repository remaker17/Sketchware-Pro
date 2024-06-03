package mod.remaker.settings.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference.BaseSavedState;

import com.sketchware.remod.R;

public class M3EditTextPreference extends EditTextPreference {
    private String mHelperText;
    private String mHint;

    public M3EditTextPreference(@NonNull Context context) {
        this(context, null);
    }

    public M3EditTextPreference(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.m3EditTextPreferenceStyle);
    }

    public M3EditTextPreference(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public M3EditTextPreference(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        TypedArray a = context.obtainStyledAttributes(
            attrs, R.styleable.M3EditTextPreference, defStyleAttr, defStyleRes);

        String helperText = a.getString(R.styleable.M3EditTextPreference_helperText);
        if (!TextUtils.isEmpty(helperText)) {
            setHelperText(helperText);
        }

        String hint = a.getString(R.styleable.M3EditTextPreference_hint);
        if (!TextUtils.isEmpty(hint)) {
            setHint(hint);
        }

        a.recycle();
    }

    /**
     * Sets the helper text to the preference.
     *
     * @param helperText The helper text to store
     */
    public void setHelperText(@Nullable String helperText) {
        mHelperText = helperText;
    }

    @Nullable
    public String getHelperText() {
        return mHelperText;
    }

    /**
     * Sets the hint to the preference.
     *
     * @param hint The hint text to store
     */
    public void setHint(@Nullable String hint) {
        mHint = hint;
    }

    @Nullable
    public String getHint() {
        return mHint;
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        final Parcelable superState = super.onSaveInstanceState();
        final SavedState myState = new SavedState(superState);
        myState.mHelperText = getHelperText();
        myState.mHint = getHint();
        return myState;
    }

    @Override
    protected void onRestoreInstanceState(@Nullable Parcelable state) {
        if (state == null || !state.getClass().equals(SavedState.class)) {
            // Didn't save state for us in onSaveInstanceState
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState myState = (SavedState) state;
        super.onRestoreInstanceState(myState.getSuperState());
        setHelperText(myState.mHelperText);
        setHint(myState.mHint);
    }

    private static class SavedState extends BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR =
                new Parcelable.Creator<SavedState>() {
                    @Override
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }

                    @Override
                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };

        String mHelperText;
        String mHint;

        SavedState(Parcel source) {
            super(source);
            mHelperText = source.readString();
            mHint = source.readString();
        }

        SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeString(mHelperText);
            dest.writeString(mHint);
        }
    }
}
