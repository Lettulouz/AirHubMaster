package com.airhubmaster.airhubmaster.gameFragment;

import android.app.Dialog;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.airhubmaster.airhubmaster.R;

public class MainFragment extends Fragment {

    Dialog dialogPersonnel;
    ImageView imageTerminal;
    ImageView imageRunway;

    public MainFragment() {
    }

    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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
        imageTerminal = getActivity().findViewById(R.id.imageTerminal);
        imageRunway = getActivity().findViewById(R.id.imageRunway);
        setDialogPersonel();

        imageTerminal.setOnClickListener(v -> dialogPersonnel.show());
        imageRunway.setOnClickListener(v -> replaceFragment(new DepartureFragment()));
    }

    @Override
    public void onPause() {
        super.onPause();
        dialogPersonnel.dismiss();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    /**
     * The method responsible for create set crew plane pop up dialog
     */
    public void setDialogPersonel() {
        dialogPersonnel = new Dialog(getActivity());
        dialogPersonnel.setContentView(R.layout.dialog_personnel_set);
        dialogPersonnel.getWindow().setBackgroundDrawable(getActivity().getDrawable(R.drawable.background_dialog));
        dialogPersonnel.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        InsetDrawable inset = new InsetDrawable(getActivity().getDrawable(R.drawable.background_dialog), 32, 0, 32, 0);
        dialogPersonnel.getWindow().setBackgroundDrawable(inset);
        dialogPersonnel.setCancelable(true);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutMenu, fragment);
        fragmentTransaction.commit();
    }
}
