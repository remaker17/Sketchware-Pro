package mod.remaker.about.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.sketchware.remod.R;
import com.sketchware.remod.databinding.AboutTeamSectionBinding;

import mod.remaker.about.model.Contributor;
import mod.remaker.about.model.TeamSection;

import java.util.ArrayList;
import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> {
    private List<TeamSection> sections = new ArrayList<>();

    public TeamAdapter(List<TeamSection> sections) {
        this.sections = sections;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        AboutTeamSectionBinding binding = AboutTeamSectionBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TeamSection section = sections.get(position);
        holder.bind(section);
    }

    @Override
    public int getItemCount() {
        return sections != null ? sections.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private AboutTeamSectionBinding binding;
        private List<Contributor> contributors;

        public ViewHolder(AboutTeamSectionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            // just for testing purposes
            contributors = List.of(
                new Contributor("Remaker", "New About Activity 😎", null, false, true),
                new Contributor("Nicesapien", "Website Creator", null, false, false),
                new Contributor("Jbk0", "Reminder of the team", null, true, false),
                new Contributor("Hasrat", "uWu", null, true, true),
                new Contributor("Ilyasse", "Old About Activity 😭", null, false, false),
                new Contributor("Pranav", "uWu x2", null, false, true)
            );
        }

        public void bind(TeamSection section) {
            binding.groupView.setHeaderText(section.name);
            binding.recyclerView.setAdapter(new ContributorsAdapter(contributors));
        }
    }
}
