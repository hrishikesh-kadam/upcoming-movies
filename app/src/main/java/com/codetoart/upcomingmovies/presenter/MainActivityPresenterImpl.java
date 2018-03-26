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

import io.reactivex.Observable;
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
    private Observable<Response<MovieResponse>> movieResponseObservable;

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

        if (movieResponseObservable == null) {

            movieResponseObservable = tmdbApiV3.getUpcomingMovies(BuildConfig.tmdbApiKey);

            movieResponseObserver = new MovieResponseObserver();
            movieResponseObserver.setHasCompleted(false);

            movieResponseObservable.subscribeOn(Schedulers.io())
                    .delay(5, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(movieResponseObserver);

        } else if (movieResponseObserver.isCompleted()) {

            if (movieResponseRetrofit == null)
                movieResponseObserver.onError(movieResponseThrowable);
            else
                movieResponseObserver.onNext(movieResponseRetrofit);

            movieResponseObserver.onComplete();
        }
    }

    private class MovieResponseObserver extends DisposableObserver<Response<MovieResponse>> {

        private boolean hasCompleted;

        public boolean isCompleted() {
            return hasCompleted;
        }

        public void setHasCompleted(boolean hasCompleted) {
            this.hasCompleted = hasCompleted;
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
            mainActivityView.showMoviesList(null);
        }

        @Override
        public void onComplete() {
            Log.v(LOG_TAG, "-> onComplete");

            hasCompleted = true;
        }
    }
}
