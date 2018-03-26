package com.codetoart.upcomingmovies.view;

import com.codetoart.upcomingmovies.model.MovieResponse;

import retrofit2.Response;

/**
 * Created by Hrishikesh Kadam on 26/03/2018
 */

public interface MainActivityView {

    void showMoviesList(Response<MovieResponse> movieResponseRetrofit);
}
