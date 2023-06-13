package com.airhubmaster.airhubmaster.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airhubmaster.airhubmaster.R;
import com.airhubmaster.airhubmaster.dto.game.PlaneDto;
import com.airhubmaster.airhubmaster.viewHolder.ServiceViewHolder;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceViewHolder> {
    private List<PlaneDto> planeList;

    public ServiceAdapter(List<PlaneDto> planeList) {
        this.planeList = planeList;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        PlaneDto plane = planeList.get(position);
        holder.planeName.setText(plane.getName());
        holder.planeCategory.setText(plane.getCategory());

        holder.expandButton.setOnClickListener(v -> {
            if (holder.serviceLayout.getVisibility() == View.GONE) {
                holder.serviceLayout.setVisibility(View.VISIBLE);
                holder.expandButton.setBackgroundResource(R.drawable.collapse_icon);
            } else {
                holder.serviceLayout.setVisibility(View.GONE);
                holder.expandButton.setBackgroundResource(R.drawable.expand_icon);
            }
        });
    }

    @Override
    public int getItemCount() {
        return planeList.size();
    }

    public void updatePlanes(List<PlaneDto> planeDtos) {
        this.planeList = planeDtos;
        notifyDataSetChanged();
    }
}