package com.zyr.converter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zyr.converter.R;
import com.zyr.converter.bean.Repo;
import com.zyr.converter.network.ApiClient;
import com.zyr.converter.network.RequestCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.get).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiClient.getInstance().getService().listRepos("ZYRzyr").enqueue(new RequestCallback<List<Repo>>() {
                    @Override
                    public void success(List<Repo> repos) {
                        for (Repo r : repos) {
                            System.out.println(r);
                        }
                    }

                    @Override
                    public void failure(String error) {
                    }
                });
            }
        });
    }
}
