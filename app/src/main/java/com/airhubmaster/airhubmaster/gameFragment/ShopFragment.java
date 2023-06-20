package com.airhubmaster.airhubmaster.gameFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airhubmaster.airhubmaster.R;
import com.airhubmaster.airhubmaster.adapter.BuyPersonnelAdapter;
import com.airhubmaster.airhubmaster.adapter.BuyPlaneAdapter;
import com.airhubmaster.airhubmaster.adapter.CategoryAdapter;
import com.airhubmaster.airhubmaster.dto.game.PersonnelDto;
import com.airhubmaster.airhubmaster.dto.game.PlaneDto;

import java.util.ArrayList;
import java.util.List;

public class ShopFragment extends Fragment {

    private RecyclerView recyclerViewShop;
    private RecyclerView recyclerViewCategoryShop;
    private List<PersonnelDto> personnelList;
    private List<PlaneDto> planeList;
    private Button buttonShowPersonel;
    private Button buttonShowPlane;
    private List<String> categories;
    private CategoryAdapter categoryAdapter;
    private String selectedCategory = null;

    public ShopFragment() {
    }

    public static ShopFragment newInstance() {
        return new ShopFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        personnelList = new ArrayList<>();
        planeList = new ArrayList<>();
        planeList.add(new PlaneDto("Samolot A", "Kategoria1", 0));
        planeList.add(new PlaneDto("Samolot B", "Kategoria2", 0));
        planeList.add(new PlaneDto("Samolot C", "Kategoria3", 0));
        planeList.add(new PlaneDto("Samolot B", "Kategoria4", 0));
        planeList.add(new PlaneDto("Samolot C", "Kategoria5", 0));
        personnelList.add(new PersonnelDto("Grzegorz Floryda", "Pilot", 69, 10, 20, 30));
        personnelList.add(new PersonnelDto("Jane Smith", "Stweardessa", 80, 50, 70, 90));
        personnelList.add(new PersonnelDto("Derek Chauvin", "Personel Naziemny", 40, 30, 40, 50));
        personnelList.add(new PersonnelDto("Bob Brown", "Personel Naziemny", 60, 80, 10, 20));
        categories = new ArrayList<>();
        categories.add("Pilot");
        categories.add("Stweardessa");
        categories.add("Personel Naziemny");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shop, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewShop = view.findViewById(R.id.recyclerViewShop);
        recyclerViewCategoryShop = view.findViewById(R.id.recyclerViewCategoryShop);
        buttonShowPersonel = view.findViewById(R.id.buttonShowPersonel);
        buttonShowPlane = view.findViewById(R.id.buttonShowPlane);

        recyclerViewShop.setHasFixedSize(true);
        recyclerViewShop.setLayoutManager(new LinearLayoutManager(getContext()));

        categoryAdapter = new CategoryAdapter(getContext(), categories, this::filterItemsByCategory);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryShop.setLayoutManager(layoutManager);
        recyclerViewCategoryShop.setAdapter(categoryAdapter);

        BuyPersonnelAdapter buypersonnelAdapter = new BuyPersonnelAdapter(personnelList);
        recyclerViewShop.setAdapter(buypersonnelAdapter);

        buttonShowPersonel.setOnClickListener(v -> {
            categories.clear();
            categories.add("Pilot");
            categories.add("Stweardessa");
            categories.add("Personel Naziemny");
            categoryAdapter = new CategoryAdapter(getContext(), categories, this::filterItemsByCategory);
            recyclerViewCategoryShop.setAdapter(categoryAdapter);
            recyclerViewShop.setAdapter(buypersonnelAdapter);
        });

        buttonShowPlane.setOnClickListener(v -> {
            categories.clear();
            categories.add("Kategoria1");
            categories.add("Kategoria2");
            categories.add("Kategoria3");
            categories.add("Kategoria4");
            categories.add("Kategoria5");
            categoryAdapter = new CategoryAdapter(getContext(), categories, this::filterItemsByCategory);
            recyclerViewCategoryShop.setAdapter(categoryAdapter);
            recyclerViewShop.setAdapter(new BuyPlaneAdapter(planeList));
        });
    }

    private void filterItemsByCategory(String category) {
        selectedCategory = category;
        if (selectedCategory == null) {
            if (recyclerViewShop.getAdapter() instanceof BuyPersonnelAdapter) {
                ((BuyPersonnelAdapter) recyclerViewShop.getAdapter()).updatePersonnel(personnelList);
            } else if (recyclerViewShop.getAdapter() instanceof BuyPlaneAdapter) {
                ((BuyPlaneAdapter) recyclerViewShop.getAdapter()).updatePlanes(planeList);
            }
        } else {
            if (recyclerViewShop.getAdapter() instanceof BuyPersonnelAdapter) {
                List<PersonnelDto> filteredPersonnelList = new ArrayList<>();
                for (PersonnelDto personnel : personnelList) {
                    if (personnel.getCategory().equals(selectedCategory)) {
                        filteredPersonnelList.add(personnel);
                    }
                }
                ((BuyPersonnelAdapter) recyclerViewShop.getAdapter()).updatePersonnel(filteredPersonnelList);
            } else if (recyclerViewShop.getAdapter() instanceof BuyPlaneAdapter) {
                List<PlaneDto> filteredPlaneList = new ArrayList<>();
                for (PlaneDto plane : planeList) {
                    if (plane.getCategory().equals(selectedCategory)) {
                        filteredPlaneList.add(plane);
                    }
                }
                ((BuyPlaneAdapter) recyclerViewShop.getAdapter()).updatePlanes(filteredPlaneList);
            }
        }
    }
}
