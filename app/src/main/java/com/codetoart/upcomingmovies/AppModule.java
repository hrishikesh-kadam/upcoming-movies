package com.codetoart.upcomingmovies;

import com.codetoart.upcomingmovies.presenter.MainActivityPresenter;
import com.codetoart.upcomingmovies.presenter.MainActivityPresenterImpl;
import com.codetoart.upcomingmovies.rest.TmdbApiV3;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Hrishikesh Kadam on 23/03/2018
 */

@Module
public class AppModule {

    private MainApplication mainApplication;

    public AppModule(MainApplication mainApplication) {

        this.mainApplication = mainApplication;
    }

    @Provides
    @Singleton
    public MainApplication provideMainApplication() {
        return mainApplication;
    }

    @Provides
    @Singleton
    public MainActivityPresenter provideMainActicityPresenter() {
        return new MainActivityPresenterImpl(mainApplication);
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit() {

        return new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    public TmdbApiV3 provideTmdbApiV3(Retrofit retrofit) {

        return retrofit.create(TmdbApiV3.class);
    }
}
