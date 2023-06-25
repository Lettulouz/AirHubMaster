package com.airhubmaster.airhubmaster.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airhubmaster.airhubmaster.R;
import com.airhubmaster.airhubmaster.dto.game.PersonnelDto;
import com.airhubmaster.airhubmaster.dto.game.PlaneDto;
import com.airhubmaster.airhubmaster.viewHolder.ArrivalViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ArrivalAdapter extends RecyclerView.Adapter<ArrivalViewHolder> {

    private List<PlaneDto> planeDtoList;
    private Context context;

    public ArrivalAdapter(Context context, List<PlaneDto> planeDtoList) {
        this.planeDtoList = planeDtoList;
        this.context = context;
    }

    @NonNull
    @Override
    public ArrivalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_arrival, parent, false);
        return new ArrivalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArrivalViewHolder holder, int position) {
        PlaneDto planeDto = planeDtoList.get(position);
        holder.planeNameTextView.setText(planeDto.getName());
        holder.arrival_dateTextView.setText("Data przylotu: 11.10.2001 9:59"); // Placeholder dla daty przylotu
        holder.planeCategoryTextView.setText(planeDto.getCategory());
        holder.personnel_recycler_view.setLayoutManager(new LinearLayoutManager(context));

        holder.expandButton.setOnClickListener(v -> {
            if (holder.personnel_layout.getVisibility() == View.GONE) {
                holder.personnel_layout.setVisibility(View.VISIBLE);
                holder.expandButton.setBackgroundResource(R.drawable.collapse_icon);
            } else {
                holder.personnel_layout.setVisibility(View.GONE);
                holder.expandButton.setBackgroundResource(R.drawable.expand_icon);
            }
        });

        // Inicjalizuj personnel_list_view i ustaw adapter dla niego
        List<PersonnelDto> personnelDtoList = new ArrayList<>();
        personnelDtoList.add(new PersonnelDto("Grzegorz Floryda", "Pilot", 69, 10, 20, 30));
        personnelDtoList.add(new PersonnelDto("Jane Smith", "Stweardessa", 80, 50, 70, 90));
        personnelDtoList.add(new PersonnelDto("Derek Chauvin", "Personel Naziemny", 40, 30, 40, 50));
        personnelDtoList.add(new PersonnelDto("Bob Brown", "Personel Naziemny", 60, 80, 10, 20));

        ArrivalPersonnelAdapter adapter = new ArrivalPersonnelAdapter(context, personnelDtoList);
        holder.personnel_recycler_view.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return planeDtoList.size();
    }

    public void updatePlanes(List<PlaneDto> planeDtos) {
        this.planeDtoList = planeDtos;
        notifyDataSetChanged();
    }
}
