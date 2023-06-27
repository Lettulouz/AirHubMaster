package com.airhubmaster.airhubmaster.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airhubmaster.airhubmaster.R;
import com.airhubmaster.airhubmaster.dto.game.PersonnelBoughtDto;
import com.airhubmaster.airhubmaster.viewHolder.ArrivalPersonnelViewHolder;

import java.util.List;

public class ArrivalPersonnelAdapter extends RecyclerView.Adapter<ArrivalPersonnelViewHolder> {

    private Context context;
    private List<PersonnelBoughtDto> personnelBoughtDtoList;

    public ArrivalPersonnelAdapter(Context context, List<PersonnelBoughtDto> personnelBoughtDtoList) {
        this.context = context;
        this.personnelBoughtDtoList = personnelBoughtDtoList;
    }

    @NonNull
    @Override
    public ArrivalPersonnelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_arrival_personnel, parent, false);
        return new ArrivalPersonnelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArrivalPersonnelViewHolder holder, int position) {
        PersonnelBoughtDto personnelBoughtDto = personnelBoughtDtoList.get(position);
        holder.nameTextView.setText(personnelBoughtDto.getFullName());
        holder.categoryTextView.setText(personnelBoughtDto.getCategoryName());
    }

    @Override
    public int getItemCount() {
        return personnelBoughtDtoList.size();
    }
}
