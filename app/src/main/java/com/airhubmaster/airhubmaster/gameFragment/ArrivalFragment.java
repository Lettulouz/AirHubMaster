package com.airhubmaster.airhubmaster.gameFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airhubmaster.airhubmaster.R;
import com.airhubmaster.airhubmaster.adapter.ArrivalAdapter;
import com.airhubmaster.airhubmaster.adapter.ArrivalPersonnelAdapter;
import com.airhubmaster.airhubmaster.adapter.CategoryAdapter;
import com.airhubmaster.airhubmaster.dto.game.PersonnelDto;
import com.airhubmaster.airhubmaster.dto.game.PlaneDto;

import java.util.ArrayList;
import java.util.List;

public class ArrivalFragment extends Fragment {

    private RecyclerView recyclerViewArrival;
    private ArrivalAdapter arrivalAdapter;
    private List<PlaneDto> planeDtoList;
    private List<String> categories;
    private RecyclerView recyclerViewHorizontal;
    private CategoryAdapter categoryAdapter;

    public ArrivalFragment() {
    }

    public static ArrivalFragment newInstance(String param1, String param2) {
        ArrivalFragment fragment = new ArrivalFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewArrival = view.findViewById(R.id.recyclerViewArrival);
        recyclerViewHorizontal = view.findViewById(R.id.recyclerViewHorizontalArrival);
        recyclerViewArrival.setLayoutManager(new LinearLayoutManager(getContext()));
        planeDtoList = new ArrayList<>();

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

        // dodaj samoloty do listy
        planeDtoList.add(new PlaneDto("Samolot A", "Kategoria1", 0));
        planeDtoList.add(new PlaneDto("Samolot B", "Kategoria2", 0));

        arrivalAdapter = new ArrivalAdapter(getContext(), planeDtoList);
        recyclerViewArrival.setAdapter(arrivalAdapter);

        recyclerViewArrival.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                int action = e.getAction();
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child != null) {
                    int position = rv.getChildAdapterPosition(child);
                    View recyclerView = child.findViewById(R.id.personnel_recycler_view);
                    if (recyclerView != null) {
                        if (action == MotionEvent.ACTION_MOVE) {
                            rv.requestDisallowInterceptTouchEvent(true);
                        }
                    }
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {}

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_arrival, container, false);
    }

    private void filterPlanesByCategory(String category) {
        if (category == null) {
            arrivalAdapter.updatePlanes(planeDtoList);
        } else {
            List<PlaneDto> filteredPlaneDtos = new ArrayList<>();
            for (PlaneDto planeDto : planeDtoList) {
                if (planeDto.getCategory().equals(category)) {
                    filteredPlaneDtos.add(planeDto);
                }
            }
            arrivalAdapter.updatePlanes(filteredPlaneDtos);
        }
    }

}
