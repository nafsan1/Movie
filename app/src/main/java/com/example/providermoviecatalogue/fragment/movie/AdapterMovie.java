package com.example.providermoviecatalogue.fragment.movie;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.providermoviecatalogue.R;
import com.example.providermoviecatalogue.activity.DetailActivity;


import java.util.ArrayList;
import java.util.List;

import static com.example.providermoviecatalogue.MainActivity.EXTRA_MOVIES;
import static com.example.providermoviecatalogue.MainActivity.TYPE_MOVIE_INTENT;
import static com.example.providermoviecatalogue.sqlite.DatabaseContracts.MovieColumns.CONTENT_URI_MOVIE;


public class AdapterMovie extends RecyclerView.Adapter<AdapterMovie.CategoryViewHolder> {
    private Context context;
    private List<Movie> listMovie = new ArrayList<>();

    public AdapterMovie(Context context) {
        this.context = context;
    }

    public List<Movie> getListMovie() {
        return listMovie;
    }

    public void setListMovie(List<Movie> listMovie) {
        this.listMovie.clear();
        this.listMovie = listMovie;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterMovie.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new CategoryViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMovie.CategoryViewHolder holder, final int i) {
        final Movie m = getListMovie().get(i);
        holder.txt_movie_name.setText(getListMovie().get(i).getOriginalTitle());
        holder.txt_movie_overview.setText(getListMovie().get(i).getOverview());
        final String poster = "https://image.tmdb.org/t/p/w154" + getListMovie().get(i).getPosterPath();
        Glide.with(context)
                .load(poster)
                .into(holder.img_movie_poster);

        holder.linear_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(CONTENT_URI_MOVIE + "/"+getListMovie().get(i).getId());

                Intent i = new Intent(context, DetailActivity.class);

                i.setData(uri);
                i.putExtra(EXTRA_MOVIES, m);
                i.putExtra("code", TYPE_MOVIE_INTENT);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return getListMovie().size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView txt_movie_name, txt_movie_overview;
        ImageView img_movie_poster;
        LinearLayout linear_movie;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_movie_name = itemView.findViewById(R.id.txt_movie_name);
            txt_movie_overview = itemView.findViewById(R.id.txt_movie_overview);
            img_movie_poster = itemView.findViewById(R.id.img_movie_poster);
            linear_movie = itemView.findViewById(R.id.linear_movie);
        }
    }
}
