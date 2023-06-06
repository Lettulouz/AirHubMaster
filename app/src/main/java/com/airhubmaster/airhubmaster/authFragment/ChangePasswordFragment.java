package com.airhubmaster.airhubmaster.authFragment;

import static com.airhubmaster.airhubmaster.utils.Constans.MESSAGE_ERROR_STANDARD;
import static com.airhubmaster.airhubmaster.utils.Constans.URL_SERVER;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.airhubmaster.airhubmaster.LoginActivity;
import com.airhubmaster.airhubmaster.R;
import com.airhubmaster.airhubmaster.RegisterActivity;
import com.airhubmaster.airhubmaster.dto.api.ChangePasswordRequestDto;
import com.airhubmaster.airhubmaster.dto.api.ChangePasswordResponseDto;
import com.airhubmaster.airhubmaster.dto.api.FieldMessageErrorDto;
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

public class ChangePasswordFragment extends Fragment {

    Button buttonChange;
    TextInputEditText inputToken;
    TextInputEditText inputPasswordChange;
    TextInputEditText inputPasswordRepeatChange;
    TextInputLayout inputTokenLayout;
    TextInputLayout inputPasswordChangeLayout;
    TextInputLayout inputPasswordRepeatChangeLayout;
    StandardMessageErrorDto standardMessageErrorDto;
    FieldMessageErrorDto fieldMessageErrorDto;
    ChangePasswordRequestDto changePasswordRequestDto;
    ChangePasswordResponseDto changePasswordResponseDto;
    private final Gson gson = new Gson();

    public ChangePasswordFragment() {
    }

    public static ChangePasswordFragment newInstance(String param1, String param2) {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
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
        buttonChange = getActivity().findViewById(R.id.buttonChangePassword);
        inputToken = getActivity().findViewById(R.id.inputToken);
        inputPasswordChange = getActivity().findViewById(R.id.inputPasswordChange);
        inputPasswordRepeatChange = getActivity().findViewById(R.id.inputPasswordChangeRepeat);
        inputTokenLayout = getActivity().findViewById(R.id.textTokenLayout);
        inputPasswordChangeLayout = getActivity().findViewById(R.id.textChangePasswordLayout);
        inputPasswordRepeatChangeLayout = getActivity().findViewById(R.id.textChangePasswordRepeatLayout);
        buttonChange.setOnClickListener(v -> changePassword());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    private void changePassword() {
        String token = inputToken.getText().toString();
        String password = inputPasswordChange.getText().toString();
        String passwordRepeat = inputPasswordRepeatChange.getText().toString();
        changePasswordRequestDto = new ChangePasswordRequestDto(token, password, passwordRepeat);

        OkHttpClient client = new OkHttpClient();
        String url = URL_SERVER + "api/v1/renew-password/change";
        RequestBody body = RequestBody.create(gson.toJson(changePasswordRequestDto), Constans.JSON);
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
                    changePasswordResponseDto = gson.fromJson(response.body().string(), ChangePasswordResponseDto.class);
                    getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),
                            changePasswordResponseDto.getMessage(), Toast.LENGTH_LONG).show());
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                } else if (response.code() == 401) {
                    standardMessageErrorDto = gson.fromJson(response.body().string(), StandardMessageErrorDto.class);
                    getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),
                            standardMessageErrorDto.getMessage(), Toast.LENGTH_SHORT).show());
                } else if (response.code() == 400) {
                    String responseBody = response.body().string();
                    fieldMessageErrorDto = gson.fromJson(responseBody, FieldMessageErrorDto.class);
                    if (fieldMessageErrorDto.getErrors() == null) {
                        standardMessageErrorDto = gson.fromJson(responseBody, StandardMessageErrorDto.class);
                        getActivity().runOnUiThread(() -> {
                            Toast.makeText(getActivity(),
                                    standardMessageErrorDto.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                    } else {
                        for (Map.Entry<String, String> entry : fieldMessageErrorDto.getErrors().entrySet()) {
                            String key = entry.getKey();
                            String value = entry.getValue();
                            switch (key) {
                                case "token": {
                                    getActivity().runOnUiThread(() -> {
                                        inputTokenLayout.setErrorEnabled(true);
                                        inputTokenLayout.setError(value);
                                    });
                                    break;
                                }
                                case "newPassword": {
                                    getActivity().runOnUiThread(() -> {
                                        inputPasswordChangeLayout.setErrorEnabled(true);
                                        inputPasswordChangeLayout.setError(value);
                                    });
                                    break;
                                }
                                case "confirmedNewPassword": {
                                    getActivity().runOnUiThread(() -> {
                                        inputPasswordRepeatChangeLayout.setErrorEnabled(true);
                                        inputPasswordRepeatChangeLayout.setError(value);
                                    });
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),
                            MESSAGE_ERROR_STANDARD, Toast.LENGTH_SHORT).show());
                }
            }
        });
    }
}
