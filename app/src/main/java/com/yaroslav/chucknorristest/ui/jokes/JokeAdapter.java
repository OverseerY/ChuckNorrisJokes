package com.yaroslav.chucknorristest.ui.jokes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yaroslav.chucknorristest.R;

import java.util.List;

public class JokeAdapter extends RecyclerView.Adapter<JokeAdapter.ViewHolder> {
    private List<String> jokes;

    public JokeAdapter(List<String> jokes) {
        this.jokes = jokes;
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
