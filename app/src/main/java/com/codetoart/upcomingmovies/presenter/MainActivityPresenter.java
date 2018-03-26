package com.codetoart.upcomingmovies.presenter;

import com.codetoart.upcomingmovies.view.MainActivityView;

/**
 * Created by Hrishikesh Kadam on 26/03/2018
 */

public interface MainActivityPresenter {

    public void setView(MainActivityView mainActivityView);

    public void getUpcomingMovies();
}
