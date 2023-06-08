package com.airhubmaster.airhubmaster.menuFragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.airhubmaster.airhubmaster.R;

public class ProfileFragment extends Fragment {

    TextView textLevelExp;
    ProgressBar progressBar;

    public ProfileFragment() {
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = getActivity().findViewById(R.id.levelProgressBar);
        textLevelExp = getActivity().findViewById(R.id.textViewLevelStatus);

        ObjectAnimator.ofInt(progressBar, "progress", 0, 47)
                .setDuration(1000)
                .start();

        ValueAnimator animator = ValueAnimator.ofInt(0, 4200);
        animator.addUpdateListener(valueAnimator -> {
            animator.setDuration(1000);
            textLevelExp.setText(valueAnimator.getAnimatedValue().toString() + " / 6900 exp");
        });
        animator.start();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
}
