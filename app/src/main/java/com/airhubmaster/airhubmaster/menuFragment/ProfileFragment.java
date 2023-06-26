package com.airhubmaster.airhubmaster.menuFragment;

import static com.airhubmaster.airhubmaster.interceptor.ApiInterceptor.tokenExpiredInterceptor;
import static com.airhubmaster.airhubmaster.utils.Constans.MESSAGE_ERROR_STANDARD;
import static com.airhubmaster.airhubmaster.utils.Constans.URL_SERVER;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airhubmaster.airhubmaster.R;
import com.airhubmaster.airhubmaster.dto.api.ProfileResponseDto;
import com.airhubmaster.airhubmaster.localDataBase.UserLocalStore;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProfileFragment extends Fragment {

    NavigationView navigationView;
    TextView textHeaderMenuName;
    TextView textHeaderMenuLvl;
    TextView textLevelAnimatedExp;
    TextView textLevel;
    TextView textName;
    TextView textLogin;
    TextView textEmail;
    ProgressBar progressBar;
    UserLocalStore userLocalStore;
    ProfileResponseDto profileResponseDto;
    private final Gson gson = new Gson();

    public ProfileFragment() {
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = getView().findViewById(R.id.levelProgressBar);
        textLevelAnimatedExp = getView().findViewById(R.id.textViewLevelStatus);
        textLevel = getView().findViewById(R.id.levelTextView);
        textName = getView().findViewById(R.id.textViewNameProfile);
        textLogin = getView().findViewById(R.id.textViewLoginProfile);
        textEmail = getView().findViewById(R.id.textViewEmailProfile);
        navigationView = getActivity().findViewById(R.id.sideMenu);
        View headerView = navigationView.getHeaderView(0);

        textHeaderMenuName = headerView.findViewById(R.id.textViewSideBarUserName);
        textHeaderMenuLvl = headerView.findViewById(R.id.textViewSideBarUserLevel);
        getDataProfile();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    //==============================================================================================

    /**
     * The method responsible for downloading user profile data
     */
    private void getDataProfile() {
        userLocalStore = UserLocalStore.getInstance(getActivity());

        String url = URL_SERVER + "api/v1/account/details";
        Request request = new Request.Builder()
                .url(url)
                .get()
                .header("Authorization", "Bearer " + userLocalStore.getJwtUserToken())
                .header("Connection", "close")
                .header("Accept-language", "pl")
                .header("User-Agent", "mobile")
                .build();

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(tokenExpiredInterceptor(userLocalStore))
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),
                        MESSAGE_ERROR_STANDARD, Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    profileResponseDto = gson.fromJson(response.body().string(), ProfileResponseDto.class);

                    getActivity().runOnUiThread(() -> {
                        textName.setText(profileResponseDto.getFirstName() + " " + profileResponseDto.getLastName());
                        textLogin.setText(profileResponseDto.getLogin());
                        textEmail.setText(profileResponseDto.getEmailAddress());
                        textLevel.setText(String.valueOf(profileResponseDto.getLevel()));

                        ObjectAnimator.ofInt(progressBar, "progress", 0, profileResponseDto.getLevel())
                                .setDuration(1000)
                                .start();

                        ValueAnimator animator = ValueAnimator.ofInt(0, profileResponseDto.getExp());
                        animator.addUpdateListener(valueAnimator -> {
                            animator.setDuration(1000);
                            textLevelAnimatedExp.setText(valueAnimator.getAnimatedValue().toString() + " / 25 exp");
                        });
                        animator.start();

                        textHeaderMenuName.setText(profileResponseDto.getFirstName() + " " + profileResponseDto.getLastName());
                        textHeaderMenuLvl.setText("Poziom konta: " + String.valueOf(profileResponseDto.getLevel()));
                    });

                } else {
                    getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),
                            MESSAGE_ERROR_STANDARD, Toast.LENGTH_SHORT).show());
                }
            }
        });
    }
}
