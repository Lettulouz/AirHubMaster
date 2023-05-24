package com.airhubmaster.airhubmaster.gameFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airhubmaster.airhubmaster.R;

import java.util.ArrayList;
import java.util.List;

import com.airhubmaster.airhubmaster.dto.game.Plane;

public class PlaneFragment extends Fragment {

    private RecyclerView recyclerView;
    private PlaneAdapter adapter;
    private List<Plane> planeList;

    public PlaneFragment() { }

    public static PlaneFragment newInstance() {
        PlaneFragment fragment = new PlaneFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plane, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        planeList = new ArrayList<>();
        // dodaj samoloty do listy
        planeList.add(new Plane("Samolot A", 0));
        planeList.add(new Plane("Samolot B", 0));
        planeList.add(new Plane("Samolot C", 0));
        planeList.add(new Plane("Samolot B", 0));
        planeList.add(new Plane("Samolot C", 0));
        planeList.add(new Plane("Samolot B", 0));
        planeList.add(new Plane("Samolot C", 0));
        planeList.add(new Plane("Samolot B", 0));
        planeList.add(new Plane("Samolot C", 0));
        planeList.add(new Plane("Samolot B", 0));
        planeList.add(new Plane("Samolot C", 0));
        adapter = new PlaneAdapter(planeList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    class PlaneAdapter extends RecyclerView.Adapter<PlaneAdapter.PlaneViewHolder> {

        private List<Plane> planes;

        public PlaneAdapter(List<Plane> planes) {
            this.planes = planes;
        }

        @Override
        public PlaneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plane, parent, false);
            return new PlaneViewHolder(view);
        }

        @Override
        public void onBindViewHolder(PlaneViewHolder holder, int position) {
            Plane plane = planes.get(position);
            holder.planeName.setText(plane.getName());
            holder.upgradeLevel.setText(getString(R.string.upgrade_level, plane.getUpgradeLevel()));
            holder.expandButton.setOnClickListener(v -> {
                if (holder.upgradeLayout.getVisibility() == View.GONE) {
                    holder.upgradeLayout.setVisibility(View.VISIBLE);
                    holder.expandButton.setBackgroundResource(R.drawable.collapse_icon);
                } else {
                    holder.upgradeLayout.setVisibility(View.GONE);
                    holder.expandButton.setBackgroundResource(R.drawable.expand_icon);
                }
            });

            holder.upgradeButton.setOnClickListener(v -> {
                plane.upgrade();
                holder.upgradeLevel.setText(getString(R.string.upgrade_level, plane.getUpgradeLevel()));
                updateLevelImages(holder, plane.getUpgradeLevel());
            });

            updateLevelImages(holder, plane.getUpgradeLevel());
        }

        private void updateLevelImages(PlaneViewHolder holder, int level) {
            ImageView[] levelImages = {holder.level1, holder.level2, holder.level3, holder.level4, holder.level5};
            for (int i = 0; i < levelImages.length; i++) {
                levelImages[i].setBackgroundResource(i < level ? R.drawable.level_active : R.drawable.level_inactive);
            }
        }

        @Override
        public int getItemCount() {
            return planes.size();
        }

        class PlaneViewHolder extends RecyclerView.ViewHolder {

            TextView planeName, upgradeLevel;
            ImageButton expandButton;
            Button upgradeButton;
            ConstraintLayout upgradeLayout;
            ImageView level1, level2, level3, level4, level5;

            public PlaneViewHolder(View itemView) {
                super(itemView);
                planeName = itemView.findViewById(R.id.plane_name);
                upgradeLevel = itemView.findViewById(R.id.upgrade_level);
                expandButton = itemView.findViewById(R.id.expand_button);
                upgradeButton = itemView.findViewById(R.id.upgrade_button);
                upgradeLayout = itemView.findViewById(R.id.upgrade_layout);
                level1 = itemView.findViewById(R.id.level_1);
                level2 = itemView.findViewById(R.id.level_2);
                level3 = itemView.findViewById(R.id.level_3);
                level4 = itemView.findViewById(R.id.level_4);
                level5 = itemView.findViewById(R.id.level_5);
            }
        }
    }
}
