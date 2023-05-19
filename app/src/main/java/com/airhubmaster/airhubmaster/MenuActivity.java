package com.airhubmaster.airhubmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * Activity responsible for displaying and handling each individual fragments of the game
 */
public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }
}