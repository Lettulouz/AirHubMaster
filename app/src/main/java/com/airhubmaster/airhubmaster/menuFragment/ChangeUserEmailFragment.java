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
import com.airhubmaster.airhubmaster.dto.api.ChangeUserEmailRequestDto;
import com.airhubmaster.airhubmaster.dto.api.ChangeUserEmailResponseDto;
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

public class ChangeUserEmailFragment extends Fragment {

    Button buttonSendChange;
    TextInputEditText inputEmail;
    TextInputLayout inputEmailLayout;
    UserLocalStore userLocalStore;
    StandardMessageErrorDto standardMessageErrorDto;
    FieldMessageErrorDto fieldMessageErrorDto;
    ChangeUserEmailRequestDto changeUserEmailRequestDto;
    ChangeUserEmailResponseDto changeUserEmailResponseDto;
    private final Gson gson = new Gson();

    public ChangeUserEmailFragment() {
    }

    public static ChangeUserEmailFragment newInstance(String param1, String param2) {
        ChangeUserEmailFragment fragment = new ChangeUserEmailFragment();
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
        buttonSendChange = getActivity().findViewById(R.id.buttonChangeUserEmail);
        inputEmail = getActivity().findViewById(R.id.inputChangeUserEmail);
        inputEmailLayout = getActivity().findViewById(R.id.textChangeUserEmailLayout);

        buttonSendChange.setOnClickListener(v -> changeEmail());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_user_email, container, false);
    }

    private void changeEmail() {
        String email = inputEmail.getText().toString();
        changeUserEmailRequestDto = new ChangeUserEmailRequestDto(email);
        userLocalStore = UserLocalStore.getInstance(getActivity());

        String url = URL_SERVER + "api/v1/account/update/email";
        RequestBody body = RequestBody.create(gson.toJson(changeUserEmailRequestDto), Constans.JSON);
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
                    changeUserEmailResponseDto = gson.fromJson(response.body().string(), ChangeUserEmailResponseDto.class);
                    getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),
                            changeUserEmailResponseDto.getMessage(), Toast.LENGTH_LONG).show());
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
                            inputEmailLayout.setErrorEnabled(true);
                            inputEmailLayout.setError(standardMessageErrorDto.getMessage());
                        });
                    } else {
                        for (Map.Entry<String, String> entry : fieldMessageErrorDto.getErrors().entrySet()) {
                            String key = entry.getKey();
                            String value = entry.getValue();
                            if (key.equals("newEmail")) {
                                getActivity().runOnUiThread(() -> {
                                    inputEmailLayout.setErrorEnabled(true);
                                    inputEmailLayout.setError(value);
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
