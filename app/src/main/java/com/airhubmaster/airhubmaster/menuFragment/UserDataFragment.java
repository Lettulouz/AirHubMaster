package com.airhubmaster.airhubmaster.menuFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airhubmaster.airhubmaster.R;

public class UserDataFragment extends Fragment {

    CardView usernameCardView;
    CardView loginCardView;
    CardView emailCardView;
    CardView passwordCardView;

    public UserDataFragment() {
    }

    public static UserDataFragment newInstance(String param1, String param2) {
        UserDataFragment fragment = new UserDataFragment();
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
        usernameCardView = getView().findViewById(R.id.usernameChangeCard);
        loginCardView = getView().findViewById(R.id.loginChangeCard);
        emailCardView = getView().findViewById(R.id.emailChangeCard);
        passwordCardView = getView().findViewById(R.id.passwordChangeCard);

        loginCardView.setOnClickListener(v -> replaceFragment(new ChangeUserLoginFragment()));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_data, container, false);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutMenu, fragment);
        fragmentTransaction.commit();
    }
}
