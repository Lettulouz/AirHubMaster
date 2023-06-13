package com.airhubmaster.airhubmaster.menuFragment;

import static com.airhubmaster.airhubmaster.interceptor.ApiInterceptor.tokenExpiredInterceptor;
import static com.airhubmaster.airhubmaster.utils.Constans.MESSAGE_AUTHENTICATION;
import static com.airhubmaster.airhubmaster.utils.Constans.MESSAGE_ERROR_STANDARD;
import static com.airhubmaster.airhubmaster.utils.Constans.URL_SERVER;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.airhubmaster.airhubmaster.R;
import com.airhubmaster.airhubmaster.dto.api.ChangeUserPasswordRequestDto;
import com.airhubmaster.airhubmaster.dto.api.ChangeUserPasswordResponseDto;
import com.airhubmaster.airhubmaster.dto.api.FieldMessageErrorDto;
import com.airhubmaster.airhubmaster.dto.api.StandardMessageErrorDto;
import com.airhubmaster.airhubmaster.localDataBase.UserLocalStore;
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

public class ChangeUserPasswordFragment extends Fragment {

    Button buttonSendChange;
    TextInputEditText inputOldPassword;
    TextInputEditText inputPassword;
    TextInputEditText inputRepeatPassword;
    TextInputLayout inputOldPasswordLayout;
    TextInputLayout inputPasswordLayout;
    TextInputLayout inputRepeatPasswordLayout;
    UserLocalStore userLocalStore;
    FieldMessageErrorDto fieldMessageErrorDto;
    StandardMessageErrorDto standardMessageErrorDto;
    ChangeUserPasswordResponseDto changeUserPasswordResponseDto;
    ChangeUserPasswordRequestDto changeUserPasswordRequestDto;
    private final Gson gson = new Gson();

    public ChangeUserPasswordFragment() {
    }

    public static ChangeUserPasswordFragment newInstance(String param1, String param2) {
        ChangeUserPasswordFragment fragment = new ChangeUserPasswordFragment();
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
        buttonSendChange = getActivity().findViewById(R.id.buttonChangeUserPassword);
        inputOldPassword = getActivity().findViewById(R.id.inputChangeUserPasswordOld);
        inputPassword = getActivity().findViewById(R.id.inputChangeUserPassword);
        inputRepeatPassword = getActivity().findViewById(R.id.inputChangeUserPasswordRepeat);
        inputOldPasswordLayout = getActivity().findViewById(R.id.textChangeUserPasswordOldLayout);
        inputPasswordLayout = getActivity().findViewById(R.id.textChangeUserPasswordLayout);
        inputRepeatPasswordLayout = getActivity().findViewById(R.id.textChangeUserPasswordRepeatLayout);

        buttonSendChange.setOnClickListener(v -> changePassword());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_user_password, container, false);
    }

    private void changePassword() {
        String passwordOld = inputOldPassword.getText().toString();
        String password = inputPassword.getText().toString();
        String passwordRepeat = inputRepeatPassword.getText().toString();

        changeUserPasswordRequestDto = new ChangeUserPasswordRequestDto(passwordOld, password, passwordRepeat);
        userLocalStore = UserLocalStore.getInstance(getActivity());

        String url = URL_SERVER + "api/v1/account/update/password";
        RequestBody body = RequestBody.create(gson.toJson(changeUserPasswordRequestDto), Constans.JSON);
        Request request = new Request.Builder()
                .url(url)
                .patch(body)
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
                    changeUserPasswordResponseDto = gson.fromJson(response.body().string(), ChangeUserPasswordResponseDto.class);
                    getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),
                            changeUserPasswordResponseDto.getMessage(), Toast.LENGTH_LONG).show());
                    InputMethodManager inputManager = (InputMethodManager) getActivity()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    View currentFocusedView = getActivity().getCurrentFocus();
                    if (currentFocusedView != null) {
                        inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    replaceFragment(new ProfileFragment());
                } else if (response.code() == 400) {
                    String responseBody = response.body().string();
                    fieldMessageErrorDto = gson.fromJson(responseBody, FieldMessageErrorDto.class);
                    if (fieldMessageErrorDto.getErrors() == null) {
                        standardMessageErrorDto = gson.fromJson(responseBody, StandardMessageErrorDto.class);
                        getActivity().runOnUiThread(() -> {
                            getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),
                                    MESSAGE_AUTHENTICATION, Toast.LENGTH_SHORT).show());
                        });
                    } else {
                        for (Map.Entry<String, String> entry : fieldMessageErrorDto.getErrors().entrySet()) {
                            String key = entry.getKey();
                            String value = entry.getValue();
                            if (key.equals("oldPassword")) {
                                getActivity().runOnUiThread(() -> {
                                    inputOldPasswordLayout.setErrorEnabled(true);
                                    inputOldPasswordLayout.setError(value);
                                });
                            }
                            if (key.equals("newPassword")) {
                                getActivity().runOnUiThread(() -> {
                                    inputPasswordLayout.setErrorEnabled(true);
                                    inputPasswordLayout.setError(value);
                                });
                            }
                            if (key.equals("confirmedNewPassword")) {
                                getActivity().runOnUiThread(() -> {
                                    inputRepeatPasswordLayout.setErrorEnabled(true);
                                    inputRepeatPasswordLayout.setError(value);
                                });
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

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutMenu, fragment);
        fragmentTransaction.commit();
    }
}
