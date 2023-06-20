package com.airhubmaster.airhubmaster.viewHolder;

        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import com.airhubmaster.airhubmaster.R;

public class BuyPlaneViewHolder extends RecyclerView.ViewHolder {

    public ImageView planeIcon;
    public TextView planeName;
    public TextView planeCategory;
    public ImageButton expandButton;
    public View upgradeLayout;
    public Button buy_button;

    public BuyPlaneViewHolder(@NonNull View itemView) {
        super(itemView);
        planeIcon = itemView.findViewById(R.id.plane_icon);
        planeName = itemView.findViewById(R.id.plane_name);
        planeCategory = itemView.findViewById(R.id.plane_category);
        expandButton = itemView.findViewById(R.id.expand_button);
        upgradeLayout = itemView.findViewById(R.id.upgrade_layout);
        buy_button = itemView.findViewById(R.id.buy_button);
    }
}