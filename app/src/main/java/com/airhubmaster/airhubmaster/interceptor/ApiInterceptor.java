package com.airhubmaster.airhubmaster.interceptor;

import static com.airhubmaster.airhubmaster.utils.Constans.URL_SERVER;

import com.airhubmaster.airhubmaster.dto.api.LoginResponseDto;
import com.airhubmaster.airhubmaster.dto.api.RefreshRequestDto;
import com.airhubmaster.airhubmaster.localDataBase.UserLocalStore;
import com.airhubmaster.airhubmaster.utils.Constans;
import com.google.gson.Gson;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * The class responsible for storing the interceptor method that refreshes
 * the JWT token when it expires
 */
public class ApiInterceptor {

    /**
     * Variable declaration
     */
    private static final Gson gson = new Gson();

    /**
     * Interceptor responsible for renewing the user's expired JWT token in case of
     * returning code 401 in the query
     *
     * @param userLocalStore a class object containing a JWT token and a refresh token
     * @return
     */
    public static final Interceptor tokenExpiredInterceptor(UserLocalStore userLocalStore) {
        return chain -> {
            Response response = chain.proceed(chain.request());
            LoginResponseDto loginResponseDto;
            if (response.code() == 401) {
                RefreshRequestDto refreshRequestDto = new RefreshRequestDto(userLocalStore.getJwtUserToken(),
                        userLocalStore.getRefreshUserToken());
                OkHttpClient client = new OkHttpClient();
                String url = URL_SERVER + "api/v1/auth/refresh";
                RequestBody body = RequestBody.create(gson.toJson(refreshRequestDto), Constans.JSON);
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .header("Connection", "close")
                        .header("Accept-language", "pl")
                        .header("User-Agent", "mobile")
                        .build();
                Response httpResponse = client.newCall(request).execute();
                if (httpResponse.code() == 200) {
                    loginResponseDto = gson.fromJson(httpResponse.body().string(), LoginResponseDto.class);
                    userLocalStore.storeUserData(loginResponseDto);
                    response.close();
                    return chain.proceed(chain.request()
                            .newBuilder()
                            .removeHeader("Authorization")
                            .header("Authorization", "Bearer " + loginResponseDto.getJwtToken())
                            .build());
                }
                response.close();
            }
            return response;
        };
    }
}
