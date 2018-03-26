package com.codetoart.upcomingmovies.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hrishikesh Kadam on 23/03/2018
 */

public class MovieResult implements Parcelable {

    public static final Creator<MovieResult> CREATOR = new Creator<MovieResult>() {
        @Override
        public MovieResult createFromParcel(Parcel in) {
            return new MovieResult(in);
        }

        @Override
        public MovieResult[] newArray(int size) {
            return new MovieResult[size];
        }
    };
    @SerializedName("poster_path")
    private String posterPath;
    private String title;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("adult")
    private Boolean adult;

    protected MovieResult(Parcel in) {
        posterPath = in.readString();
        title = in.readString();
        releaseDate = in.readString();
        byte tmpAdult = in.readByte();
        adult = tmpAdult == 0 ? null : tmpAdult == 1;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public Uri getFullPosterPath() {
        return Uri.parse("https://image.tmdb.org/t/p/")
                .buildUpon()
                .appendPath("w185")
                .appendEncodedPath(getPosterPath())
                .build();
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public Boolean getAdult() {
        return adult;
    }

    @Override
    public String toString() {
        return "MovieResult{" +
                "posterPath='" + posterPath + '\'' +
                ", title='" + title + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", adult=" + adult +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(posterPath);
        dest.writeString(title);
        dest.writeString(releaseDate);
        dest.writeByte((byte) (adult == null ? 0 : adult ? 1 : 2));
    }
}
