package com.airhubmaster.airhubmaster.viewHolder;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.airhubmaster.airhubmaster.R;

public class ServiceViewHolder extends RecyclerView.ViewHolder {
    public TextView planeName;
    public TextView planeCategory;
    public ImageButton expandButton;
    public ConstraintLayout serviceLayout;

    public ServiceViewHolder(@NonNull View itemView) {
        super(itemView);
        planeName = itemView.findViewById(R.id.plane_name);
        planeCategory = itemView.findViewById(R.id.plane_category);
        expandButton = itemView.findViewById(R.id.expand_button);
        serviceLayout = itemView.findViewById(R.id.service_layout);
    }
}
