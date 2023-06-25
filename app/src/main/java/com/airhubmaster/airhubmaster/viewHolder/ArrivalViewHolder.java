package com.airhubmaster.airhubmaster.viewHolder;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.airhubmaster.airhubmaster.R;

public class ArrivalViewHolder extends RecyclerView.ViewHolder {

    public TextView planeNameTextView;
    public TextView arrival_dateTextView;
    public TextView planeCategoryTextView;
    public ImageButton expandButton;
    public ConstraintLayout personnel_layout;
    public RecyclerView personnel_recycler_view;


    public ArrivalViewHolder(@NonNull View itemView) {
        super(itemView);
        planeNameTextView = itemView.findViewById(R.id.plane_name);
        arrival_dateTextView = itemView.findViewById(R.id.arrival_date);
        expandButton = itemView.findViewById(R.id.expand_button);
        planeCategoryTextView = itemView.findViewById(R.id.plane_category);
        personnel_recycler_view = itemView.findViewById(R.id.personnel_recycler_view);
        personnel_layout = itemView.findViewById(R.id.personnel_layout);
    }
}
