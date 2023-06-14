package com.airhubmaster.airhubmaster;

import static com.airhubmaster.airhubmaster.interceptor.ApiInterceptor.tokenExpiredInterceptor;
import static com.airhubmaster.airhubmaster.utils.Constans.MESSAGE_AUTHENTICATION;
import static com.airhubmaster.airhubmaster.utils.Constans.MESSAGE_ERROR_STANDARD;
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
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.airhubmaster.airhubmaster.databinding.ActivityMenuBinding;
import com.airhubmaster.airhubmaster.dto.api.DeleteUserAccountRequestDto;
import com.airhubmaster.airhubmaster.dto.api.DeleteUserAccountResponseDto;
import com.airhubmaster.airhubmaster.dto.api.LogoutResponseDto;
import com.airhubmaster.airhubmaster.dto.api.StandardMessageErrorDto;
import com.airhubmaster.airhubmaster.gameFragment.MainFragment;
import com.airhubmaster.airhubmaster.gameFragment.PersonnelFragment;
import com.airhubmaster.airhubmaster.gameFragment.PlaneFragment;
import com.airhubmaster.airhubmaster.gameFragment.ServiceFragment;
import com.airhubmaster.airhubmaster.localDataBase.UserLocalStore;
import com.airhubmaster.airhubmaster.menuFragment.ProfileFragment;
import com.airhubmaster.airhubmaster.menuFragment.UserDataFragment;
import com.airhubmaster.airhubmaster.utils.Constans;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
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
    Dialog dialogLogout;
    Button buttonLogoutAccept;
    Button buttonLogoutCancel;
    Dialog dialogDelete;
    Button buttonDeleteAccept;
    Button buttonDeleteCancel;
    TextInputEditText inputPasswordDelete;
    TextInputLayout inputPasswordDeleteLayout;
    Button buttonMarket;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    FloatingActionButton airportButton;
    ActivityMenuBinding binding;
    UserLocalStore userLocalStore;
    LogoutResponseDto logoutResponseDto;
    StandardMessageErrorDto standardMessageErrorDto;
    DeleteUserAccountRequestDto deleteUserAccountRequestDto;
    DeleteUserAccountResponseDto deleteUserAccountResponseDto;
    private final Gson gson = new Gson();

    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        replaceFragment(new MainFragment());
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
                dialogLogout.show();
            } else if (item.getItemId() == R.id.deleteAccountSideIcon) {
                dialogDelete.show();
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
            } else if (item.getItemId() == R.id.serviceIcon) {
                checkMenu();
                replaceFragment(new ServiceFragment());
            }
            return true;
        });

        airportButton.setOnClickListener(v -> {
            checkMenu();
            replaceFragment(new MainFragment());
        });

        setDialogLogout();
        setDialogDelete();
        buttonLogoutAccept = dialogLogout.findViewById(R.id.buttonLogoutAccept);
        buttonLogoutCancel = dialogLogout.findViewById(R.id.buttonLogoutCancel);
        buttonLogoutAccept.setOnClickListener(v -> logoutUser());
        buttonLogoutCancel.setOnClickListener(v -> dialogLogout.dismiss());

        inputPasswordDelete = dialogDelete.findViewById(R.id.inputPasswordDeleteAccount);
        inputPasswordDeleteLayout = dialogDelete.findViewById(R.id.textPasswordDeleteAccountLayout);
        buttonDeleteAccept = dialogDelete.findViewById(R.id.buttonDeleteAccept);
        buttonDeleteCancel = dialogDelete.findViewById(R.id.buttonDeleteCancel);
        buttonDeleteAccept.setOnClickListener(v -> deleteUserAccount());
        buttonDeleteCancel.setOnClickListener(v -> dialogDelete.dismiss());
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

    @Override
    protected void onPause() {
        super.onPause();
        dialogDelete.dismiss();
        dialogLogout.dismiss();
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
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    //==============================================================================================

    /**
     * The method responsible for logout user from application
     */
    void logoutUser() {
        userLocalStore = UserLocalStore.getInstance(MenuActivity.this);
        String url = URL_SERVER + "api/v1/auth/logout";
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + userLocalStore.getJwtUserToken())
                .header("Accept-language", "pl")
                .header("User-Agent", "mobile")
                .header("Connection", "close")
                .build();

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(tokenExpiredInterceptor(userLocalStore))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(MenuActivity.this,
                        MESSAGE_ERROR_STANDARD, Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    logoutResponseDto = gson.fromJson(response.body().string(), LogoutResponseDto.class);
                    runOnUiThread(() -> Toast.makeText(MenuActivity.this,
                            logoutResponseDto.getMessage(), Toast.LENGTH_SHORT).show());
                    userLocalStore.clearUserData();
                    finish();
                    System.exit(0);
                } else {
                    runOnUiThread(() -> Toast.makeText(MenuActivity.this,
                            MESSAGE_ERROR_STANDARD, Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    //==============================================================================================

    /**
     * The method responsible for delete user account
     */
    void deleteUserAccount() {
        String password = inputPasswordDelete.getText().toString();
        deleteUserAccountRequestDto = new DeleteUserAccountRequestDto(password);
        userLocalStore = UserLocalStore.getInstance(MenuActivity.this);

        String url = URL_SERVER + "api/v1/account";
        RequestBody body = RequestBody.create(gson.toJson(deleteUserAccountRequestDto), Constans.JSON);
        Request request = new Request.Builder()
                .url(url)
                .delete(body)
                .header("Authorization", "Bearer " + userLocalStore.getJwtUserToken())
                .header("Accept-language", "pl")
                .header("User-Agent", "mobile")
                .header("Connection", "close")
                .build();

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(tokenExpiredInterceptor(userLocalStore))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(MenuActivity.this,
                        MESSAGE_ERROR_STANDARD, Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    deleteUserAccountResponseDto = gson.fromJson(response.body().string(), DeleteUserAccountResponseDto.class);
                    runOnUiThread(() -> Toast.makeText(MenuActivity.this,
                            deleteUserAccountResponseDto.getMessage(), Toast.LENGTH_LONG).show());
                    InputMethodManager inputManager = (InputMethodManager) MenuActivity.this
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    View currentFocusedView = MenuActivity.this.getCurrentFocus();
                    if (currentFocusedView != null) {
                        inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    userLocalStore.clearUserData();
                    finish();
                    System.exit(0);
                } else if (response.code() == 400) {
                    standardMessageErrorDto = gson.fromJson(response.body().string(), StandardMessageErrorDto.class);
                    runOnUiThread(() -> {
                        inputPasswordDeleteLayout.setErrorEnabled(true);
                        inputPasswordDeleteLayout.setError(MESSAGE_AUTHENTICATION);
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(MenuActivity.this,
                            MESSAGE_ERROR_STANDARD, Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    //==============================================================================================

    /**
     * The method responsible for create logout user pop up dialog
     */
    public void setDialogLogout() {
        dialogLogout = new Dialog(this);
        dialogLogout.setContentView(R.layout.dialog_logout);
        dialogLogout.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background_dialog));
        dialogLogout.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        InsetDrawable inset = new InsetDrawable(getDrawable(R.drawable.background_dialog), 32, 0, 32, 0);
        dialogLogout.getWindow().setBackgroundDrawable(inset);
        dialogLogout.setCancelable(true);
    }

    //==============================================================================================

    /**
     * The method responsible for create delete user account pop up dialog
     */
    public void setDialogDelete() {
        dialogDelete = new Dialog(this);
        dialogDelete.setContentView(R.layout.dialog_delete);
        dialogDelete.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background_dialog));
        dialogDelete.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        InsetDrawable inset = new InsetDrawable(getDrawable(R.drawable.background_dialog), 32, 0, 32, 0);
        dialogDelete.getWindow().setBackgroundDrawable(inset);
        dialogDelete.setCancelable(true);
    }
}
