package com.airhubmaster.airhubmaster.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airhubmaster.airhubmaster.R;
import com.airhubmaster.airhubmaster.dto.game.PersonnelDto;
import com.airhubmaster.airhubmaster.viewHolder.PersonnelViewHolder;

import java.util.List;

/**
 * An adapter that provides data binding from the Personnel class
 */
public class PersonnelAdapter extends RecyclerView.Adapter<PersonnelViewHolder> {

    /**
     * Variable declaration
     */
    private List<PersonnelDto> personnelDtoList;

    public PersonnelAdapter(List<PersonnelDto> personnelDtoList) {
        this.personnelDtoList = personnelDtoList;
    }

    @NonNull
    @Override
    public PersonnelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_personnel, parent, false);
        return new PersonnelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonnelViewHolder holder, int position) {
        PersonnelDto personnelDto = personnelDtoList.get(position);
        holder.personCategory.setText(personnelDto.getCategory());
        holder.personName.setText(personnelDto.getName());
        holder.expandButton.setOnClickListener(v -> {
            if (holder.personStatsLayout.getVisibility() == View.GONE) {
                holder.personStatsLayout.setVisibility(View.VISIBLE);
                holder.expandButton.setBackgroundResource(R.drawable.collapse_icon);
            } else {
                holder.personStatsLayout.setVisibility(View.GONE);
                holder.expandButton.setBackgroundResource(R.drawable.expand_icon);
            }
        });
        holder.fieldD.setText("D  " + personnelDto.getExperience());
        holder.fieldU.setText("U  " + personnelDto.getSkills());
        holder.fieldW.setText("W  " + personnelDto.getCooperation());
        holder.fieldB.setText("B  " + personnelDto.getRebelliousness());

    }

    @Override
    public int getItemCount() {
        return personnelDtoList.size();
    }

    public void updatePersonnel(List<PersonnelDto> personnelDtoList) {
        this.personnelDtoList = personnelDtoList;
        notifyDataSetChanged();
    }
}
