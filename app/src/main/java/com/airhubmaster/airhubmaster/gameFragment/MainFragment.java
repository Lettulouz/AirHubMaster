package com.airhubmaster.airhubmaster.gameFragment;

import static com.airhubmaster.airhubmaster.interceptor.ApiInterceptor.tokenExpiredInterceptor;
import static com.airhubmaster.airhubmaster.utils.Constans.MESSAGE_ERROR_STANDARD;
import static com.airhubmaster.airhubmaster.utils.Constans.URL_SERVER;

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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airhubmaster.airhubmaster.R;
import com.airhubmaster.airhubmaster.adapter.SetPersonnelAdapter;
import com.airhubmaster.airhubmaster.adapter.SetPlaneDialogAdapter;
import com.airhubmaster.airhubmaster.dto.api.StandardMessageErrorDto;
import com.airhubmaster.airhubmaster.dto.game.SetPersonnelPlaneDto;
import com.airhubmaster.airhubmaster.dto.game.SetPersonnelRequestDto;
import com.airhubmaster.airhubmaster.dto.game.SetPersonnelResponseDto;
import com.airhubmaster.airhubmaster.dto.game.SetPersonnelWorkerDto;
import com.airhubmaster.airhubmaster.localDataBase.UserLocalStore;
import com.airhubmaster.airhubmaster.utils.Constans;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainFragment extends Fragment {

    /////////////////////////
    int planeId;
    int workerIdPilot;
    int workerIdStewardess;
    int workerIdGroundPersonnel;
    List<Integer> workersIdList = new ArrayList<>();
    /////////////////////////
    Button buttonAccept;
    Button buttonCancel;
    TextView textViewLabel;
    SetPlaneDialogAdapter adapterPlane;
    AutoCompleteTextView spinnerPlane;
    SetPersonnelAdapter adapterPilot;
    AutoCompleteTextView spinnerPilot;
    SetPersonnelAdapter adapterStewardess;
    AutoCompleteTextView spinnerStewardess;
    SetPersonnelAdapter adapterGroundPersonnel;
    AutoCompleteTextView spinnerGroundPersonnel;
    TextInputLayout textInputLayoutPlane;
    TextInputLayout textInputLayoutPilot;
    TextInputLayout textInputLayoutStewardess;
    TextInputLayout textInputLayoutGroundPersonnel;
    Dialog dialogPersonnel;
    ImageView imageTerminal;
    ImageView imageRunway;
    ImageView imagePlane;
    UserLocalStore userLocalStore;
    StandardMessageErrorDto standardMessageErrorDto;
    SetPersonnelResponseDto setPersonnelResponseDto;
    private final Gson gson = new Gson();

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
                getPersonnel();
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
        textInputLayoutPilot = dialogPersonnel.findViewById(R.id.textInputLayoutSetPilot);
        textInputLayoutStewardess = dialogPersonnel.findViewById(R.id.textInputLayoutSetStewardess);
        textInputLayoutGroundPersonnel = dialogPersonnel.findViewById(R.id.textInputLayoutSetGroundPersonnel);

        spinnerPlane = dialogPersonnel.findViewById(R.id.spinnerSetPlane);
        spinnerPilot = dialogPersonnel.findViewById(R.id.spinnerSetPilot);
        spinnerStewardess = dialogPersonnel.findViewById(R.id.spinnerSetStewardess);
        spinnerGroundPersonnel = dialogPersonnel.findViewById(R.id.spinnerSetGroundPersonnel);

        textViewLabel = dialogPersonnel.findViewById(R.id.textViewSetPersonnelMessage);
        buttonAccept = dialogPersonnel.findViewById(R.id.buttonPersonnelSetAccept);
        buttonCancel = dialogPersonnel.findViewById(R.id.buttonPersonnelSetCancel);
        buttonCancel.setOnClickListener(v -> dialogPersonnel.dismiss());

        buttonAccept.setOnClickListener(v -> {
            workersIdList.add(workerIdPilot);
            workersIdList.add(workerIdStewardess);
            workersIdList.add(workerIdGroundPersonnel);
            setPersonnelToPlane(planeId, workersIdList);
            workersIdList.clear();
        });

        spinnerPilot.setOnDismissListener(() -> {
            if (adapterPilot.getSelectedItems().size() != 1) {
                spinnerPilot.setHint(null);
                textInputLayoutPilot.setError("Wybierz odpowiednią ilość personelu!");
                textInputLayoutPilot.setErrorEnabled(true);
                adapterPilot.resetList();
            } else {
                workerIdPilot = adapterPilot.getSelectedId();
                textInputLayoutPilot.setErrorEnabled(false);
                spinnerPilot.setHint(adapterPilot.getSelectedItems().toString().replaceAll("[\\[\\]]", ""));
                adapterPilot.resetList();
            }
        });


        spinnerStewardess.setOnDismissListener(() -> {
            if (adapterStewardess.getSelectedItems().size() != 1) {
                spinnerStewardess.setHint(null);
                textInputLayoutStewardess.setError("Wybierz odpowiednią ilość personelu!");
                textInputLayoutStewardess.setErrorEnabled(true);
                adapterStewardess.resetList();
            } else {
                workerIdStewardess = adapterStewardess.getSelectedId();
                textInputLayoutStewardess.setErrorEnabled(false);
                spinnerStewardess.setHint(adapterStewardess.getSelectedItems().toString().replaceAll("[\\[\\]]", ""));
                adapterStewardess.resetList();
            }
        });


        spinnerGroundPersonnel.setOnDismissListener(() -> {
            if (adapterGroundPersonnel.getSelectedItems().size() != 1) {
                spinnerGroundPersonnel.setHint(null);
                textInputLayoutGroundPersonnel.setError("Wybierz odpowiednią ilość personelu!");
                textInputLayoutGroundPersonnel.setErrorEnabled(true);
                adapterGroundPersonnel.resetList();
            } else {
                workerIdGroundPersonnel = adapterGroundPersonnel.getSelectedId();
                textInputLayoutGroundPersonnel.setErrorEnabled(false);
                spinnerGroundPersonnel.setHint(adapterGroundPersonnel.getSelectedItems().toString().replaceAll("[\\[\\]]", ""));
                adapterGroundPersonnel.resetList();
            }
        });

        spinnerPlane.setOnDismissListener(() -> {
            if (adapterPlane.getSelectedItems().size() != 1) {
                spinnerPlane.setHint(null);
                textInputLayoutPlane.setError("Wybierz samolot!");
                textInputLayoutPlane.setErrorEnabled(true);
                adapterPlane.resetList();
                adapterPilot.resetList();
                adapterStewardess.resetList();
                adapterGroundPersonnel.resetList();
                spinnerGroundPersonnel.setHint("");
                spinnerStewardess.setHint("");
                spinnerPilot.setHint("");
            } else {
                planeId = adapterPlane.getSelectedId();
                textInputLayoutPlane.setErrorEnabled(false);
                spinnerPlane.setHint(adapterPlane.getSelectedItems().toString().replaceAll("[\\[\\]]", ""));
                adapterPlane.resetList();
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

    /**
     * The method responsible for downloading the data of currently available
     * employees assigned to a given user
     */
    private void getPersonnel() {
        userLocalStore = UserLocalStore.getInstance(getActivity());

        String url = URL_SERVER + "api/v1/crew";
        Request request = new Request.Builder()
                .url(url)
                .get()
                .header("Authorization", "Bearer " + userLocalStore.getJwtUserToken())
                .header("Connection", "close")
                .header("Accept-language", "pl")
                .header("User-Agent", "mobile")
                .build();

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(tokenExpiredInterceptor(userLocalStore))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),
                        MESSAGE_ERROR_STANDARD, Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    setPersonnelResponseDto = gson.fromJson(response.body().string(), SetPersonnelResponseDto.class);
                    getActivity().runOnUiThread(() -> {

                        List<SetPersonnelWorkerDto> workerPListTemp;
                        if (setPersonnelResponseDto.getWorkers().getPilot() == null) {
                            workerPListTemp = new ArrayList<>();
                        } else {
                            workerPListTemp = setPersonnelResponseDto.getWorkers().getPilot();
                        }

                        adapterPilot = new SetPersonnelAdapter(getActivity(), 1, workerPListTemp);
                        spinnerPilot.setAdapter(adapterPilot);

                        List<SetPersonnelWorkerDto> workerSListTemp;
                        if (setPersonnelResponseDto.getWorkers().getStewardessa() == null) {
                            workerSListTemp = new ArrayList<>();
                        } else {
                            workerSListTemp = setPersonnelResponseDto.getWorkers().getStewardessa();
                        }
                        adapterStewardess = new SetPersonnelAdapter(getActivity(), 1, workerSListTemp);
                        spinnerStewardess.setAdapter(adapterStewardess);

                        List<SetPersonnelWorkerDto> workerGListTemp;
                        if (setPersonnelResponseDto.getWorkers().getPersonelNaziemny() == null) {
                            workerGListTemp = new ArrayList<>();
                        } else {
                            workerGListTemp = setPersonnelResponseDto.getWorkers().getPersonelNaziemny();
                        }

                        adapterGroundPersonnel = new SetPersonnelAdapter(getActivity(), 1, workerGListTemp);
                        spinnerGroundPersonnel.setAdapter(adapterGroundPersonnel);

                        List<SetPersonnelPlaneDto> workerAirListTemp;
                        if (setPersonnelResponseDto.getPlanes() == null) {
                            workerAirListTemp = new ArrayList<>();
                        } else {
                            workerAirListTemp = setPersonnelResponseDto.getPlanes();
                        }

                        adapterPlane = new SetPlaneDialogAdapter(getActivity(), 1, workerAirListTemp);
                        spinnerPlane.setAdapter(adapterPlane);
                    });
                } else {
                    getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),
                            MESSAGE_ERROR_STANDARD, Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    //==============================================================================================

    /**
     * The method responsible for assigning the crew to the plane
     */
    private void setPersonnelToPlane(int planeId, List<Integer> workersId) {
        userLocalStore = UserLocalStore.getInstance(getActivity());
        SetPersonnelRequestDto setPersonnelRequestDto = new SetPersonnelRequestDto(planeId, workersId);

        String url = URL_SERVER + "api/v1/crew";
        RequestBody body = RequestBody.create(gson.toJson(setPersonnelRequestDto), Constans.JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Authorization", "Bearer " + userLocalStore.getJwtUserToken())
                .header("Connection", "close")
                .header("Accept-language", "pl")
                .header("User-Agent", "mobile")
                .build();

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(tokenExpiredInterceptor(userLocalStore))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),
                        MESSAGE_ERROR_STANDARD, Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 201) {
                    standardMessageErrorDto = gson.fromJson(response.body().string(), StandardMessageErrorDto.class);
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getActivity(),
                                standardMessageErrorDto.getMessage(), Toast.LENGTH_SHORT).show();
                        replaceFragment(new DepartureFragment());
                    });
                } else if (response.code() == 404) {
                    standardMessageErrorDto = gson.fromJson(response.body().string(), StandardMessageErrorDto.class);
                    getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),
                            standardMessageErrorDto.getMessage(), Toast.LENGTH_SHORT).show());
                } else {
                    getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),
                            MESSAGE_ERROR_STANDARD, Toast.LENGTH_SHORT).show());
                }
            }
        });
    }
}
