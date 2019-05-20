package com.example.movieprovider.fragment.tv;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieprovider.R;

import com.example.movieprovider.api.BaseApiService;
import com.example.movieprovider.api.UtilsAPI;
import com.example.movieprovider.fragment.movie.AdapterMovie;
import com.example.movieprovider.fragment.movie.MovieDataResponse;
import com.example.movieprovider.fragment.movie.MoviePresenter;
import com.example.movieprovider.fragment.tv.AdapterTv;
import com.example.movieprovider.fragment.tv.Tv;
import com.example.movieprovider.fragment.tv.TvDataResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("ALL")
public class TvFragment extends Fragment implements TvView {


    private AdapterMovie adapter;
    private RecyclerView rv_tv;
    private List<Tv> listTv = new ArrayList<>();
    private List<TvDataResponse> listTvResponse = new ArrayList<>();
    private TvPresenter presenter;
    private LinearLayout lyt_edtxt;
    private ImageButton bt_clear;
    private EditText et_search;
    public TvFragment() {
        // Required empty public constructor
    }

    private FrameLayout frameLayout;
    private View view;
    private ProgressBar progress_circular;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        frameLayout = new FrameLayout(getActivity());
        inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.fragment_tv, null);
        frameLayout.addView(view);
        initToolbar(view);
        rv_tv = view.findViewById(R.id.rv_tv);
        lyt_edtxt = view.findViewById(R.id.lyt_edtxt);
        et_search = view.findViewById(R.id.et_search);
        bt_clear = view.findViewById(R.id.bt_clear);
        progress_circular = view.findViewById(R.id.progress_circular);
        progress_circular.bringToFront();
        presenter = new TvPresenter(this, listTvResponse);
        setHasOptionsMenu(true);
        initCompontent();
        return frameLayout;
    }

    private void initToolbar(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.tv_show));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.eng) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        } else if (item.getItemId() == R.id.search) {
            lyt_edtxt.setVisibility(View.VISIBLE);
            item.setVisible(false);
        }else if (item.getItemId() == R.id.daily_reminder){
            /*Intent intent = new Intent(getActivity(), DailyReminderActivity.class);
            startActivity(intent);*/
        }
        onClickitem(item);
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        ((AppCompatActivity) getActivity()).getMenuInflater().inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.showData();
    }


    @Override
    public void showProgress() {
        progress_circular.setVisibility(View.VISIBLE);
        rv_tv.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progress_circular.setVisibility(View.GONE);
        rv_tv.setVisibility(View.VISIBLE);
    }

    @Override
    public void getDataMovie(List<Tv> listMovie) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        rv_tv.setLayoutManager(layoutManager);
        AdapterTv adapter = new AdapterTv(getActivity());
        adapter.setListMovie(listMovie);
        rv_tv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAddError(String message) {
        Toast.makeText(getContext(), message + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }
    private void onClickitem(final MenuItem item) {
        bt_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_search.getText().toString().isEmpty()) {
                    lyt_edtxt.setVisibility(View.GONE);
                    item.setVisible(true);
                } else {
                    et_search.setText("");
                }
            }
        });
    }

    private void initCompontent() {
        et_search.addTextChangedListener(textWatcher);
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideKeyboard();
                    searchAction();
                }
                return false;
            }
        });
    }

    private void searchAction() {

        final String query = et_search.getText().toString().trim();
        if (!query.equals("")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    presenter.searchDataTv(query);
                }
            }, 500);
        } else {
            Toast.makeText(getActivity(), "Please fill search input", Toast.LENGTH_SHORT).show();
        }
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence c, int i, int i1, int i2) {
            if (c.toString().trim().length() == 0) {
                bt_clear.setVisibility(View.GONE);
            } else {
                bt_clear.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence c, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    private void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
