package com.airhubmaster.airhubmaster.viewHolder;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.airhubmaster.airhubmaster.R;
import com.airhubmaster.airhubmaster.dto.game.PlaneDto;

public class SendPlaneViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView planeName, upgradeLevel, planeCategory;
    public ImageButton expandButton;
    public ConstraintLayout upgradeLayout;
    private Button button_route_1;
    private Button send_plane;
    private Button button_route_2;
    private Button button_route_3;
    private Context context;

    public SendPlaneViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        planeName = itemView.findViewById(R.id.plane_name);
        planeCategory = itemView.findViewById(R.id.plane_category);
        send_plane = itemView.findViewById(R.id.send_plane);
        expandButton = itemView.findViewById(R.id.expand_button);
        upgradeLayout = itemView.findViewById(R.id.upgrade_layout);
        button_route_1 = itemView.findViewById(R.id.button_route_1);
        button_route_2 = itemView.findViewById(R.id.button_route_2);
        button_route_3 = itemView.findViewById(R.id.button_route_3);

        button_route_1.setOnClickListener(this);
        button_route_2.setOnClickListener(this);
        button_route_3.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button_route_1) {
            button_route_1.setBackgroundColor(ContextCompat.getColor(context, R.color.level_active_color));
            button_route_2.setBackgroundColor(ContextCompat.getColor(context, R.color.level_inactive_stroke_color));
            button_route_3.setBackgroundColor(ContextCompat.getColor(context, R.color.level_inactive_stroke_color));
        } else if (id == R.id.button_route_2) {
            button_route_1.setBackgroundColor(ContextCompat.getColor(context, R.color.level_inactive_stroke_color));
            button_route_2.setBackgroundColor(ContextCompat.getColor(context, R.color.level_active_color));
            button_route_3.setBackgroundColor(ContextCompat.getColor(context, R.color.level_inactive_stroke_color));
        } else if (id == R.id.button_route_3) {
            button_route_1.setBackgroundColor(ContextCompat.getColor(context, R.color.level_inactive_stroke_color));
            button_route_2.setBackgroundColor(ContextCompat.getColor(context, R.color.level_inactive_stroke_color));
            button_route_3.setBackgroundColor(ContextCompat.getColor(context, R.color.level_active_color));
        }
    }
}