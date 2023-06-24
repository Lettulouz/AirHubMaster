package com.airhubmaster.airhubmaster.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airhubmaster.airhubmaster.R;
import com.airhubmaster.airhubmaster.dto.game.PersonnelShopDto;
import com.airhubmaster.airhubmaster.viewHolder.BuyPersonnelViewHolder;

import java.util.List;

public class BuyPersonnelAdapter extends RecyclerView.Adapter<BuyPersonnelViewHolder> {

    OnAcceptListener onAcceptListener;
    private List<PersonnelShopDto> personnelList;

    public BuyPersonnelAdapter(List<PersonnelShopDto> personnelList) {
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
        PersonnelShopDto personnel = personnelList.get(position);

        holder.personCategory.setText(personnel.getCategoryName());
        holder.personName.setText(personnel.getFullName());
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
            if (onAcceptListener != null) {
                onAcceptListener.onAcceptClick(personnel.getFullName(), personnel.getId(), personnel.getPrice());
            }
        });
    }

    @Override
    public int getItemCount() {
        return personnelList.size();
    }

    public void updatePersonnel(List<PersonnelShopDto> personnelDtoList) {
        this.personnelList = personnelDtoList;
        notifyDataSetChanged();
    }

    public void setOnAcceptListener(OnAcceptListener onAcceptListener) {
        this.onAcceptListener = onAcceptListener;
    }
}