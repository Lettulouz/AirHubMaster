package com.airhubmaster.airhubmaster.adapter;

import static com.airhubmaster.airhubmaster.utils.Constans.parseJsonDate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airhubmaster.airhubmaster.R;
import com.airhubmaster.airhubmaster.dto.game.PlaneArrivalDto;
import com.airhubmaster.airhubmaster.viewHolder.ArrivalViewHolder;

import java.util.List;

public class ArrivalAdapter extends RecyclerView.Adapter<ArrivalViewHolder> {

    private List<PlaneArrivalDto> planeArrivalDtoList;
    private Context context;

    public ArrivalAdapter(Context context, List<PlaneArrivalDto> planeArrivalDtoList) {
        this.planeArrivalDtoList = planeArrivalDtoList;
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
        PlaneArrivalDto planeArrivalDto = planeArrivalDtoList.get(position);
        holder.planeNameTextView.setText(planeArrivalDto.getName());

        holder.arrival_dateTextView.setText("Data przylotu: " + parseJsonDate(planeArrivalDto.getArrival()));
        holder.planeCategoryTextView.setText(planeArrivalDto.getCategoryName());
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

        ArrivalPersonnelAdapter adapter = new ArrivalPersonnelAdapter(context, planeArrivalDto.getWorkers());
        holder.personnel_recycler_view.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return planeArrivalDtoList.size();
    }

    public void updatePlanes(List<PlaneArrivalDto> planeArrivalDtoList) {
        this.planeArrivalDtoList = planeArrivalDtoList;
        notifyDataSetChanged();
    }
}
