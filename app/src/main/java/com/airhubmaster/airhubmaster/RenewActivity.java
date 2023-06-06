package com.airhubmaster.airhubmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.airhubmaster.airhubmaster.authFragment.RequestPasswordFragment;

/**
 * Activity responsible for basic user login to the application
 */
public class RenewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renew);
        getSupportActionBar().hide();
        replaceFragment(new RequestPasswordFragment());
    }

    //==============================================================================================

    /**
     * The method responsible for dynamically replacing views
     *
     * @param fragment dynamic view
     */
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutRenew, fragment);
        fragmentTransaction.commit();
    }
}
