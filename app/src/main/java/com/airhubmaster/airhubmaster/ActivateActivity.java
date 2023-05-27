package com.airhubmaster.airhubmaster;

import static com.airhubmaster.airhubmaster.utils.Constans.MESSAGE_ERROR_ACTIVATE;
import static com.airhubmaster.airhubmaster.utils.Constans.MESSAGE_ERROR_STANDARD;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.airhubmaster.airhubmaster.dto.api.ActivateAccountRequestDto;
import com.airhubmaster.airhubmaster.dto.api.ActivateAccountResponseDto;
import com.airhubmaster.airhubmaster.utils.Constans;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Activity responsible for activating a registered user's account
 */
public class ActivateActivity extends AppCompatActivity {

    /**
     * Declaring items from the view
     */
    Button buttonActivate;
    TextInputEditText inputToken;
    TextInputLayout inputTokenLayout;
    ActivateAccountRequestDto activateAccountRequestDto;
    ActivateAccountResponseDto activateAccountResponseDto;
    private final Gson gson = new Gson();

    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activate);
        buttonActivate = findViewById(R.id.buttonActivate);
        inputToken = findViewById(R.id.inputActivate);
        inputTokenLayout = findViewById(R.id.textActivateLayout);
        buttonActivate.setOnClickListener(v -> activateAccount());
    }

    //==============================================================================================

    /**
     * The method responsible for activating the user's account
     */
    private void activateAccount() {
        if (!Objects.requireNonNull(inputToken.getText()).toString().equals("")) {
            String token = inputToken.getText().toString();
            activateAccountRequestDto = new ActivateAccountRequestDto(token);

            OkHttpClient client = new OkHttpClient();
            String url = "http://airhubmaster.miloszgilga.pl/api/v1/auth/activate";
            RequestBody body = RequestBody.create(gson.toJson(activateAccountRequestDto), Constans.JSON);
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
                    runOnUiThread(() -> Toast.makeText(ActivateActivity.this,
                            MESSAGE_ERROR_STANDARD, Toast.LENGTH_SHORT).show());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.code() == 200) {
                        activateAccountResponseDto = gson.fromJson(response.body().string(),
                                ActivateAccountResponseDto.class);
                        Intent intent = new Intent(ActivateActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        runOnUiThread(() -> Toast.makeText(ActivateActivity.this,
                                activateAccountResponseDto.getMessage(), Toast.LENGTH_SHORT).show());
                    } else {
                        runOnUiThread(() -> {
                            inputTokenLayout.setErrorEnabled(true);
                            inputTokenLayout.setError(MESSAGE_ERROR_ACTIVATE);
                        });
                    }
                }
            });
        } else {
            inputTokenLayout.setErrorEnabled(true);
            inputTokenLayout.setError(MESSAGE_ERROR_ACTIVATE);
        }
    }
}
