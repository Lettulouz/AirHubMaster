package com.airhubmaster.airhubmaster.viewHolder;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.airhubmaster.airhubmaster.R;

public class PersonnelViewHolder extends RecyclerView.ViewHolder {

    ImageView personIcon;
    public TextView personCategory;
    public TextView personName;
    public ImageButton expandButton;
    public ConstraintLayout personStatsLayout;
    public TextView fieldD;
    public TextView fieldU;
    public TextView fieldW;
    public TextView fieldB;

    public PersonnelViewHolder(@NonNull View itemView) {
        super(itemView);
        personIcon = itemView.findViewById(R.id.person_icon);
        personCategory = itemView.findViewById(R.id.person_category);
        personName = itemView.findViewById(R.id.person_name);
        expandButton = itemView.findViewById(R.id.expand_button);
        personStatsLayout = itemView.findViewById(R.id.person_stats_layout);
        fieldD = itemView.findViewById(R.id.field_D);
        fieldU = itemView.findViewById(R.id.field_U);
        fieldW = itemView.findViewById(R.id.field_W);
        fieldB = itemView.findViewById(R.id.field_B);
    }
}