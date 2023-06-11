package com.airhubmaster.airhubmaster.menuFragment;

import static com.airhubmaster.airhubmaster.interceptor.ApiInterceptor.tokenExpiredInterceptor;
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
import com.airhubmaster.airhubmaster.dto.api.ChangeUserLoginRequestDto;
import com.airhubmaster.airhubmaster.dto.api.ChangeUserLoginResponseDto;
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

public class ChangeUserLoginFragment extends Fragment {

    Button buttonSendChange;
    TextInputEditText inputLogin;
    TextInputLayout inputLoginLayout;
    UserLocalStore userLocalStore;
    StandardMessageErrorDto standardMessageErrorDto;
    FieldMessageErrorDto fieldMessageErrorDto;
    ChangeUserLoginRequestDto changeUserLoginRequestDto;
    ChangeUserLoginResponseDto changeUserLoginResponseDto;
    private final Gson gson = new Gson();

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
        buttonSendChange = getActivity().findViewById(R.id.buttonChangeUserLogin);
        inputLogin = getActivity().findViewById(R.id.inputChangeUserLogin);
        inputLoginLayout = getActivity().findViewById(R.id.textChangeUserLoginLayout);

        buttonSendChange.setOnClickListener(v -> changeLogin());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_user_login, container, false);
    }

    private void changeLogin() {
        String login = inputLogin.getText().toString();
        changeUserLoginRequestDto = new ChangeUserLoginRequestDto(login);
        userLocalStore = UserLocalStore.getInstance(getActivity());

        String url = URL_SERVER + "api/v1/account/update/login ";
        RequestBody body = RequestBody.create(gson.toJson(changeUserLoginRequestDto), Constans.JSON);
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
                    changeUserLoginResponseDto = gson.fromJson(response.body().string(), ChangeUserLoginResponseDto.class);
                    userLocalStore.setJwtUserToken(changeUserLoginResponseDto.getUpdatedJwt());
                    getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),
                            changeUserLoginResponseDto.getMessage(), Toast.LENGTH_LONG).show());
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
                            inputLoginLayout.setErrorEnabled(true);
                            inputLoginLayout.setError(standardMessageErrorDto.getMessage());
                        });
                    } else {
                        for (Map.Entry<String, String> entry : fieldMessageErrorDto.getErrors().entrySet()) {
                            String key = entry.getKey();
                            String value = entry.getValue();
                            if (key.equals("newLogin")) {
                                getActivity().runOnUiThread(() -> {
                                    inputLoginLayout.setErrorEnabled(true);
                                    inputLoginLayout.setError(value);
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
