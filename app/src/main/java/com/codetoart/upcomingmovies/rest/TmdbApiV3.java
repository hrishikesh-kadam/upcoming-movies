package com.codetoart.upcomingmovies.rest;

import com.codetoart.upcomingmovies.model.MovieResponse;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Hrishikesh Kadam on 23/03/2018
 */

public interface TmdbApiV3 {

    @GET("movie/upcoming")
    Observable<Response<MovieResponse>> getUpcomingMovies(@Query("api_key") String apiKey);
}
