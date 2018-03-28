package com.codetoart.upcomingmovies.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.codetoart.upcomingmovies.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LauncherActivity extends AppCompatActivity {

    private static final String LOG_TAG = LauncherActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(LOG_TAG, "-> onCreate");

        setContentView(R.layout.activity_launcher);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.buttonMainActivity)
    public void onClickButtonMainActivity() {
        Log.v(LOG_TAG, "-> onClickButtonMainActivity");

        startActivity(new Intent(this, MainActivity.class));
    }
}
