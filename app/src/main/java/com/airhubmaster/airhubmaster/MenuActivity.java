package com.airhubmaster.airhubmaster;

import static com.airhubmaster.airhubmaster.utils.Constans.URL_SERVER;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.airhubmaster.airhubmaster.databinding.ActivityMenuBinding;
import com.airhubmaster.airhubmaster.dto.api.LogoutResponseDto;
import com.airhubmaster.airhubmaster.dto.api.RefreshRequestDto;
import com.airhubmaster.airhubmaster.gameFragment.MainFragment;
import com.airhubmaster.airhubmaster.gameFragment.PersonnelFragment;
import com.airhubmaster.airhubmaster.gameFragment.PlaneFragment;
import com.airhubmaster.airhubmaster.localDataBase.UserLocalStore;
import com.airhubmaster.airhubmaster.menuFragment.ProfileFragment;
import com.airhubmaster.airhubmaster.menuFragment.UserDataFragment;
import com.airhubmaster.airhubmaster.utils.Constans;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Activity responsible for displaying and handling each individual fragments of the game
 */
public class MenuActivity extends AppCompatActivity {

    /**
     * Declaring items from the view
     */
    Button buttonMarket;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    FloatingActionButton airportButton;
    ActivityMenuBinding binding;
    UserLocalStore userLocalStore;
    LogoutResponseDto logoutResponseDto;
    private final Gson gson = new Gson();

    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        airportButton = findViewById(R.id.buttonMenuMain);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.sideMenu);
        drawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, R.string.openMenu, R.string.closeMenu);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.profileSideIcon) {
                checkMenu();
                replaceFragment(new ProfileFragment());
            } else if (item.getItemId() == R.id.helpSideIcon) {
                Toast.makeText(this, "kontakt", Toast.LENGTH_SHORT).show();
            } else if (item.getItemId() == R.id.changeUserDataIcon) {
                checkMenu();
                replaceFragment(new UserDataFragment());
            } else if (item.getItemId() == R.id.logoutSideIcon) {
                Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
                logoutUser();
                userLocalStore.clearUserData();
            }
            return false;
        });

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.personnelIcon) {
                checkMenu();
                replaceFragment(new PersonnelFragment());
            } else if (item.getItemId() == R.id.planeIcon) {
                checkMenu();
                replaceFragment(new PlaneFragment());
            }
            return true;
        });

        airportButton.setOnClickListener(v -> {
            checkMenu();
            replaceFragment(new MainFragment());
        });
    }

    //==============================================================================================

    private void checkMenu() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    //==============================================================================================

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //==============================================================================================

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.currency_top_menu, menu);
        MenuItem itemButton = menu.findItem(R.id.buttonCurrencyBar);
        View view = itemButton.getActionView();
        buttonMarket = view.findViewById(R.id.currencyButton);
        ValueAnimator animator = ValueAnimator.ofInt(0, 69420);
        animator.addUpdateListener(valueAnimator -> {
            animator.setDuration(1500);
            buttonMarket.setText(valueAnimator.getAnimatedValue().toString());
        });
        animator.start();
        buttonMarket.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, ShopActivity.class);
            startActivity(intent);
        });
        return super.onCreateOptionsMenu(menu);
    }

    //==============================================================================================

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
        fragmentTransaction.replace(R.id.frameLayoutMenu, fragment);
        fragmentTransaction.commit();
    }

    //==============================================================================================

    /**
     * The method responsible for logout user from application
     */
    void logoutUser() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .addInterceptor(this::intercept)
                .build();
        userLocalStore = UserLocalStore.getInstance(MenuActivity.this);
        String url = URL_SERVER + "api/v1/auth/logout";
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + userLocalStore.getJwtUserToken())
                .header("Accept-language", "pl")
                .header("User-Agent", "mobile")
                .header("Connection", "close")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    logoutResponseDto = gson.fromJson(response.body().string(), LogoutResponseDto.class);
                    runOnUiThread(() -> Toast.makeText(MenuActivity.this,
                            logoutResponseDto.getMessage(), Toast.LENGTH_SHORT).show());
                    finish();
                    System.exit(0);
                }

            }
        });
    }

    //==============================================================================================

    public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        Request newRequest;
        userLocalStore = UserLocalStore.getInstance(MenuActivity.this);
        RefreshRequestDto refreshRequestDto = new RefreshRequestDto(userLocalStore.getJwtUserToken(),
                userLocalStore.getRefreshUserToken());
        String url = URL_SERVER + "api/v1/auth/refresh";
        RequestBody body = RequestBody.create(gson.toJson(refreshRequestDto), Constans.JSON);

        newRequest = request.newBuilder()
                .url(url)
                .post(body)
                .header("Connection", "close")
                .header("Accept-language", "pl")
                .header("User-Agent", "mobile")
                .build();
        return chain.proceed(newRequest);
    }
}
