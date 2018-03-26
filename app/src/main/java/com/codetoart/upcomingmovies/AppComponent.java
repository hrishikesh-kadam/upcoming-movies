package com.codetoart.upcomingmovies;

import com.codetoart.upcomingmovies.presenter.MainActivityPresenterImpl;
import com.codetoart.upcomingmovies.view.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Hrishikesh Kadam on 23/03/2018
 */

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {

    void inject(MainActivity mainActivity);

    void inject(MainActivityPresenterImpl mainActivityPresenterImpl);
}
