package com.airhubmaster.airhubmaster.gameFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airhubmaster.airhubmaster.R;

public class PersonnelFragment extends Fragment {

    public PersonnelFragment() { }

    public static PersonnelFragment newInstance(String param1, String param2) {
        PersonnelFragment fragment = new PersonnelFragment();
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
        return inflater.inflate(R.layout.fragment_personnel, container, false);
    }
}
