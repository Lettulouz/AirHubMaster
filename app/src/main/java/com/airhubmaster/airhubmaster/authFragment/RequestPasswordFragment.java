package com.airhubmaster.airhubmaster.authFragment;

import static com.airhubmaster.airhubmaster.utils.Constans.MESSAGE_ERROR_STANDARD;
import static com.airhubmaster.airhubmaster.utils.Constans.URL_SERVER;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.airhubmaster.airhubmaster.R;
import com.airhubmaster.airhubmaster.dto.api.FieldMessageErrorDto;
import com.airhubmaster.airhubmaster.dto.api.RenewPasswordRequestDto;
import com.airhubmaster.airhubmaster.dto.api.RenewPasswordResponseDto;
import com.airhubmaster.airhubmaster.dto.api.StandardMessageErrorDto;
import com.airhubmaster.airhubmaster.utils.Constans;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestPasswordFragment extends Fragment {

    Button buttonRenew;
    TextInputEditText inputRenew;
    TextInputLayout inputRenewLayout;
    StandardMessageErrorDto standardMessageErrorDto;
    FieldMessageErrorDto fieldMessageErrorDto;
    RenewPasswordRequestDto renewPasswordRequestDto;
    RenewPasswordResponseDto renewPasswordResponseDto;
    private final Gson gson = new Gson();

    public RequestPasswordFragment() {
    }

    public static RequestPasswordFragment newInstance(String param1, String param2) {
        RequestPasswordFragment fragment = new RequestPasswordFragment();
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
        buttonRenew = getActivity().findViewById(R.id.buttonRenew);
        inputRenew = getActivity().findViewById(R.id.inputRenew);
        inputRenewLayout = getActivity().findViewById(R.id.textRenewLayout);
        buttonRenew.setOnClickListener(v -> renewPassword());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_request_password, container, false);
    }

    private void renewPassword() {
        String loginOrEmail = inputRenew.getText().toString();
        renewPasswordRequestDto = new RenewPasswordRequestDto(loginOrEmail);

        OkHttpClient client = new OkHttpClient();
        String url = URL_SERVER + "api/v1/renew-password/request";
        RequestBody body = RequestBody.create(gson.toJson(renewPasswordRequestDto), Constans.JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Connection", "close")
                .header("Accept-language", "pl")
                .header("User-Agent", "mobile")
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
                    renewPasswordResponseDto = gson.fromJson(response.body().string(), RenewPasswordResponseDto.class);
                    getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),
                            renewPasswordResponseDto.getMessage(), Toast.LENGTH_LONG).show());
                    replaceFragment(new ChangePasswordFragment());
                } else if (response.code() == 401) {
                    standardMessageErrorDto = gson.fromJson(response.body().string(), StandardMessageErrorDto.class);
                    getActivity().runOnUiThread(() -> {
                        inputRenewLayout.setErrorEnabled(true);
                        inputRenewLayout.setError(standardMessageErrorDto.getMessage());
                    });
                } else if (response.code() == 400) {
                    fieldMessageErrorDto = gson.fromJson(response.body().string(), FieldMessageErrorDto.class);
                    for (Map.Entry<String, String> entry : fieldMessageErrorDto.getErrors().entrySet()) {
                        String key = entry.getKey();
                        String value = entry.getValue();
                        if (key.equals("loginOrEmail")) {
                            getActivity().runOnUiThread(() -> {
                                inputRenewLayout.setErrorEnabled(true);
                                inputRenewLayout.setError(value);
                            });
                        }
                    }
                } else {
                    getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),
                            MESSAGE_ERROR_STANDARD, Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutRenew, fragment);
        fragmentTransaction.commit();
    }
}
