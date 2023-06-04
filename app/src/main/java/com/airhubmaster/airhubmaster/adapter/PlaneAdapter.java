package com.airhubmaster.airhubmaster.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.airhubmaster.airhubmaster.R;
import com.airhubmaster.airhubmaster.dto.game.Plane;
import com.airhubmaster.airhubmaster.viewHolder.PlaneViewHolder;

import java.util.List;

public class PlaneAdapter extends RecyclerView.Adapter<PlaneViewHolder> {

    private List<Plane> planes;

    public PlaneAdapter(List<Plane> planes) {
        this.planes = planes;
    }

    @Override
    public PlaneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plane, parent, false);
        return new PlaneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlaneViewHolder holder, int position) {
        Plane plane = planes.get(position);
        holder.planeName.setText(plane.getName());
        holder.planeCategory.setText(plane.getCategory());
        holder.upgradeLevel.setText(holder.itemView.getContext().getString(R.string.upgrade_level, plane.getUpgradeLevel()));
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
            plane.upgrade();
            holder.upgradeLevel.setText(holder.itemView.getContext().getString(R.string.upgrade_level, plane.getUpgradeLevel()));
            updateLevelImages(holder, plane.getUpgradeLevel());
        });

        updateLevelImages(holder, plane.getUpgradeLevel());
    }

    private void updateLevelImages(PlaneViewHolder holder, int level) {
        ImageView[] levelImages = {holder.level1, holder.level2, holder.level3, holder.level4, holder.level5};
        for (int i = 0; i < levelImages.length; i++) {
            levelImages[i].setBackgroundResource(i < level ? R.drawable.level_active : R.drawable.level_inactive);
        }
    }

    @Override
    public int getItemCount() {
        return planes.size();
    }

    public void updatePlanes(List<Plane> planes) {
        this.planes = planes;
        notifyDataSetChanged();
    }
}