package com.airhubmaster.airhubmaster;

import static com.airhubmaster.airhubmaster.utils.Constans.messageAuthentication;
import static com.airhubmaster.airhubmaster.utils.Constans.messageCorrectLoginIn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    /**
     * Declaring items from the view
     */
    Button buttonLogin;
    TextInputEditText inputLogin;
    TextInputEditText inputPassword;

    TextInputLayout inputLoginLayout;
    TextInputLayout inputPasswordLayout;

    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonLogin = findViewById(R.id.buttonLogin);
        inputLogin = findViewById(R.id.inputLogin);
        inputPassword = findViewById(R.id.inputPassword);
        inputLoginLayout = findViewById(R.id.textPasswordLayout);
        inputPasswordLayout = findViewById(R.id.textLoginLayout);
        buttonLogin.setOnClickListener(v -> login());
    }

    //==============================================================================================

    private void login() {

        if (Objects.requireNonNull(inputLogin.getText()).toString().equals("admin") &&
                Objects.requireNonNull(inputPassword.getText()).toString().equals("admin")) {
            Toast.makeText(MainActivity.this, messageCorrectLoginIn, Toast.LENGTH_SHORT).show();
        } else {
            inputLoginLayout.setErrorEnabled(true);
            inputLoginLayout.setError(messageAuthentication);
            inputPasswordLayout.setErrorEnabled(true);
            inputPasswordLayout.setError(messageAuthentication);
        }
    }
}
