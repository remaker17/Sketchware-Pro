package com.besome.sketch.editor.manage.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.lib.base.BaseDialogActivity;
import com.besome.sketch.lib.ui.SelectableButtonBar;
import com.sketchware.remod.R;
import com.sketchware.remod.databinding.ManageScreenActivityAddTempBinding;

import java.util.ArrayList;

import a.a.a.YB;
import a.a.a.bB;
import a.a.a.rq;
import a.a.a.uq;
import a.a.a.wB;
import a.a.a.xB;

public class AddViewActivity extends BaseDialogActivity {

    private YB nameValidator;
    private boolean featureStatusBar,
            featureToolbar,
            featureFab,
            featureDrawer;
    private int requestCode;
    private ProjectFileBean projectFileBean;
    private String P;
    private ArrayList<FeatureItem> featureItems;
    private FeaturesAdapter featuresAdapter;

    private ManageScreenActivityAddTempBinding binding;

    private void a(FeatureItem featureItem) {
        int type = featureItem.type;
        switch (type) {
            case 0 -> {
                if (featureItem.isEnabled) {
                    resetTranslationY(binding.previewStatusbar);
                    if (featureToolbar) {
                        resetTranslationY(binding.previewToolbar);
                    }
                } else {
                    slideInVertically(binding.previewStatusbar);
                    if (featureToolbar) {
                        binding.previewToolbar.animate().translationY((float) (-binding.previewStatusbar.getMeasuredHeight())).start();
                    } else {
                        slideOutPreviewToolbar();
                    }
                }
            }
            case 1 -> {
                if (featureItem.isEnabled) {
                    if (!featureStatusBar) {
                        binding.previewToolbar.animate().translationY((float) (-binding.previewStatusbar.getMeasuredHeight())).start();
                    } else {
                        resetTranslationY(binding.previewToolbar);
                    }
                } else if (!featureStatusBar) {
                    slideOutPreviewToolbar();
                } else {
                    slideInVertically(binding.previewToolbar);
                }
            }
            case 2 -> {
                if (featureItem.isEnabled) {
                    resetTranslationX(binding.previewDrawer);
                } else {
                    slideOutHorizontally(binding.previewDrawer);
                }
            }
            case 3 -> {
                if (featureItem.isEnabled) {
                    resetTranslationY(binding.previewFab);
                } else {
                    slideOutVertically(binding.previewFab);
                }
            }
        }
    }

    private boolean isValid(YB validator) {
        return validator.b();
    }

    private void slideOutHorizontally(View view) {
        view.animate().translationX((float) (-view.getMeasuredWidth())).start();
    }

    private void slideOutVertically(View view) {
        view.animate().translationY((float) view.getMeasuredHeight()).start();
    }

    private void slideOutPreviewToolbar() {
        binding.previewToolbar.animate().translationY((float) (-(binding.previewStatusbar.getMeasuredHeight() + binding.previewToolbar.getMeasuredHeight()))).start();
    }

    private void slideInVertically(View view) {
        view.animate().translationY((float) (-view.getMeasuredHeight())).start();
    }

    private void disableDrawer() {
        for (int i = 0; i < featureItems.size(); i++) {
            FeatureItem item = featureItems.get(i);
            if (item.type == 2) {
                item.isEnabled = false;
                featuresAdapter.notifyItemChanged(i);
                break;
            }
        }
    }

    private void enableToolbar() {
        for (int i = 0; i < featureItems.size(); i++) {
            FeatureItem item = featureItems.get(i);
            if (item.type == 1) {
                item.isEnabled = true;
                featuresAdapter.notifyItemChanged(i);
                break;
            }
        }
    }

    private void resetTranslationX(View view) {
        view.animate().translationX(0.0F).start();
    }

    private void resetTranslationY(View view) {
        view.animate().translationY(0.0F).start();
    }

    private ArrayList<ViewBean> getPresetData(String var1) {
        return rq.f(var1);
    }

    private void initItem(int option) {
        featureToolbar = (option & 1) == 1;
        featureStatusBar = (option & 2) != 2;
        featureFab = (option & 8) == 8;
        featureDrawer = (option & 4) == 4;
    }

