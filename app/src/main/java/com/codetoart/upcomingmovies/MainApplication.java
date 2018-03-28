package com.codetoart.upcomingmovies;

import android.app.Application;

import com.codetoart.upcomingmovies.di.AppComponent;
import com.codetoart.upcomingmovies.di.AppModule;
import com.codetoart.upcomingmovies.di.DaggerAppComponent;

/**
 * Created by Hrishikesh Kadam on 23/03/2018
 */

public class MainApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
