package mod.remaker.settings.preference;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceDialogFragmentCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.sketchware.remod.R;

public class M3EditTextPreferenceFragment extends PreferenceDialogFragmentCompat {
    private static final String SAVE_STATE_TEXT = "M3EditTextPreferenceFragment.text";

    private TextInputLayout mInputLayout;
    private CharSequence mText;

    @NonNull
    public static M3EditTextPreferenceFragment newInstance(@NonNull String key) {
        final M3EditTextPreferenceFragment fragment = new M3EditTextPreferenceFragment();
        final Bundle b = new Bundle(1);
        b.putString(ARG_KEY, key);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mText = getEditTextPreference().getText();
        } else {
            mText = savedInstanceState.getCharSequence(SAVE_STATE_TEXT);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(SAVE_STATE_TEXT, mText);
    }

    @Override
    public @NonNull Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext())
                .setTitle(getPreference().getDialogTitle())
                .setPositiveButton(getPreference().getPositiveButtonText(), this)
                .setNegativeButton(getPreference().getNegativeButtonText(), this);

        View contentView = onCreateDialogView(requireContext());
        if (contentView != null) {
            onBindDialogView(contentView);
            builder.setView(contentView);
        } else {
            builder.setMessage(getPreference().getDialogMessage());
        }

        return builder.create();
    }

    @Override
    protected void onBindDialogView(@NonNull View view) {
        super.onBindDialogView(view);

        mInputLayout = view.findViewById(R.id.input_layout);

        if (mInputLayout == null) {
            throw new IllegalStateException("Dialog view must contain an TextInputLayout with id" +
                    " @id/input_layout");
        }

        M3EditTextPreference preference = getEditTextPreference();
        mInputLayout.getEditText().setText(mText);
        mInputLayout.setHint(preference.getHint());
        mInputLayout.setHelperText(preference.getHelperText());
    }

    private M3EditTextPreference getEditTextPreference() {
        return (M3EditTextPreference) getPreference();
    }

    @Override
    public void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            String value = mInputLayout.getEditText().getText().toString();
            final M3EditTextPreference preference = getEditTextPreference();
            if (preference.callChangeListener(value)) {
                preference.setText(value);
            }
        }
    }
}