    private void initializeItems() {
        featureItems = new ArrayList<>();
        featureItems.add(new FeatureItem(0, 2131165864, "StatusBar", featureStatusBar));
        featureItems.add(new FeatureItem(1, 2131165872, "Toolbar", featureToolbar));
        featureItems.add(new FeatureItem(2, 2131165737, "Drawer", featureDrawer));
        featureItems.add(new FeatureItem(3, 2131165608, "FAB", featureFab));
        featuresAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 276 && resultCode == -1) {
            ProjectFileBean presetData = data.getParcelableExtra("preset_data");
            P = presetData.presetName;
            initItem(presetData.options);
            initializeItems();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ManageScreenActivityAddTempBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        e(getTranslatedString(R.string.design_manager_view_title_add_view));
        Intent intent1 = getIntent();
        ArrayList<String> screenNames = intent1.getStringArrayListExtra("screen_names");
        requestCode = intent1.getIntExtra("request_code", 264);
        projectFileBean = intent1.getParcelableExtra("project_file");
        if (projectFileBean != null) {
            e(getTranslatedString(R.string.design_manager_view_title_edit_view));
        }

        binding.tvWarning.setVisibility(View.GONE);
        binding.tvWarning.setText(getTranslatedString(R.string.design_manager_view_message_slow_down));
        binding.tiName.setHint(getTranslatedString(R.string.design_manager_view_hint_enter_view_name));
        binding.edName.setPrivateImeOptions("defaultInputmode=english;");
        featuresAdapter = new FeaturesAdapter();
        binding.featureTypes.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        binding.featureTypes.setHasFixedSize(true);
        binding.featureTypes.setAdapter(featuresAdapter);
        binding.keyboardGroup.addOnButtonCheckedListener((group, checkedId, isChecked) ->
                updateKeyboardGroupState(group.getCheckedButtonId()));
        d(getTranslatedString(R.string.common_word_add));
        b(getTranslatedString(R.string.common_word_cancel));

        super.r.setOnClickListener(v -> {
            int options = ProjectFileBean.OPTION_ACTIVITY_TOOLBAR;
            if (265 == requestCode) {
                projectFileBean.orientation = getOrientationKey(binding.orientationGroup.getCheckedButtonId());
                projectFileBean.keyboardSetting = getKeyboardKey(binding.keyboardGroup.getCheckedButtonId());
                if (!featureToolbar) {
                    options = 0;
                }
                if (!featureStatusBar) {
                    options = options | 2;
                }
                if (featureFab) {
                    options = options | 8;
                }
                if (featureDrawer) {
                    options = options | 4;
                }
                projectFileBean.options = options;
                Intent intent = new Intent();
                intent.putExtra("project_file", projectFileBean);
                setResult(-1, intent);
                bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), R.string.design_manager_message_edit_complete, new Object[0]), 0).show();
                finish();
            } else if (isValid(nameValidator)) {
                String var4 = binding.edName.getText().toString() + getTypeSuffix(binding.typeGroup.getCheckedButtonId());
                int orientationKey = getOrientationKey(binding.orientationGroup.getCheckedButtonId());
                int keyboardKey = getKeyboardKey(binding.keyboardGroup.getCheckedButtonId());
                ProjectFileBean projectFileBean = new ProjectFileBean(0, var4,
                        orientationKey, keyboardKey,
                        featureToolbar, !featureStatusBar,
                        featureFab, featureDrawer);
                Intent intent = new Intent();
                intent.putExtra("project_file", projectFileBean);
                if (P != null) {
                    intent.putExtra("preset_views", getPresetData(P));
                }
                setResult(-1, intent);
                bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), 2131625276, new Object[0]), 0).show();
                finish();
            }

        });
        super.s.setOnClickListener(v -> {
            setResult(0);
            finish();
        });
        if (requestCode == 265) {
            nameValidator = new YB(getApplicationContext(), binding.tiName, uq.b, new ArrayList<>(), projectFileBean.fileName);
            binding.edName.setText(projectFileBean.fileName);
            binding.edName.setEnabled(false);
            binding.edName.setBackgroundResource(R.color.transparent);
            initItem(projectFileBean.options);
            binding.typeGroupWrapper.setVisibility(View.GONE);
            binding.orientationGroup.check(getOrientationIdByKey(projectFileBean.orientation));
            binding.keyboardGroup.check(getKeyboardIdByKey(projectFileBean.keyboardSetting));
            updateKeyboardGroupState(binding.keyboardGroup.getCheckedButtonId());
            super.r.setText(getTranslatedString(R.string.common_word_save).toUpperCase());
        } else {
            featureToolbar = true;
            featureStatusBar = true;
            nameValidator = new YB(getApplicationContext(), binding.tiName, uq.b, screenNames);
        }
        initializeItems();
    }

    private void updateKeyboardGroupState(int checkedId) {
        if (checkedId == R.id.keyboard_unspecified || checkedId == R.id.keyboard_visible) {
            resetTranslationY(binding.imgKeyboard);
        } else if (checkedId == R.id.keyboard_hidden) {
            // binding.activityPreview.animate().translationY((float) binding.imgKeyboard.getMeasuredHeight()).start();
            binding.imgKeyboard.animate().translationY((float) binding.imgKeyboard.getMeasuredHeight()).start();
        }
    }

    private String getTypeSuffix(int checkedId) {
        return switch (checkedId) {
            case R.id.type_fragment -> "_fragment";
            case R.id.type_dialog_fragment -> "_dialog_fragment";
            default -> "";
        };
    }

    private int getOrientationKey(int checkedId) {
        return switch (checkedId) {
            case R.id.orientation_landscape -> 1;
            case R.id.orientation_both -> 2;
            default -> 0; // portrait
        };
    }

    private @IdRes int getOrientationIdByKey(int key) {
        return switch (key) {
            case 1 -> R.id.orientation_landscape;
            case 2 -> R.id.orientation_both;
            default -> R.id.orientation_portrait;
        };
    }

    private int getKeyboardKey(int checkedId) {
        return switch (checkedId) {
            case R.id.keyboard_visible -> 1;
            case R.id.keyboard_hidden -> 2;
            default -> 0; // unspecified
        };
    }

    private @IdRes int getKeyboardIdByKey(int key) {
        return switch (key) {
            case 1 -> R.id.keyboard_visible;
            case 2 -> R.id.keyboard_hidden;
            default -> R.id.keyboard_unspecified;
        };
    }

    private static class FeatureItem {
        public int type;
        public int previewImg;
        public String name;
        public boolean isEnabled;

        public FeatureItem(int type, int previewImg, String name, boolean isEnabled) {
            this.type = type;
            this.previewImg = previewImg;
            this.name = name;
            this.isEnabled = isEnabled;
        }
    }

    public class FeaturesAdapter extends RecyclerView.Adapter<FeaturesAdapter.ViewHolder> {
        public int layoutPosition = -1;
        public boolean d;

        public FeaturesAdapter() {
        }

        @Override
        public int getItemCount() {
            return featureItems.size();
        }

        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            d = true;
            FeatureItem featureItem = featureItems.get(position);
            viewHolder.t.setImageResource(featureItem.previewImg);
            viewHolder.u.setText(featureItem.name);
            viewHolder.v.setChecked(featureItem.isEnabled);
            switch (featureItem.type) {
                case 0 -> featureStatusBar = featureItem.isEnabled;
                case 1 -> featureToolbar = featureItem.isEnabled;
                case 2 -> featureDrawer = featureItem.isEnabled;
                case 3 -> featureFab = featureItem.isEnabled;
            }

            if (featureFab || featureDrawer) {
                binding.tvWarning.setVisibility(View.VISIBLE);
            }

            a(featureItem);
            d = false;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup var1, int var2) {
            View var3 = wB.a(var1.getContext(), R.layout.manage_screen_activity_add_feature_item);
            var3.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
            return new ViewHolder(var3);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public ImageView t;
            public TextView u;
            public CheckBox v;

            public ViewHolder(View var2) {
                super(var2);
                t = var2.findViewById(R.id.img_icon);
                u = var2.findViewById(R.id.tv_name);
                v = var2.findViewById(R.id.checkbox);
                v.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (!d) {
                        layoutPosition = getLayoutPosition();
                        FeatureItem item = featureItems.get(layoutPosition);
                        binding.tvWarning.setVisibility(View.GONE);
                        item.isEnabled = isChecked;
                        if (item.type == 2 || item.isEnabled) {
                            enableToolbar();
                        } else if (item.type == 1 || !item.isEnabled) {
                            disableDrawer();
                        }
                        notifyItemChanged(layoutPosition);
                    }
                });
            }
        }
    }
}