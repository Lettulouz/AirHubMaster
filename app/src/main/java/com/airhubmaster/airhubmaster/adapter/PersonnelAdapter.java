package com.airhubmaster.airhubmaster.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airhubmaster.airhubmaster.R;
import com.airhubmaster.airhubmaster.dto.game.PersonnelBoughtDto;
import com.airhubmaster.airhubmaster.viewHolder.PersonnelViewHolder;

import java.util.List;

/**
 * An adapter that provides data binding from the Personnel class
 */
public class PersonnelAdapter extends RecyclerView.Adapter<PersonnelViewHolder> {

    /**
     * Variable declaration
     */
    private List<PersonnelBoughtDto> personnelBoughtDtoList;

    public PersonnelAdapter(List<PersonnelBoughtDto> personnelBoughtDtoList) {
        this.personnelBoughtDtoList = personnelBoughtDtoList;
    }

    @NonNull
    @Override
    public PersonnelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_personnel, parent, false);
        return new PersonnelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonnelViewHolder holder, int position) {
        PersonnelBoughtDto personnelBoughtDto = personnelBoughtDtoList.get(position);
        holder.personCategory.setText(personnelBoughtDto.getCategoryName());
        holder.personName.setText(personnelBoughtDto.getFullName());
        if (!personnelBoughtDto.isAvailable()) {
            holder.personCategory.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_custom_ticket_is_available, 0, 0, 0);
        } else {
            holder.personCategory.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_check_24, 0, 0, 0);
        }
        holder.expandButton.setOnClickListener(v -> {
            if (holder.personStatsLayout.getVisibility() == View.GONE) {
                holder.personStatsLayout.setVisibility(View.VISIBLE);
                holder.expandButton.setBackgroundResource(R.drawable.collapse_icon);
            } else {
                holder.personStatsLayout.setVisibility(View.GONE);
                holder.expandButton.setBackgroundResource(R.drawable.expand_icon);
            }
        });
        holder.fieldD.setText("D  " + personnelBoughtDto.getExperience());
        holder.fieldU.setText("U  " + personnelBoughtDto.getSkills());
        holder.fieldW.setText("W  " + personnelBoughtDto.getCooperation());
        holder.fieldB.setText("B  " + personnelBoughtDto.getRebelliousness());

    }

    @Override
    public int getItemCount() {
        return personnelBoughtDtoList.size();
    }

    public void updatePersonnel(List<PersonnelBoughtDto> personnelDtoList) {
        this.personnelBoughtDtoList = personnelDtoList;
        notifyDataSetChanged();
    }
}
