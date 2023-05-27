package com.airhubmaster.airhubmaster.gameFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.airhubmaster.airhubmaster.R;
import com.airhubmaster.airhubmaster.localDataBase.UserLocalStore;

public class PersonnelFragment extends Fragment {

    Button button;
    private UserLocalStore userLocalStore = UserLocalStore.getInstance(getContext());

    public PersonnelFragment() { }

    public static PersonnelFragment newInstance(String param1, String param2) {
        PersonnelFragment fragment = new PersonnelFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button = getActivity().findViewById(R.id.buttonLogout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLocalStore.clearUserData();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personnel, container, false);
    }
}
