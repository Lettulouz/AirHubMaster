package com.airhubmaster.airhubmaster.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airhubmaster.airhubmaster.R;
import com.airhubmaster.airhubmaster.dto.game.PersonnelDto;
import com.airhubmaster.airhubmaster.viewHolder.BuyPersonnelViewHolder;

import java.util.List;

public class BuyPersonnelAdapter extends RecyclerView.Adapter<BuyPersonnelViewHolder> {

    private List<PersonnelDto> personnelList;

    public BuyPersonnelAdapter(List<PersonnelDto> personnelList) {
        this.personnelList = personnelList;
    }

    @NonNull
    @Override
    public BuyPersonnelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_buy_personnel, parent, false);
        return new BuyPersonnelViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BuyPersonnelViewHolder holder, int position) {
        PersonnelDto personnel = personnelList.get(position);

        holder.personCategory.setText(personnel.getCategory());
        holder.personName.setText(personnel.getName());
        holder.fieldD.setText("D " + personnel.getExperience());
        holder.fieldU.setText("U " + personnel.getSkills());
        holder.fieldW.setText("W " + personnel.getCooperation());
        holder.fieldB.setText("B " + personnel.getRebelliousness());

        holder.expandButton.setOnClickListener(v -> {
            if (holder.personStatsLayout.getVisibility() == View.GONE) {
                holder.personStatsLayout.setVisibility(View.VISIBLE);
                holder.expandButton.setBackgroundResource(R.drawable.collapse_icon);
            } else {
                holder.personStatsLayout.setVisibility(View.GONE);
                holder.expandButton.setBackgroundResource(R.drawable.expand_icon);
            }
        });

        holder.hireButton.setOnClickListener(v -> {
            // logika buttona
        });
    }

    @Override
    public int getItemCount() {
        return personnelList.size();
    }

    public void updatePersonnel(List<PersonnelDto> personnelDtoList) {
        this.personnelList = personnelDtoList;
        notifyDataSetChanged();
    }
}