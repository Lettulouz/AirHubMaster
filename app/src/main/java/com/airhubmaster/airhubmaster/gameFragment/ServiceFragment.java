package com.airhubmaster.airhubmaster.gameFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.airhubmaster.airhubmaster.R;
import com.airhubmaster.airhubmaster.adapter.CategoryAdapter;
import com.airhubmaster.airhubmaster.adapter.PlaneAdapter;
import com.airhubmaster.airhubmaster.adapter.ServiceAdapter;
import com.airhubmaster.airhubmaster.dto.game.PlaneDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServiceFragment extends Fragment {
    private RecyclerView recyclerView;
    private ServiceAdapter adapter;
    private List<PlaneDto> planeDtoList;
    private RecyclerView recyclerViewHorizontal;
    private CategoryAdapter categoryAdapter;
    private List<String> categories;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerViewHorizontal = view.findViewById(R.id.recyclerViewHorizontal);

        // Initialize the categories
        categories = new ArrayList<>();
        categories.add("Kategoria1");
        categories.add("Kategoria2");
        categories.add("Kategoria3");
        categories.add("Kategoria4");
        categories.add("Kategoria5");

        planeDtoList = new ArrayList<>();
        // dodaj samoloty do listy
        planeDtoList.add(new PlaneDto("Samolot A", "Kategoria1", 0));
        planeDtoList.add(new PlaneDto("Samolot B", "Kategoria2", 0));
        planeDtoList.add(new PlaneDto("Samolot C", "Kategoria3", 0));
        planeDtoList.add(new PlaneDto("Samolot B", "Kategoria4", 0));
        planeDtoList.add(new PlaneDto("Samolot C", "Kategoria5", 0));
        planeDtoList.add(new PlaneDto("Samolot B", "Kategoria1", 0));
        planeDtoList.add(new PlaneDto("Samolot C", "Kategoria2", 0));
        planeDtoList.add(new PlaneDto("Samolot B", "Kategoria3", 0));
        planeDtoList.add(new PlaneDto("Samolot C", "Kategoria4", 0));
        planeDtoList.add(new PlaneDto("Samolot B", "Kategoria5", 0));
        planeDtoList.add(new PlaneDto("Samolot C", "Kategoria1", 0));
        adapter = new ServiceAdapter(planeDtoList);
        recyclerView.setAdapter(adapter);


        // Set the category adapter
        categoryAdapter = new CategoryAdapter(getContext(), categories, this::filterPlanesByCategory);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewHorizontal.setLayoutManager(layoutManager);
        recyclerViewHorizontal.setAdapter(categoryAdapter);

        return view;
    }

    private void filterPlanesByCategory(String category) {
        if (category == null) {
            adapter.updatePlanes(planeDtoList); // zmień tutaj na adapter
        } else {
            List<PlaneDto> filteredPlaneDtos = new ArrayList<>();
            for (PlaneDto planeDto : planeDtoList) {
                if (planeDto.getCategory().equals(category)) {
                    filteredPlaneDtos.add(planeDto);
                }
            }
            adapter.updatePlanes(filteredPlaneDtos); // zmień tutaj na adapter
        }
    }
}
