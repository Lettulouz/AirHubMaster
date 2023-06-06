package com.airhubmaster.airhubmaster.utils;

import okhttp3.MediaType;

/**
 * The class stores constants and helper variables that are globally available
 */
public class Constans {

    /**
     * Variable declaration
     */
    public static final String URL_SERVER = "https://api.airhubmaster.com/";
    public static final String MESSAGE_AUTHENTICATION = "Podaj poprawne dane.";
    public static final String MESSAGE_CORRECT_LOGIN_IN = "Poprawnie zalogowano.";
    public static final String MESSAGE_ERROR_STANDARD = "Wystąpił błąd podczas łączenia.";
    public static final String MESSAGE_ERROR_PREFERENCES_LOGIN = "Wystąpił błąd, zaloguj się ponownie.";
    public static final String MESSAGE_ERROR_ACTIVATE = "Podaj poprawny token.";
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    //==============================================================================================

    private Constans() {
    }
}
