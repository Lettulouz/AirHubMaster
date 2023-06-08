package com.airhubmaster.airhubmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ShopActivity extends AppCompatActivity {

    Button buttonMarket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.currency_top_menu, menu);
        MenuItem itemButton = menu.findItem(R.id.buttonCurrencyBar);
        View view = itemButton.getActionView();
        buttonMarket = view.findViewById(R.id.currencyButton);
//        ValueAnimator animator = ValueAnimator.ofInt(0, 69420);
//        animator.addUpdateListener(valueAnimator -> {
//            animator.setDuration(1500);
//            buttonMarket.setText(valueAnimator.getAnimatedValue().toString());
//        });
//        animator.start();
        return super.onCreateOptionsMenu(menu);
    }
}