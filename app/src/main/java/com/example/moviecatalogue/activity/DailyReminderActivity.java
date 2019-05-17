package com.example.moviecatalogue.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.api.BaseApiService;
import com.example.moviecatalogue.api.UtilsAPI;
import com.example.moviecatalogue.fragment.movie.Movie;
import com.example.moviecatalogue.fragment.movie.MovieDataResponse;
import com.example.moviecatalogue.notification.AlarmReceiver;
import com.example.moviecatalogue.notification.ReleaseReceiver;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DailyReminderActivity extends AppCompatActivity {
    private SwitchCompat switch_daily_reminder, switch_release_reminder;
    private AlarmReceiver alarmReceiver = new AlarmReceiver();
    private ReleaseReceiver releaseReceiver = new ReleaseReceiver();
    private String my_shared_preferences = "SHARE_PREFERENCE";
    private String daily_preferences = "daily_preferencde";
    private String release_preferences = "daily_preferencde";
    private String date_preferences = "date_preferencde";
    Boolean checkedDaily = false;
    Boolean checkedRelease = false;
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_reminder);
        switch_daily_reminder = findViewById(R.id.daily_reminder);
        switch_release_reminder = findViewById(R.id.release_reminder);
        initToolbar();
        dailyChecked();
        releaseChecked();
        editor = sharedpreferences.edit();


        switch_daily_reminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (switch_daily_reminder.isChecked()) {
                    checkedDaily = true;
                    switch_daily_reminder.setChecked(true);
                    editor.putBoolean(daily_preferences, checkedDaily);
                    dailyReminderAlarm();
                    switch_daily_reminder.setChecked(true);
                } else {
                    checkedDaily = false;
                    switch_daily_reminder.setChecked(false);
                    editor.putBoolean(daily_preferences, checkedDaily);
                    alarmReceiver.cancelAlarm(DailyReminderActivity.this, AlarmReceiver.TYPE_REPEATING);

                }
                editor.apply();
            }
        });
        switch_release_reminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (switch_release_reminder.isChecked()) {
                    checkedRelease = true;
                    switch_release_reminder.setChecked(true);
                    editor.putBoolean(release_preferences, checkedRelease);
                    searchDataMovie();
                } else {
                    checkedRelease = false;
                    switch_release_reminder.setChecked(false);
                    editor.putBoolean(release_preferences, checkedRelease);
                    releaseReceiver.cancelAlarm(DailyReminderActivity.this);

                }
                editor.apply();
            }
        });

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.settings));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void searchDataMovie() {
        Calendar calander = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
        Date date = new Date();
        final String currentDate = dateFormat.format(date);
        BaseApiService mApiService = UtilsAPI.getApiService();
        mApiService.getMovieRelease().enqueue(new Callback<MovieDataResponse>() {
            @Override
            public void onResponse(Call<MovieDataResponse> call, Response<MovieDataResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        int total = response.body().getMovies().size();

                        for (int i = 0; i < 1; i++) {

                            Movie m = new Movie(response.body().getMovies().get(i).getVoteAverage(),
                                    response.body().getMovies().get(i).getTitle(),
                                    response.body().getMovies().get(i).getPopularity(),
                                    response.body().getMovies().get(i).getPosterPath(),
                                    response.body().getMovies().get(i).getOriginalLanguage(),
                                    response.body().getMovies().get(i).getOriginalTitle(),
                                    response.body().getMovies().get(i).getOverview(),
                                    response.body().getMovies().get(i).getReleaseDate(),
                                    response.body().getMovies().get(i).getId());
                            if (currentDate.equals(response.body().getMovies().get(i).getReleaseDate())){
                                dailyReleaseAlaram(m);
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(DailyReminderActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieDataResponse> call, Throwable t) {
                Toast.makeText(DailyReminderActivity.this, "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void dailyChecked() {
        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        checkedDaily = sharedpreferences.getBoolean(daily_preferences, false);
        if (checkedDaily) {
            switch_daily_reminder.setChecked(true);
        } else {
            switch_daily_reminder.setChecked(false);
        }
    }

    private void releaseChecked() {
        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        checkedRelease = sharedpreferences.getBoolean(release_preferences, false);
        if (checkedRelease) {
            switch_release_reminder.setChecked(true);
        } else {
            switch_release_reminder.setChecked(false);
        }
    }

    private void dailyReleaseAlaram(Movie movie) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 6);
        calendar.set(Calendar.MINUTE, 0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String time = dateFormat.format(calendar.getTime());


        releaseReceiver.setReleaseReminderAlarm(this, ReleaseReceiver.TYPE_ONE_TIME_RELEASE, time, movie);
    }

    private void dailyReminderAlarm() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 6);
        calendar.set(Calendar.MINUTE, 0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String time = dateFormat.format(calendar.getTime());
        alarmReceiver.setDailyReminderAlarm(this, AlarmReceiver.TYPE_REPEATING, time, getResources().getString(R.string.daily_message));
    }

}
