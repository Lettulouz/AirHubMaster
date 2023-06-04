package com.airhubmaster.airhubmaster.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airhubmaster.airhubmaster.R;
import com.airhubmaster.airhubmaster.dto.game.Personnel;
import com.airhubmaster.airhubmaster.dto.game.Plane;
import com.airhubmaster.airhubmaster.viewHolder.PersonnelViewHolder;

import java.util.List;

public class PersonnelAdapter extends RecyclerView.Adapter<PersonnelViewHolder> {
    private List<Personnel> personnelList;

    public PersonnelAdapter(List<Personnel> personnelList) {
        this.personnelList = personnelList;
    }

    @NonNull
    @Override
    public PersonnelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_personel, parent, false);
        return new PersonnelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonnelViewHolder holder, int position) {
        Personnel personnel = personnelList.get(position);
        holder.personCategory.setText(personnel.getCategory());
        holder.personName.setText(personnel.getName());
        holder.expandButton.setOnClickListener(v -> {
            if (holder.personStatsLayout.getVisibility() == View.GONE) {
                holder.personStatsLayout.setVisibility(View.VISIBLE);
                holder.expandButton.setBackgroundResource(R.drawable.collapse_icon);
            } else {
                holder.personStatsLayout.setVisibility(View.GONE);
                holder.expandButton.setBackgroundResource(R.drawable.expand_icon);
            }
        });
        holder.fieldD.setText("D  " + personnel.getExperience());
        holder.fieldU.setText("U  " + personnel.getSkills());
        holder.fieldW.setText("W  " + personnel.getCooperation());
        holder.fieldB.setText("B  " + personnel.getRebelliousness());

    }

    @Override
    public int getItemCount() {
        return personnelList.size();
    }

    public void updatePersonnel(List<Personnel> personnelList) {
        this.personnelList = personnelList;
        notifyDataSetChanged();
    }
}