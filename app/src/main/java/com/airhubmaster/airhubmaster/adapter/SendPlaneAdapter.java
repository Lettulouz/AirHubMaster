package com.airhubmaster.airhubmaster.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airhubmaster.airhubmaster.R;
import com.airhubmaster.airhubmaster.dto.game.PlaneDto;
import com.airhubmaster.airhubmaster.viewHolder.SendPlaneViewHolder;

import java.util.List;

public class SendPlaneAdapter extends RecyclerView.Adapter<SendPlaneViewHolder> {

    private List<PlaneDto> planes;
    private Context context;

    public SendPlaneAdapter(List<PlaneDto> planes, Context context) {
        this.planes = planes;
        this.context = context;
    }

    @NonNull
    @Override
    public SendPlaneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_send_plane, parent, false);
        return new SendPlaneViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull SendPlaneViewHolder holder, int position) {
        PlaneDto planeDto = planes.get(position);
        holder.planeName.setText(planeDto.getName());
        holder.planeCategory.setText(planeDto.getCategory());
        holder.expandButton.setOnClickListener(v -> {
            if (holder.upgradeLayout.getVisibility() == View.GONE) {
                holder.upgradeLayout.setVisibility(View.VISIBLE);
                holder.expandButton.setBackgroundResource(R.drawable.collapse_icon);
            } else {
                holder.upgradeLayout.setVisibility(View.GONE);
                holder.expandButton.setBackgroundResource(R.drawable.expand_icon);
            }
        });

    }

    @Override
    public int getItemCount() {
        return planes.size();
    }

    public void updatePlanes(List<PlaneDto> planeDtos) {
        this.planes = planeDtos;
        notifyDataSetChanged();
    }
}