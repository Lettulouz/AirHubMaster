package com.airhubmaster.airhubmaster.gameFragment;

import com.airhubmaster.airhubmaster.adapter.CategoryAdapter;
import com.airhubmaster.airhubmaster.adapter.PlaneAdapter;
import com.airhubmaster.airhubmaster.dto.game.Plane;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.airhubmaster.airhubmaster.R;
import com.airhubmaster.airhubmaster.adapter.PlaneAdapter;

public class PlaneFragment extends Fragment{
    private RecyclerView recyclerView;
    private PlaneAdapter adapter;
    private List<Plane> planeList;
    private RecyclerView recyclerViewHorizontal;
    private CategoryAdapter categoryAdapter;
    private List<String> categories;

    public PlaneFragment() { }

    public static PlaneFragment newInstance() {
        PlaneFragment fragment = new PlaneFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plane, container, false);


        recyclerViewHorizontal = view.findViewById(R.id.recyclerViewHorizontal);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

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


        planeList = new ArrayList<>();
        // dodaj samoloty do listy
        planeList.add(new Plane("Samolot A","Kategoria1", 0));
        planeList.add(new Plane("Samolot B", "Kategoria2", 0));
        planeList.add(new Plane("Samolot C", "Kategoria3", 0));
        planeList.add(new Plane("Samolot B", "Kategoria4", 0));
        planeList.add(new Plane("Samolot C", "Kategoria5", 0));
        planeList.add(new Plane("Samolot B", "Kategoria1", 0));
        planeList.add(new Plane("Samolot C", "Kategoria2", 0));
        planeList.add(new Plane("Samolot B", "Kategoria3", 0));
        planeList.add(new Plane("Samolot C", "Kategoria4", 0));
        planeList.add(new Plane("Samolot B", "Kategoria5", 0));
        planeList.add(new Plane("Samolot C", "Kategoria1", 0));
        adapter = new PlaneAdapter(planeList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void filterPlanesByCategory(String category) {
        if (category == null) {
            adapter.updatePlanes(planeList); // zmień tutaj na adapter
        } else {
            List<Plane> filteredPlanes = new ArrayList<>();
            for (Plane plane : planeList) {
                if (plane.getCategory().equals(category)) {
                    filteredPlanes.add(plane);
                }
            }
            adapter.updatePlanes(filteredPlanes); // zmień tutaj na adapter
        }
    }
}