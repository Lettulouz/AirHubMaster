package com.airhubmaster.airhubmaster.menuFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airhubmaster.airhubmaster.R;

public class ChangeUserLoginFragment extends Fragment {

    public ChangeUserLoginFragment() {
    }

    public static ChangeUserLoginFragment newInstance(String param1, String param2) {
        ChangeUserLoginFragment fragment = new ChangeUserLoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_user_login, container, false);
    }
}
