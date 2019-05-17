package com.example.myfavouritemovie.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static com.example.myfavouritemovie.sqlite.DatabaseContracts.MovieColumns.ID_MOVIE;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.MovieColumns.ORIGINAL_LANGUAGE_MOVIE;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.MovieColumns.ORIGINAL_TITLE_MOVIE;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.MovieColumns.OVERVIEW_MOVIE;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.MovieColumns.POPULAR_MOVIE;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.MovieColumns.POSTER_PATH_MOVIE;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.MovieColumns.RELEASE_MOVIE;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.MovieColumns.TITLE_MOVIE;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.MovieColumns.VOTE_AVERAGE_MOVIE;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.getColumnDouble;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.getColumnInt;
import static com.example.myfavouritemovie.sqlite.DatabaseContracts.getColumnString;

public class Movie implements Parcelable {
    @SerializedName("vote_average")
    @Expose
    private double voteAverage;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("popularity")
    @Expose
    private double popularity;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("original_language")
    @Expose
    private String originalLanguage;
    @SerializedName("original_title")
    @Expose
    private String originalTitle;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    @SerializedName("id")
    @Expose
    private Integer id;

    public Movie(double voteAverage, String title, double popularity, String posterPath, String originalLanguage, String originalTitle,
                 String overview, String releaseDate, Integer id){
        this.voteAverage = voteAverage;
        this.title = title;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.id = id;
    }
    public Movie(Cursor cursor){
        this.voteAverage = getColumnDouble(cursor, VOTE_AVERAGE_MOVIE);
        this.title = getColumnString(cursor, TITLE_MOVIE);
        this.popularity = getColumnDouble(cursor, POPULAR_MOVIE);
        this.posterPath = getColumnString(cursor, POSTER_PATH_MOVIE);
        this.originalLanguage = getColumnString(cursor, ORIGINAL_LANGUAGE_MOVIE);
        this.originalTitle = getColumnString(cursor, ORIGINAL_TITLE_MOVIE);
        this.overview = getColumnString(cursor, OVERVIEW_MOVIE);
        this.releaseDate = getColumnString(cursor, RELEASE_MOVIE);
        this.id = getColumnInt(cursor, ID_MOVIE);
    }
    public Movie() {

    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.voteAverage);
        dest.writeString(this.title);
        dest.writeDouble(this.popularity);
        dest.writeString(this.posterPath);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.originalTitle);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
        dest.writeInt(this.id);
    }

    protected Movie(Parcel in) {
        this.voteAverage = in.readDouble();
        this.title = in.readString();
        this.popularity = in.readDouble();
        this.posterPath = in.readString();
        this.originalLanguage = in.readString();
        this.originalTitle = in.readString();
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.id = in.readInt();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };


}
