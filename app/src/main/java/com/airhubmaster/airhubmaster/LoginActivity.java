package com.airhubmaster.airhubmaster;

import static com.airhubmaster.airhubmaster.utils.Constans.MESSAGE_AUTHENTICATION;
import static com.airhubmaster.airhubmaster.utils.Constans.MESSAGE_CORRECT_LOGIN_IN;
import static com.airhubmaster.airhubmaster.utils.Constans.MESSAGE_ERROR_PREFERENCES_LOGIN;
import static com.airhubmaster.airhubmaster.utils.Constans.MESSAGE_ERROR_STANDARD;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airhubmaster.airhubmaster.dto.api.FieldMessageErrorDto;
import com.airhubmaster.airhubmaster.dto.api.RefreshRequestDto;
import com.airhubmaster.airhubmaster.dto.api.StandardMessageErrorDto;
import com.airhubmaster.airhubmaster.dto.api.LoginRequestDto;
import com.airhubmaster.airhubmaster.dto.api.LoginResponseDto;
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

/**
 * Activity responsible for basic user login to the application
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Declaring items from the view
     */
    Button buttonLogin;
    TextView registerLink;
    TextInputEditText inputLogin;
    TextInputEditText inputPassword;
    TextInputLayout inputLoginLayout;
    TextInputLayout inputPasswordLayout;
    LoginResponseDto loginResponseDto;
    StandardMessageErrorDto standardMessageErrorDto;
    FieldMessageErrorDto fieldMessageErrorDto;
    UserLocalStore userLocalStore;
    private final Gson gson = new Gson();

    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userLocalStore = UserLocalStore.getInstance(this);
        buttonLogin = findViewById(R.id.buttonLogin);
        inputLogin = findViewById(R.id.inputLogin);
        inputPassword = findViewById(R.id.inputPassword);
        inputLoginLayout = findViewById(R.id.textLoginLayout);
        inputPasswordLayout = findViewById(R.id.textPasswordLayout);
        registerLink = findViewById(R.id.textViewRegisterLink);
        registerLink.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });
        buttonLogin.setOnClickListener(v -> login());
    }

    //==============================================================================================

    /**
     * The method responsible for logging the user into the application
     */
    private void login() {
        if (inputLogin.getText().toString() != null && inputPassword.getText().toString() != null) {
            String login = inputLogin.getText().toString();
            String password = inputPassword.getText().toString();
            LoginRequestDto loginRequestDto = new LoginRequestDto(login, password);

            OkHttpClient client = new OkHttpClient();
            String url = "http://airhubmaster.miloszgilga.pl/api/v1/auth/login";
            RequestBody body = RequestBody.create(gson.toJson(loginRequestDto), Constans.JSON);
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
                    runOnUiThread(() -> Toast.makeText(LoginActivity.this,
                            MESSAGE_ERROR_STANDARD, Toast.LENGTH_SHORT).show());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.code() == 200) {
                        loginResponseDto = gson.fromJson(response.body().string(), LoginResponseDto.class);
                        userLocalStore.storeUserData(loginResponseDto);
                        userLocalStore.setUserLoggedIn(true);
                        runOnUiThread(() -> {
                            Toast.makeText(LoginActivity.this,
                                    MESSAGE_CORRECT_LOGIN_IN, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                            startActivity(intent);
                            finish();
                        });
                    } else if (response.code() == 401) {
                        standardMessageErrorDto = gson.fromJson(response.body().string(), StandardMessageErrorDto.class);
                        runOnUiThread(() -> {
                            setSpanClass();
                            Toast.makeText(LoginActivity.this,
                                    standardMessageErrorDto.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                    } else if (response.code() == 400) {
                        fieldMessageErrorDto = gson.fromJson(response.body().string(), FieldMessageErrorDto.class);
                        for (Map.Entry<String, String> entry : fieldMessageErrorDto.getErrors().entrySet()) {
                            String key = entry.getKey();
                            String value = entry.getValue();
                            switch (key) {
                                case "loginOrEmail": {
                                    runOnUiThread(() -> {
                                        inputLoginLayout.setErrorEnabled(true);
                                        inputLoginLayout.setError(value);
                                    });
                                    break;
                                }
                                case "password": {
                                    runOnUiThread(() -> {
                                        inputPasswordLayout.setErrorEnabled(true);
                                        inputPasswordLayout.setError(value);
                                    });
                                    break;
                                }
                            }

                        }

                    }
                }
            });
        } else {
            setSpanClass();
        }
    }

    //==============================================================================================

    /**
     * A method responsible for setting the span class for inputs
     */
    private void setSpanClass() {
        inputLoginLayout.setErrorEnabled(true);
        inputLoginLayout.setError(MESSAGE_AUTHENTICATION);
        inputPasswordLayout.setErrorEnabled(true);
        inputPasswordLayout.setError(MESSAGE_AUTHENTICATION);
    }

    //==============================================================================================

    /**
     * The method responsible for checking if the user is already logged in on the device
     */
    @Override
    protected void onStart() {
        super.onStart();
        if (authenticate()) {
            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        }
    }

    //==============================================================================================

    /**
     * The helper method responsible for return logged data user
     *
     * @return logged user data
     */
    private boolean authenticate() {
        if (userLocalStore.getLoggedInUser() != null) {
            RefreshRequestDto refreshRequestDto = new RefreshRequestDto(userLocalStore.getJwtUserToken(),
                    userLocalStore.getRefreshUserToken());

            OkHttpClient client = new OkHttpClient();
            String url = "http://airhubmaster.miloszgilga.pl/api/v1/auth/refresh";
            RequestBody body = RequestBody.create(gson.toJson(refreshRequestDto), Constans.JSON);
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
                    userLocalStore.clearUserData();
                    Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    runOnUiThread(() -> Toast.makeText(LoginActivity.this,
                            MESSAGE_ERROR_STANDARD, Toast.LENGTH_SHORT).show());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.code() == 200) {
                        loginResponseDto = gson.fromJson(response.body().string(), LoginResponseDto.class);
                        userLocalStore.storeUserData(loginResponseDto);
                    } else {
                        userLocalStore.clearUserData();
                        Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        runOnUiThread(() -> Toast.makeText(LoginActivity.this,
                                MESSAGE_ERROR_PREFERENCES_LOGIN, Toast.LENGTH_SHORT).show());
                    }
                }
            });
        }
        return userLocalStore.getLoggedInUser() != null;
    }
}
