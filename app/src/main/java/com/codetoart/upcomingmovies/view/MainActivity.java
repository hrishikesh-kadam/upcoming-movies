package com.codetoart.upcomingmovies.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.codetoart.upcomingmovies.MainApplication;
import com.codetoart.upcomingmovies.R;
import com.codetoart.upcomingmovies.model.MovieResponse;
import com.codetoart.upcomingmovies.presenter.MainActivityPresenter;

import javax.inject.Inject;

import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MainActivityView {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Inject
    MainActivityPresenter mainActivityPresenter;
    private Response<MovieResponse> movieResponseRetrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(LOG_TAG, "-> onCreate");
        setContentView(R.layout.activity_main);

        ((MainApplication) getApplication()).getAppComponent().inject(this);
        mainActivityPresenter.setView(this);

        getUpcomingMovies();
    }

    public void getUpcomingMovies() {
        Log.v(LOG_TAG, "-> getUpcomingMovies");

        mainActivityPresenter.getUpcomingMovies();
    }

    @Override
    public void showMoviesList(Response<MovieResponse> movieResponseRetrofit) {
        Log.v(LOG_TAG, "-> showMoviesList");

        this.movieResponseRetrofit = movieResponseRetrofit;

        if (movieResponseRetrofit == null || !movieResponseRetrofit.isSuccessful())
            Log.e(LOG_TAG, "-> showMoviesList -> Error");
        else
            Log.d(LOG_TAG, "-> showMoviesList -> " + movieResponseRetrofit.body());
    }
}
