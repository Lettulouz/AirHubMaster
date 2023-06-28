package com.airhubmaster.airhubmaster.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.airhubmaster.airhubmaster.R;
import com.airhubmaster.airhubmaster.dto.game.PlaneSetRoutDto;
import com.airhubmaster.airhubmaster.viewHolder.SendPlaneViewHolder;

import java.util.ArrayList;
import java.util.List;

public class SendPlaneAdapter extends RecyclerView.Adapter<SendPlaneViewHolder> {

    int routId;
    OnSendPlaneListener onSendPlaneListener;
    List<PlaneSetRoutDto> planes;
    Context context;

    public SendPlaneAdapter(ArrayList<PlaneSetRoutDto> planes, Context context) {
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
        PlaneSetRoutDto planeSetRoutDto = planes.get(position);
        holder.planeName.setText(planeSetRoutDto.getPlaneName());
        holder.planeCategory.setText(planeSetRoutDto.getPlaneCategory());
        holder.expandButton.setOnClickListener(v -> {
            if (holder.upgradeLayout.getVisibility() == View.GONE) {
                holder.textViewRout1.setText("Czas lotu: " + String.valueOf(planeSetRoutDto.getRoutes().get(0).getHours()) + "h");
                holder.textViewRout2.setText("Czas lotu: " + String.valueOf(planeSetRoutDto.getRoutes().get(1).getHours()) + "h");
                holder.textViewRout3.setText("Czas lotu: " + String.valueOf(planeSetRoutDto.getRoutes().get(2).getHours()) + "h");
                holder.button_route_1.setOnClickListener(v15 -> {
                    routId = planeSetRoutDto.getRoutes().get(0).getId();
                    holder.button_route_1.setBackgroundColor(ContextCompat.getColor(context, R.color.level_active_color));
                    holder.button_route_2.setBackgroundColor(ContextCompat.getColor(context, R.color.level_inactive_stroke_color));
                    holder.button_route_3.setBackgroundColor(ContextCompat.getColor(context, R.color.level_inactive_stroke_color));
                });
                holder.button_route_2.setOnClickListener(v15 -> {
                    routId = planeSetRoutDto.getRoutes().get(1).getId();
                    holder.button_route_1.setBackgroundColor(ContextCompat.getColor(context, R.color.level_inactive_stroke_color));
                    holder.button_route_2.setBackgroundColor(ContextCompat.getColor(context, R.color.level_active_color));
                    holder.button_route_3.setBackgroundColor(ContextCompat.getColor(context, R.color.level_inactive_stroke_color));
                });
                holder.button_route_3.setOnClickListener(v15 -> {
                    routId = planeSetRoutDto.getRoutes().get(2).getId();
                    holder.button_route_1.setBackgroundColor(ContextCompat.getColor(context, R.color.level_inactive_stroke_color));
                    holder.button_route_2.setBackgroundColor(ContextCompat.getColor(context, R.color.level_inactive_stroke_color));
                    holder.button_route_3.setBackgroundColor(ContextCompat.getColor(context, R.color.level_active_color));
                });

                holder.upgradeLayout.setVisibility(View.VISIBLE);
                holder.expandButton.setBackgroundResource(R.drawable.collapse_icon);

                holder.send_plane.setOnClickListener(v1 -> {
                    if (onSendPlaneListener != null) {
                        onSendPlaneListener.onFlightClick(planeSetRoutDto.getPlaneId(), planeSetRoutDto.getCrew(), routId);
                    }
                });

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

    public void updatePlanes(List<PlaneSetRoutDto> planeDtos) {
        this.planes = planeDtos;
        notifyDataSetChanged();
    }

    public void setOnSendPlaneListener(OnSendPlaneListener onSendPlaneListener) {
        this.onSendPlaneListener = onSendPlaneListener;
    }
}
