package com.airhubmaster.airhubmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.airhubmaster.airhubmaster.databinding.ActivityMenuBinding;
import com.airhubmaster.airhubmaster.gameFragment.PersonnelFragment;
import com.airhubmaster.airhubmaster.gameFragment.PlaneFragment;

/**
 * Activity responsible for displaying and handling each individual fragments of the game
 */
public class MenuActivity extends AppCompatActivity {

    ActivityMenuBinding binding;

    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.personnelIcon) {
                replaceFragment(new PersonnelFragment());
            } else if (item.getItemId() == R.id.planeIcon) {
                replaceFragment(new PlaneFragment());
            }
            return true;
        });
    }

    //==============================================================================================

    /**
     * The method responsible for dynamically replacing views
     * @param fragment dynamic view
     */
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutMenu, fragment);
        fragmentTransaction.commit();
    }
}
