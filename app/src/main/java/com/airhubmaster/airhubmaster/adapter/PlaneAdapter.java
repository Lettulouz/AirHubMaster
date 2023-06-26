package com.airhubmaster.airhubmaster.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.airhubmaster.airhubmaster.R;
import com.airhubmaster.airhubmaster.dto.game.PlaneBoughtDto;
import com.airhubmaster.airhubmaster.viewHolder.PlaneViewHolder;

import java.util.List;

/**
 * An adapter that provides data binding from the Plane class
 */
public class PlaneAdapter extends RecyclerView.Adapter<PlaneViewHolder> {

    /**
     * Variable declaration
     */
    private List<PlaneBoughtDto> personnelBoughtDtoList;

    public PlaneAdapter(List<PlaneBoughtDto> personnelBoughtDtoList) {
        this.personnelBoughtDtoList = personnelBoughtDtoList;
    }

    @Override
    public PlaneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plane, parent, false);
        return new PlaneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlaneViewHolder holder, int position) {
        PlaneBoughtDto planeBoughtDto = personnelBoughtDtoList.get(position);
        holder.planeName.setText(planeBoughtDto.getPlaneName());
        holder.planeCategory.setText(planeBoughtDto.getCategoryName());
        holder.upgradeLevel.setText(holder.itemView.getContext().getString(R.string.upgrade_level, planeBoughtDto.getUpgrade()));
        holder.expandButton.setOnClickListener(v -> {
            if (holder.upgradeLayout.getVisibility() == View.GONE) {
                holder.upgradeLayout.setVisibility(View.VISIBLE);
                holder.expandButton.setBackgroundResource(R.drawable.collapse_icon);
            } else {
                holder.upgradeLayout.setVisibility(View.GONE);
                holder.expandButton.setBackgroundResource(R.drawable.expand_icon);
            }
        });

        holder.itemView.findViewById(R.id.upgrade_button).setOnClickListener(v -> {
            planeBoughtDto.upgrade();
            holder.upgradeLevel.setText(holder.itemView.getContext().getString(R.string.upgrade_level, planeBoughtDto.getUpgrade()));
            updateLevelImages(holder, planeBoughtDto.getUpgrade());
        });

        updateLevelImages(holder, planeBoughtDto.getUpgrade());
    }

    private void updateLevelImages(PlaneViewHolder holder, int level) {
        ImageView[] levelImages = {holder.level1, holder.level2, holder.level3, holder.level4, holder.level5};
        for (int i = 0; i < levelImages.length; i++) {
            levelImages[i].setBackgroundResource(i < level ? R.drawable.level_active : R.drawable.level_inactive);
        }
    }

    @Override
    public int getItemCount() {
        return personnelBoughtDtoList.size();
    }

    public void updatePlanes(List<PlaneBoughtDto> planeDtos) {
        this.personnelBoughtDtoList = planeDtos;
        notifyDataSetChanged();
    }
}
