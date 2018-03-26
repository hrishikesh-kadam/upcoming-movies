package com.codetoart.upcomingmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Hrishikesh Kadam on 23/03/2018
 */

public class MovieResponse implements Parcelable {

    public static final Creator<MovieResponse> CREATOR = new Creator<MovieResponse>() {
        @Override
        public MovieResponse createFromParcel(Parcel in) {
            return new MovieResponse(in);
        }

        @Override
        public MovieResponse[] newArray(int size) {
            return new MovieResponse[size];
        }
    };
    private ArrayList<MovieResult> results = new ArrayList<>();

    protected MovieResponse(Parcel in) {
        results = in.createTypedArrayList(MovieResult.CREATOR);
    }

    public ArrayList<MovieResult> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "MovieResponse{" +
                "results=" + results +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(results);
    }
}
