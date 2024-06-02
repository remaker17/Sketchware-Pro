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
    private static final int SHOW_REQUEST_TIMEOUT = 1000;
    private static final String SAVE_STATE_TEXT = "M3EditTextPreferenceFragment.text";
    private static final String SAVE_STATE_MESSAGE_TEXT = "M3EditTextPreferenceFragment.messageText";

    private TextInputLayout mInputLayout;
    private CharSequence mText;
    private CharSequence mMessageText;

    private int mWhichButtonClicked = 0;

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
            mMessageText = getEditTextPreference().getDialogMessage();
        } else {
            mText = savedInstanceState.getCharSequence(SAVE_STATE_TEXT);
            mMessageText = savedInstanceState.getCharSequence(SAVE_STATE_MESSAGE_TEXT);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(SAVE_STATE_TEXT, mText);
        outState.putCharSequence(SAVE_STATE_MESSAGE_TEXT, mMessageText);
    }

    @Override
    public @NonNull Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mWhichButtonClicked = DialogInterface.BUTTON_NEGATIVE;

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

        mInputLayout.getEditText().setText(mText);
        mInputLayout.setHelperText(mMessageText);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        mWhichButtonClicked = which;
    }

    private EditTextPreference getEditTextPreference() {
        return (EditTextPreference) getPreference();
    }

    @Override
    public void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            String value = mInputLayout.getEditText().getText().toString();
            final EditTextPreference preference = getEditTextPreference();
            if (preference.callChangeListener(value)) {
                preference.setText(value);
            }
        }
    }
}
