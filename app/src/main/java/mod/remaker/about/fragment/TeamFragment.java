package mod.remaker.about.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sketchware.remod.databinding.AboutTeamBinding;

import mod.remaker.about.adapter.TeamAdapter;
import mod.remaker.about.model.TeamSection;

import java.util.List;

public class TeamFragment extends Fragment {
    private AboutTeamBinding binding;
    private List<TeamSection> teamSections;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = AboutTeamBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initializeSections();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initializeSections() {
        teamSections = List.of(
            new TeamSection("Main modders", "https://github.com/Sketchware-Pro/Sketchware-Pro/tree/host/aboutus.json"),
            new TeamSection("Special thanks", "https://github.com/Sketchware-Pro/Sketchware-Pro/tree/host/aboutus.json")
        );
        binding.recyclerView.setAdapter(new TeamAdapter(teamSections));
    }
}
