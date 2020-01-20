package com.yaroslav.chucknorristest.ui.jokes;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yaroslav.chucknorristest.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class JokesFragment extends Fragment implements JokesDialogFragment.JokesCountListener {
    private static final String JOKES_URL = "http://api.icndb.com/jokes/random/";
    private JokesViewModel jokesViewModel;

    private List<String> listOfJokes;
    private JokeAdapter jokeAdapter;
    private RecyclerView.ItemDecoration decoration;
    private RecyclerView jokesRecyclerView;
    private FloatingActionButton fabRefreshJokes;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        jokesViewModel = ViewModelProviders.of(this).get(JokesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_jokes, container, false);

        jokesRecyclerView = root.findViewById(R.id.recycler_view_jokes);
        fabRefreshJokes = root.findViewById(R.id.fab_refresh);
        listOfJokes = new ArrayList<>();
        decoration = new DividerItemDecoration(root.getContext(), DividerItemDecoration.VERTICAL);
        jokesRecyclerView.addItemDecoration(decoration);

        fabRefreshJokes.setOnClickListener(v -> {
            JokesDialogFragment jokesDialogFragment = new JokesDialogFragment();
            jokesDialogFragment.setTargetFragment(this, 0);
            jokesDialogFragment.show(getFragmentManager(), "jokes_count");
        });

        /*
        final TextView textView = root.findViewById(R.id.text_jokes);
        jokesViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        */
        return root;
    }

    @Override
    public void onJokesReload(int value) {
        String _url = JOKES_URL + value;
        try {
            String jokes = new DownloadFromServerAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, _url).get();
            if (jokes != null && !jokes.isEmpty()) {
                listOfJokes = new ParseJsonAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, jokes).get();
                if (listOfJokes != null && !listOfJokes.isEmpty()) {
                    Log.i("JOKES_FRAGMENT", listOfJokes.size() + ": " + listOfJokes.toString());
                    displayJokes(listOfJokes);
                }
            }
        } catch (ExecutionException | InterruptedException e) {
            Log.e("JOKES_FRAGMENT", "Exception: " + "(" + e.getClass() + "): " + e.getMessage());
        }

        //Toast.makeText(getContext(), "Count of jokes: " + value, Toast.LENGTH_SHORT).show();
    }

    private void displayJokes(List<String> list) {
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            jokeAdapter = new JokeAdapter(list);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            jokesRecyclerView.setLayoutManager(layoutManager);
            jokesRecyclerView.setItemAnimator(new DefaultItemAnimator());
            jokesRecyclerView.setAdapter(jokeAdapter);
        }, 200);
    }
}

























