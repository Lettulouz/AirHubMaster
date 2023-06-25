package com.airhubmaster.airhubmaster.gameFragment;

import android.app.Dialog;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.airhubmaster.airhubmaster.R;
import com.airhubmaster.airhubmaster.adapter.SetPersonnelAdapter;
import com.google.android.material.textfield.TextInputLayout;

public class MainFragment extends Fragment {

    boolean flag;
    Button buttonAccept;
    Button buttonCancel;
    TextView textViewLabel;
    AutoCompleteTextView spinnerPlane;
    AutoCompleteTextView spinnerStewardess;
    AutoCompleteTextView spinnerGroundPersonnel;
    TextInputLayout textInputLayoutPlane;
    TextInputLayout textInputLayoutStewardess;
    TextInputLayout textInputLayoutGroundPersonnel;
    Dialog dialogPersonnel;
    ImageView imageTerminal;
    ImageView imageRunway;
    ImageView imagePlane;

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
        imagePlane = getActivity().findViewById(R.id.imagePlane);
        setDialogPersonel();

        imageTerminal.setOnClickListener(v -> {
            imageTerminal.setScaleX(0.95f);
            imageTerminal.setScaleY(0.95f);
            new Handler().postDelayed(() -> {
                imageTerminal.setScaleX(1);
                imageTerminal.setScaleY(1);
                dialogPersonnel.show();
            }, 150);
        });

        imageRunway.setOnClickListener(v -> {
            imageRunway.setScaleX(0.95f);
            imageRunway.setScaleY(0.95f);
            new Handler().postDelayed(() -> {
                imageRunway.setScaleX(1);
                imageRunway.setScaleY(1);
                replaceFragment(new DepartureFragment());
            }, 150);
        });

        imagePlane.setOnClickListener(v -> {
            imagePlane.setScaleX(0.95f);
            imagePlane.setScaleY(0.95f);
            new Handler().postDelayed(() -> {
                imagePlane.setScaleX(1);
                imagePlane.setScaleY(1);
                replaceFragment(new ArrivalFragment());
            }, 150);
        });

        textInputLayoutPlane = dialogPersonnel.findViewById(R.id.textInputLayoutSetPlane);
        textInputLayoutStewardess = dialogPersonnel.findViewById(R.id.textInputLayoutSetStewardess);
        textInputLayoutGroundPersonnel = dialogPersonnel.findViewById(R.id.textInputLayoutSetGroundPersonnel);

        spinnerPlane = dialogPersonnel.findViewById(R.id.spinnerSetPlane);
        spinnerStewardess = dialogPersonnel.findViewById(R.id.spinnerSetStewardess);
        spinnerGroundPersonnel = dialogPersonnel.findViewById(R.id.spinnerSetGroundPersonnel);

        textViewLabel = dialogPersonnel.findViewById(R.id.textViewSetPersonnelMessage);
        buttonAccept = dialogPersonnel.findViewById(R.id.buttonPersonnelSetAccept);
        buttonCancel = dialogPersonnel.findViewById(R.id.buttonPersonnelSetCancel);

//        spinnerPlane.setOnDismissListener(() -> {
//            if (adapter.getSelectedItems().size() != 2) {
//                spinnerPlane.setHint(null);
//                textInputLayoutPlane.setError("Wybierz odpowiednia ilosc!");
//                textInputLayoutPlane.setErrorEnabled(true);
//                adapter.resetList();
//            }else {
//                textInputLayoutPlane.setErrorEnabled(false);
//                spinnerPlane.setHint(adapter.getSelectedItems().toString().replaceAll("[\\[\\]]", ""));
//                for (String item : adapter.getSelectedItems()) {
//
//                    System.out.println("Selected Item: " + item);
//                }
//                adapter.resetList();
//            }
//        });

        String[] array1 = {"Jan Kowalski", "Gerge Test", "Ela Test", "Paweł Nowok"};

        SetPersonnelAdapter adapterStewardess = new SetPersonnelAdapter(getActivity(), 1, array1);
        spinnerStewardess.setAdapter(adapterStewardess);

        spinnerStewardess.setOnDismissListener(() -> {
            if (adapterStewardess.getSelectedItems().size() != 2) {
                spinnerStewardess.setHint(null);
                textInputLayoutStewardess.setError("Wybierz odpowiednia ilosc!");
                textInputLayoutStewardess.setErrorEnabled(true);
                adapterStewardess.resetList();
            } else {
                textInputLayoutStewardess.setErrorEnabled(false);
                spinnerStewardess.setHint(adapterStewardess.getSelectedItems().toString().replaceAll("[\\[\\]]", ""));
                adapterStewardess.resetList();
            }
        });

        String[] array2 = {"Pawel Kowalski", "Szymon Test", "Janusz Nowy", "Tester Nowok"};

        SetPersonnelAdapter adapterGroundPersonnel = new SetPersonnelAdapter(getActivity(), 1, array2);
        spinnerGroundPersonnel.setAdapter(adapterGroundPersonnel);

        spinnerGroundPersonnel.setOnDismissListener(() -> {
            if (adapterGroundPersonnel.getSelectedItems().size() != 3) {
                spinnerGroundPersonnel.setHint(null);
                textInputLayoutGroundPersonnel.setError("Wybierz odpowiednia ilosc!");
                textInputLayoutGroundPersonnel.setErrorEnabled(true);
                adapterGroundPersonnel.resetList();
            } else {
                textInputLayoutGroundPersonnel.setErrorEnabled(false);
                spinnerGroundPersonnel.setHint(adapterGroundPersonnel.getSelectedItems().toString().replaceAll("[\\[\\]]", ""));
                adapterGroundPersonnel.resetList();
            }
        });

        String[] array = {"Boeing", "Airbus", "Ciesna 256", "ATR-8"};

        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_spinner_item,
                array);
        spinnerPlane.setAdapter(spinnerArrayAdapter);

        spinnerPlane.setOnItemClickListener((parent, view1, position, id) -> {
            spinnerGroundPersonnel.setHint("");
            spinnerStewardess.setHint("");
            adapterGroundPersonnel.resetList();
            adapterStewardess.resetList();
            String plane = (String) parent.getItemAtPosition(position);
            textInputLayoutPlane.setErrorEnabled(false);
            flag = true;
            textViewLabel.setText("Czy na pewno chcesz przypisac wybrany personel do samolotu " + plane + "?");
        });

        spinnerPlane.setOnDismissListener(() -> {
            if (!flag) {
                textInputLayoutPlane.setError("Wybierz samolot!");
                textInputLayoutPlane.setErrorEnabled(true);
            }
        });
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
