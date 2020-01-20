package com.yaroslav.chucknorristest.jokes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yaroslav.chucknorristest.R;

import java.util.ArrayList;

public class JokeAdapter extends RecyclerView.Adapter<JokeAdapter.ViewHolder> {
    private ArrayList<String> jokes;
    IJokeListener IJokeListener;

    public JokeAdapter(ArrayList<String> jokes) {
        this.jokes = jokes;
    }

    public void setOnItemClickListener(IJokeListener IJokeListener) {
        this.IJokeListener = IJokeListener;
    }

    @NonNull
    @Override
    public JokeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.joke_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String joke = jokes.get(position);
        holder.jokeTitle.setText(joke);
        holder.jokeTitle.setOnClickListener(v -> {
            IJokeListener.onItemClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return jokes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView jokeTitle;

        ViewHolder(View view) {
            super(view);
            jokeTitle = view.findViewById(R.id.joke_title);
        }
    }
}
