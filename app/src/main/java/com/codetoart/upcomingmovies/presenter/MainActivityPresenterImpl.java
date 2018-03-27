package com.codetoart.upcomingmovies.presenter;

import android.util.Log;

import com.codetoart.upcomingmovies.BuildConfig;
import com.codetoart.upcomingmovies.MainApplication;
import com.codetoart.upcomingmovies.model.MovieResponse;
import com.codetoart.upcomingmovies.rest.TmdbApiV3;
import com.codetoart.upcomingmovies.view.MainActivityView;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by Hrishikesh Kadam on 26/03/2018
 */

public class MainActivityPresenterImpl implements MainActivityPresenter {

    private static final String LOG_TAG = MainActivityPresenterImpl.class.getSimpleName();
    @Inject
    TmdbApiV3 tmdbApiV3;
    private Response<MovieResponse> movieResponseRetrofit;
    private Throwable movieResponseThrowable;
    private MainActivityView mainActivityView;
    private MovieResponseObserver movieResponseObserver;

    public MainActivityPresenterImpl(MainApplication mainApplication) {

        mainApplication.getAppComponent().inject(this);
    }

    @Override
    public void setView(MainActivityView mainActivityView) {

        this.mainActivityView = mainActivityView;
    }

    @Override
    public void getUpcomingMovies() {
        Log.v(LOG_TAG, "-> getUpcomingMovies");

        if (movieResponseObserver == null) {

            movieResponseObserver = tmdbApiV3.getUpcomingMovies(BuildConfig.tmdbApiKey)
                    .subscribeOn(Schedulers.io())
                    .delay(5, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new MovieResponseObserver());

        } else if (movieResponseObserver.isCompleted()) {

            if (movieResponseRetrofit == null)
                movieResponseObserver.onError(movieResponseThrowable);

            else {
                movieResponseObserver.onNext(movieResponseRetrofit);
                movieResponseObserver.onComplete();
            }
        }
    }

    private class MovieResponseObserver extends DisposableObserver<Response<MovieResponse>> {

        private boolean completed;

        public boolean isCompleted() {
            return completed;
        }

        @Override
        public void onNext(Response<MovieResponse> movieResponseRetrofit) {
            Log.v(LOG_TAG, "-> onNext");

            MainActivityPresenterImpl.this.movieResponseRetrofit = movieResponseRetrofit;

            if (!movieResponseRetrofit.isSuccessful()) {

                try {
                    onError(new Throwable(movieResponseRetrofit.errorBody().string()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {

                mainActivityView.showMoviesList(movieResponseRetrofit);
            }
        }

        @Override
        public void onError(Throwable e) {
            Log.e(LOG_TAG, "-> onError " + e);

            movieResponseThrowable = e;
            completed = true;

            mainActivityView.showMoviesList(null);
        }

        @Override
        public void onComplete() {
            Log.v(LOG_TAG, "-> onComplete");

            completed = true;
        }
    }
}
