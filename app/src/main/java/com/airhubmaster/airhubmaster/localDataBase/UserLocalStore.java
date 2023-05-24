package com.airhubmaster.airhubmaster.localDataBase;

import android.content.Context;
import android.content.SharedPreferences;

import com.airhubmaster.airhubmaster.dto.api.LoginResponseDto;

/**
 * The class responsible for storing tokens and handling logging in and logging out user from the app
 */
public class UserLocalStore {

    /**
     * Variable declaration
     */
    private static UserLocalStore instance;
    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    //==============================================================================================

    public static UserLocalStore getInstance(Context context) {
        if (instance == null)
            instance = new UserLocalStore(context);
        return instance;
    }

    //==============================================================================================

    private UserLocalStore(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    //==============================================================================================

    public String getJwtUserToken() {
        return userLocalDatabase.getString("jwtToken", "");
    }

    //==============================================================================================

    public String getRefreshUserToken() {
        return userLocalDatabase.getString("refreshToken", "");
    }

    //==============================================================================================

    public void storeUserData(LoginResponseDto loginResponseDto) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putString("jwtToken", loginResponseDto.getJwtToken());
        userLocalDatabaseEditor.putString("refreshToken", loginResponseDto.getRefreshToken());
        userLocalDatabaseEditor.commit();
    }

    //==============================================================================================

    public void setUserLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putBoolean("loggedIn", loggedIn);
        userLocalDatabaseEditor.commit();
    }

    //==============================================================================================

    public void clearUserData() {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.clear();
        userLocalDatabaseEditor.commit();
    }

    //==============================================================================================

    public LoginResponseDto getLoggedInUser() {
        if (!userLocalDatabase.getBoolean("loggedIn", false)) {
            return null;
        }
        String jwtToken = userLocalDatabase.getString("jwtToken", "");
        String refreshToken = userLocalDatabase.getString("refreshToken", "");
        return new LoginResponseDto(jwtToken, refreshToken);
    }
}
