package com.airhubmaster.airhubmaster.gameFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airhubmaster.airhubmaster.R;
import com.airhubmaster.airhubmaster.adapter.CategoryAdapter;
import com.airhubmaster.airhubmaster.adapter.PersonnelAdapter;
import com.airhubmaster.airhubmaster.dto.game.PersonnelDto;

import java.util.ArrayList;
import java.util.List;

public class PersonnelFragment extends Fragment {
    private RecyclerView recyclerView;
    private PersonnelAdapter adapter;
    private List<PersonnelDto> personnelDtoList;
    private RecyclerView recyclerViewHorizontal;
    private CategoryAdapter categoryAdapter;
    private List<String> categories;

    public PersonnelFragment() {
    }

    public static PersonnelFragment newInstance() {
        PersonnelFragment fragment = new PersonnelFragment();
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
        View view = inflater.inflate(R.layout.fragment_personnel, container, false);

        recyclerViewHorizontal = view.findViewById(R.id.recyclerViewHorizontal);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the categories
        categories = new ArrayList<>();
        categories.add("Pilot");
        categories.add("Stweardessa");
        categories.add("Personel Naziemny");

        // Set the category adapter
        categoryAdapter = new CategoryAdapter(getContext(), categories, this::filterPersonnelByCategory);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewHorizontal.setLayoutManager(layoutManager);
        recyclerViewHorizontal.setAdapter(categoryAdapter);

        personnelDtoList = new ArrayList<>();
        // Add personnel to the list
        personnelDtoList.add(new PersonnelDto("Grzegorz Floryda", "Pilot", 69, 10, 20, 30));
        personnelDtoList.add(new PersonnelDto("Jane Smith", "Stweardessa", 80, 50, 70, 90));
        personnelDtoList.add(new PersonnelDto("Derek Chauvin", "Personel Naziemny", 40, 30, 40, 50));
        personnelDtoList.add(new PersonnelDto("Bob Brown", "Personel Naziemny", 60, 80, 10, 20));

        adapter = new PersonnelAdapter(personnelDtoList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void filterPersonnelByCategory(String category) {
        if (category == null) {
            adapter.updatePersonnel(personnelDtoList);
        } else {
            List<PersonnelDto> filteredPersonnelDto = new ArrayList<>();
            for (PersonnelDto personnelDto : personnelDtoList) {
                if (personnelDto.getCategory().equals(category)) {
                    filteredPersonnelDto.add(personnelDto);
                }
            }
            adapter.updatePersonnel(filteredPersonnelDto);
        }
    }
}
