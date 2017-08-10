package com.zyr.converter.network;


import com.zyr.library.FastJsonConverterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class ApiClient {
    private static volatile ApiClient apiClient;

    private static final int TIMEOUT_CONNECTION = 60_000;
    private static final int TIMEOUT_READ = 90_000;

    private Service service;

    private ApiClient() {
    }

    public static ApiClient getInstance() {
        if (apiClient == null) {
            synchronized (ApiClient.class) {
                if (apiClient == null) {
                    apiClient = new ApiClient();
                }
            }
        }
        return apiClient;
    }

    public void init() {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.MILLISECONDS)
                .readTimeout(TIMEOUT_READ, TimeUnit.MILLISECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(FastJsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        service = retrofit.create(Service.class);
    }

    public Service getService() {
        return service;
    }
}
