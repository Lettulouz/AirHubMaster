package com.airhubmaster.airhubmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;

/**
 * Activity responsible for basic new user register to the application
 */
public class RegisterActivity extends AppCompatActivity {

    /**
     * Declaring items from the view
     */
    TextView loginLink;

    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        loginLink = findViewById(R.id.textViewLoginLink);
        loginLink.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
