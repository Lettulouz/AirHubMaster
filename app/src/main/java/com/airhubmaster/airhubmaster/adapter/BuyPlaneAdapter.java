package com.airhubmaster.airhubmaster.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airhubmaster.airhubmaster.R;
import com.airhubmaster.airhubmaster.dto.game.PlaneShopDto;
import com.airhubmaster.airhubmaster.viewHolder.BuyPlaneViewHolder;

import java.util.List;

public class BuyPlaneAdapter extends RecyclerView.Adapter<BuyPlaneViewHolder> {

    OnAcceptListener onAcceptListener;
    private List<PlaneShopDto> planeList;

    public BuyPlaneAdapter(List<PlaneShopDto> planeList) {
        this.planeList = planeList;
    }

    @NonNull
    @Override
    public BuyPlaneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_buy_plane, parent, false);
        return new BuyPlaneViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BuyPlaneViewHolder holder, int position) {
        PlaneShopDto plane = planeList.get(position);

        holder.planeName.setText(plane.getPlaneName());
        holder.planePrice.setText("Cena: " + String.valueOf(plane.getPrice()));
        holder.planeCategory.setText(plane.getCategoryName());

        holder.expandButton.setOnClickListener(v -> {
            if (holder.upgradeLayout.getVisibility() == View.GONE) {
                holder.upgradeLayout.setVisibility(View.VISIBLE);
                holder.expandButton.setBackgroundResource(R.drawable.collapse_icon);
            } else {
                holder.upgradeLayout.setVisibility(View.GONE);
                holder.expandButton.setBackgroundResource(R.drawable.expand_icon);
            }
        });

        holder.buy_button.setOnClickListener(v -> {
            if (onAcceptListener != null) {
                onAcceptListener.onAcceptClick(plane.getPlaneName(), plane.getId(), plane.getPrice());
            }
        });
    }

    @Override
    public int getItemCount() {
        return planeList.size();
    }

    public void updatePlanes(List<PlaneShopDto> planeDtos) {
        this.planeList = planeDtos;
        notifyDataSetChanged();
    }

    public void setOnAcceptListener(OnAcceptListener onAcceptListener) {
        this.onAcceptListener = onAcceptListener;
    }
}
