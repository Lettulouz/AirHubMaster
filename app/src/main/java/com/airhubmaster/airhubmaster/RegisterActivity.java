package com.airhubmaster.airhubmaster;

import static com.airhubmaster.airhubmaster.utils.Constans.MESSAGE_ERROR_STANDARD;
import static com.airhubmaster.airhubmaster.utils.Constans.URL_SERVER;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airhubmaster.airhubmaster.dto.api.FieldMessageErrorDto;
import com.airhubmaster.airhubmaster.dto.api.RegisterRequestDto;
import com.airhubmaster.airhubmaster.dto.api.RegisterResponseDto;
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

/**
 * Activity responsible for basic new user register to the application
 */
public class RegisterActivity extends AppCompatActivity {

    /**
     * Declaring items from the view
     */
    Button buttonRegister;
    TextView loginLink;
    TextInputEditText inputFirstName;
    TextInputEditText inputLastName;
    TextInputEditText inputLogin;
    TextInputEditText inputEmail;
    TextInputEditText inputPassword;
    TextInputEditText inputRepeatPassword;
    TextInputLayout inputFirstNameLayout;
    TextInputLayout inputLastNameLayout;
    TextInputLayout inputLoginLayout;
    TextInputLayout inputEmailLayout;
    TextInputLayout inputPasswordLayout;
    TextInputLayout inputRepeatPasswordLayout;
    FieldMessageErrorDto fieldMessageErrorDto;
    StandardMessageErrorDto standardMessageErrorDto;
    RegisterRequestDto registerRequestDto;
    RegisterResponseDto registerResponseDto;
    private final Gson gson = new Gson();

    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        loginLink = findViewById(R.id.textViewLoginLink);
        buttonRegister = findViewById(R.id.buttonRegister);
        inputFirstName = findViewById(R.id.inputFirstName);
        inputLastName = findViewById(R.id.inputLastName);
        inputLogin = findViewById(R.id.inputLoginRegister);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPasswordRegister);
        inputRepeatPassword = findViewById(R.id.inputPasswordRegisterRepeat);
        inputFirstNameLayout = findViewById(R.id.textFirstNameLayout);
        inputLastNameLayout = findViewById(R.id.textLastNameLayout);
        inputLoginLayout = findViewById(R.id.textLoginRegisterLayout);
        inputEmailLayout = findViewById(R.id.textEmailLayout);
        inputPasswordLayout = findViewById(R.id.textPasswordRegisterLayout);
        inputRepeatPasswordLayout = findViewById(R.id.textPasswordRegisterRepeatLayout);

        buttonRegister.setOnClickListener(v -> register());
        loginLink.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    //==============================================================================================

    /**
     * The method responsible for register new user into the application
     */
    private void register() {
        String firstName = inputFirstName.getText().toString();
        String lastName = inputLastName.getText().toString();
        String login = inputLogin.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String repeatPassword = inputRepeatPassword.getText().toString();
        registerRequestDto = new RegisterRequestDto(firstName, lastName, login, email, password, repeatPassword);

        OkHttpClient client = new OkHttpClient();
        String url = URL_SERVER + "api/v1/auth/register";
        RequestBody body = RequestBody.create(gson.toJson(registerRequestDto), Constans.JSON);
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
                runOnUiThread(() -> Toast.makeText(RegisterActivity.this,
                        MESSAGE_ERROR_STANDARD, Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 201) {
                    registerResponseDto = gson.fromJson(response.body().string(), RegisterResponseDto.class);
                    runOnUiThread(() -> {
                        Toast.makeText(RegisterActivity.this,
                                registerResponseDto.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, ActivateActivity.class);
                        startActivity(intent);
                        finish();
                    });
                } else if (response.code() == 400) {
                    String responseBody = response.body().string();
                    fieldMessageErrorDto = gson.fromJson(responseBody, FieldMessageErrorDto.class);
                    if (fieldMessageErrorDto.getErrors() == null) {
                        standardMessageErrorDto = gson.fromJson(responseBody, StandardMessageErrorDto.class);
                        runOnUiThread(() -> {
                            Toast.makeText(RegisterActivity.this,
                                    standardMessageErrorDto.getMessage(), Toast.LENGTH_SHORT).show();
                            inputRepeatPasswordLayout.setErrorEnabled(true);
                            inputRepeatPasswordLayout.setError(standardMessageErrorDto.getMessage());
                        });
                    } else {
                        for (Map.Entry<String, String> entry : fieldMessageErrorDto.getErrors().entrySet()) {
                            String key = entry.getKey();
                            String value = entry.getValue();
                            switch (key) {
                                case "firstName": {
                                    runOnUiThread(() -> {
                                        inputFirstNameLayout.setErrorEnabled(true);
                                        inputFirstNameLayout.setError(value);
                                    });
                                    break;
                                }
                                case "lastName": {
                                    runOnUiThread(() -> {
                                        inputLastNameLayout.setErrorEnabled(true);
                                        inputLastNameLayout.setError(value);
                                    });
                                    break;
                                }
                                case "login": {
                                    runOnUiThread(() -> {
                                        inputLoginLayout.setErrorEnabled(true);
                                        inputLoginLayout.setError(value);
                                    });
                                    break;
                                }
                                case "emailAddress": {
                                    runOnUiThread(() -> {
                                        inputEmailLayout.setErrorEnabled(true);
                                        inputEmailLayout.setError(value);
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
                                case "confirmedPassword": {
                                    runOnUiThread(() -> {
                                        inputRepeatPasswordLayout.setErrorEnabled(true);
                                        inputRepeatPasswordLayout.setError(value);
                                    });
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    runOnUiThread(() -> Toast.makeText(RegisterActivity.this,
                            MESSAGE_ERROR_STANDARD, Toast.LENGTH_SHORT).show());
                }
            }
        });
    }
}
