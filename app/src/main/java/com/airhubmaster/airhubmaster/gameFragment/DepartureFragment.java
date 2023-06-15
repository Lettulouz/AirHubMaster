package com.airhubmaster.airhubmaster.gameFragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airhubmaster.airhubmaster.R;
import com.airhubmaster.airhubmaster.adapter.CategoryAdapter;
import com.airhubmaster.airhubmaster.adapter.SendPlaneAdapter;
import com.airhubmaster.airhubmaster.dto.game.PlaneDto;

import java.util.ArrayList;
import java.util.List;

public class DepartureFragment extends Fragment {

    private RecyclerView recyclerViewDeparture;
    private SendPlaneAdapter sendPlaneAdapter;
    private List<PlaneDto> planeDtoList;
    private CategoryAdapter categoryAdapter;
    private RecyclerView recyclerViewHorizontal;
    private List<String> categories;

    public DepartureFragment() {
    }

    public static DepartureFragment newInstance(String param1, String param2) {
        DepartureFragment fragment = new DepartureFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewDeparture = view.findViewById(R.id.recyclerViewDeparture);

        recyclerViewHorizontal = view.findViewById(R.id.recyclerViewDepartureCategory);

        // Initialize the categories
        categories = new ArrayList<>();
        categories.add("Kategoria1");
        categories.add("Kategoria2");
        categories.add("Kategoria3");
        categories.add("Kategoria4");
        categories.add("Kategoria5");

        // Set the category adapter
        categoryAdapter = new CategoryAdapter(getContext(), categories, this::filterPlanesByCategory);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewHorizontal.setLayoutManager(layoutManager);
        recyclerViewHorizontal.setAdapter(categoryAdapter);

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
        initRecyclerView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_departure, container, false);
    }

    private void initRecyclerView() {
        recyclerViewDeparture.setLayoutManager(new LinearLayoutManager(getContext()));
        sendPlaneAdapter = new SendPlaneAdapter(planeDtoList, getContext());
        recyclerViewDeparture.setAdapter(sendPlaneAdapter);
    }

    private void filterPlanesByCategory(String category) {
        if (category == null) {
            sendPlaneAdapter.updatePlanes(planeDtoList);
        } else {
            List<PlaneDto> filteredPlaneDtos = new ArrayList<>();
            for (PlaneDto planeDto : planeDtoList) {
                if (planeDto.getCategory().equals(category)) {
                    filteredPlaneDtos.add(planeDto);
                }
            }
            sendPlaneAdapter.updatePlanes(filteredPlaneDtos);
        }
    }
}