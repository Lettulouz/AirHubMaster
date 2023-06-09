package com.airhubmaster.airhubmaster.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airhubmaster.airhubmaster.R;
import com.airhubmaster.airhubmaster.dto.game.PlaneServiceDto;
import com.airhubmaster.airhubmaster.viewHolder.ServiceViewHolder;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceViewHolder> {
    private List<PlaneServiceDto> planeServiceDtoList;

    public ServiceAdapter(List<PlaneServiceDto> planeServiceDtoList) {
        this.planeServiceDtoList = planeServiceDtoList;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        PlaneServiceDto planeServiceDto = planeServiceDtoList.get(position);
        holder.planeName.setText(planeServiceDto.getName());
        holder.planeCategory.setText(planeServiceDto.getCategoryName());

        holder.expandButton.setOnClickListener(v -> {
            if (holder.serviceLayout.getVisibility() == View.GONE) {
                holder.serviceLayout.setVisibility(View.VISIBLE);
                holder.expandButton.setBackgroundResource(R.drawable.collapse_icon);
                holder.engine.setText(String.valueOf(planeServiceDto.getEngine()) + "%");
                holder.gear.setText(String.valueOf(planeServiceDto.getLandingGeer()) + "%");
                holder.wing.setText(String.valueOf(planeServiceDto.getWings()) + "%");
            } else {
                holder.serviceLayout.setVisibility(View.GONE);
                holder.expandButton.setBackgroundResource(R.drawable.expand_icon);
            }
        });
    }

    @Override
    public int getItemCount() {
        return planeServiceDtoList.size();
    }

    public void updatePlanes(List<PlaneServiceDto> planeServiceDtoList) {
        this.planeServiceDtoList = planeServiceDtoList;
        notifyDataSetChanged();
    }
}