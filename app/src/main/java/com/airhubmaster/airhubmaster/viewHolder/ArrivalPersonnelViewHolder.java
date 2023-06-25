package com.airhubmaster.airhubmaster.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.airhubmaster.airhubmaster.R;

public class ArrivalPersonnelViewHolder extends RecyclerView.ViewHolder{
        public TextView nameTextView;
        public TextView categoryTextView;

        public ArrivalPersonnelViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name_text_view);
            categoryTextView = itemView.findViewById(R.id.category_text_view);
        }
}
