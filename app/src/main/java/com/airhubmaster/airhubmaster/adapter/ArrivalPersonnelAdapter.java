package com.airhubmaster.airhubmaster.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airhubmaster.airhubmaster.R;
import com.airhubmaster.airhubmaster.dto.game.PersonnelDto;
import com.airhubmaster.airhubmaster.viewHolder.ArrivalPersonnelViewHolder;

import java.util.List;

public class ArrivalPersonnelAdapter extends RecyclerView.Adapter<ArrivalPersonnelViewHolder> {

    private Context context;
    private List<PersonnelDto> personnelDtoList;

    public ArrivalPersonnelAdapter(Context context, List<PersonnelDto> personnelDtoList) {
        this.context = context;
        this.personnelDtoList = personnelDtoList;
    }

    @NonNull
    @Override
    public ArrivalPersonnelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_arrival_personnel, parent, false);
        return new ArrivalPersonnelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArrivalPersonnelViewHolder holder, int position) {
        PersonnelDto personnelDto = personnelDtoList.get(position);
        holder.nameTextView.setText(personnelDto.getName());
        holder.categoryTextView.setText(personnelDto.getCategory());
    }

    @Override
    public int getItemCount() {
        return personnelDtoList.size();
    }
}
