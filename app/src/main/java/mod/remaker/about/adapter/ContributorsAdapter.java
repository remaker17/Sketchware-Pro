package mod.remaker.about.adapter;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.color.MaterialColors;
import com.sketchware.remod.R;
import com.sketchware.remod.databinding.AboutContributorBinding;

import mod.remaker.about.model.Contributor;

import java.util.ArrayList;
import java.util.List;

public class ContributorsAdapter extends RecyclerView.Adapter<ContributorsAdapter.ViewHolder> {
    private List<Contributor> contributors = new ArrayList<>();

    public ContributorsAdapter(List<Contributor> contributors) {
        this.contributors = contributors;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        AboutContributorBinding binding = AboutContributorBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contributor contributor = contributors.get(position);
        holder.bind(contributor);
    }

    @Override
    public int getItemCount() {
        return contributors != null ? contributors.size() : 0;
    }

    public void submitList(List<Contributor> contributors) {
        this.contributors = contributors;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private AboutContributorBinding binding;

        public ViewHolder(AboutContributorBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Contributor contributor) {
            if (contributor.avatarUrl != null) {
                Glide.with(binding.avatar.getContext())
                    .load(contributor.avatarUrl)
                    .into(binding.avatar);
            }

            binding.name.setText(contributor.name);
            binding.description.setText(contributor.description);
            binding.avatar.setStrokeColor(ColorStateList.valueOf(defineStrokeColor(contributor)));
        }

        private int defineStrokeColor(Contributor contributor) {
            if (contributor.isMainModder) {
                return MaterialColors.getColor(itemView, R.attr.colorGreen);
            } else {
                return MaterialColors.getColor(itemView, R.attr.colorAmber);
            }
        }
    }
}
