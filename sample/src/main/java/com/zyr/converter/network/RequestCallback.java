package com.zyr.converter.network;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class RequestCallback<T> implements Callback<T> {

    public abstract void success(T t);

    public abstract void failure(String error);

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            success(response.body());
            return;
        }

        failure("error");
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        failure("Error");
    }
}
