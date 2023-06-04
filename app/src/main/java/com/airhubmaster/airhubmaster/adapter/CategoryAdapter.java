package com.airhubmaster.airhubmaster.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airhubmaster.airhubmaster.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<String> categories;
    private Context context;
    private int selectedIndex = -1;
    private OnCategoryClickListener onCategoryClickListener;

    public CategoryAdapter(Context context, List<String> categories, OnCategoryClickListener onCategoryClickListener) {
        this.context = context;
        this.categories = categories;
        this.onCategoryClickListener = onCategoryClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String category = categories.get(position);
        holder.categoryName.setText(category);

        if (selectedIndex == position) {
            holder.categoryCard.setCardBackgroundColor(context.getResources().getColor(R.color.yellow));
        } else {
            holder.categoryCard.setCardBackgroundColor(context.getResources().getColor(R.color.level_inactive_stroke_color));
        }

        holder.itemView.setOnClickListener(v -> {
            if (selectedIndex == position) {
                selectedIndex = -1;
            } else {
                selectedIndex = position;
            }
            onCategoryClickListener.onCategoryClick(selectedIndex == -1 ? null : category);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        CardView categoryCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.category_name);
            categoryCard = itemView.findViewById(R.id.category_card);
        }
    }

    public interface OnCategoryClickListener {
        void onCategoryClick(String category);
    }
}