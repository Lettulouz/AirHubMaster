package com.airhubmaster.airhubmaster.viewHolder;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.airhubmaster.airhubmaster.R;

/**
 * A ViewHolder containing the single item in the list for the plane
 */
public class PlaneViewHolder extends RecyclerView.ViewHolder {

    /**
     * Variable declaration
     */
    public TextView planeName, upgradeLevel, planeCategory;
    public ImageButton expandButton;
    public ImageView level1, level2, level3, level4, level5;
    public ConstraintLayout upgradeLayout;

    public PlaneViewHolder(View itemView) {
        super(itemView);
        planeName = itemView.findViewById(R.id.plane_name);
        planeCategory = itemView.findViewById(R.id.plane_category);
        upgradeLevel = itemView.findViewById(R.id.upgrade_level);
        expandButton = itemView.findViewById(R.id.expand_button);
        upgradeLayout = itemView.findViewById(R.id.upgrade_layout);
        level1 = itemView.findViewById(R.id.level_1);
        level2 = itemView.findViewById(R.id.level_2);
        level3 = itemView.findViewById(R.id.level_3);
        level4 = itemView.findViewById(R.id.level_4);
        level5 = itemView.findViewById(R.id.level_5);
    }
}
