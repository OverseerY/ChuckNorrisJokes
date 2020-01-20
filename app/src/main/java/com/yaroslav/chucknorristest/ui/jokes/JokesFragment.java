package com.yaroslav.chucknorristest.ui.jokes;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yaroslav.chucknorristest.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class JokesFragment extends Fragment implements JokesDialogFragment.JokesCountListener {
    private static final String JOKES_URL = "http://api.icndb.com/jokes/random/";
    private static final String TOTAL_COUNT_URL = "http://api.icndb.com/jokes/count";

    private List<String> listOfJokes;
    private JokeAdapter jokeAdapter;
    private RecyclerView.ItemDecoration decoration;
    private RecyclerView jokesRecyclerView;
    private FloatingActionButton fabRefreshJokes;
    private ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_jokes, container, false);

        jokesRecyclerView = root.findViewById(R.id.recycler_view_jokes);
        fabRefreshJokes = root.findViewById(R.id.fab_refresh);
        progressBar = root.findViewById(R.id.jokes_progressbar);

        decoration = new DividerItemDecoration(root.getContext(), DividerItemDecoration.VERTICAL);
        jokesRecyclerView.addItemDecoration(decoration);

        listOfJokes = new ArrayList<>();

        fabRefreshJokes.setOnClickListener(v -> {
            JokesDialogFragment jokesDialogFragment = new JokesDialogFragment();
            jokesDialogFragment.setTargetFragment(this, 0);
            jokesDialogFragment.show(getFragmentManager(), "jokes_count");
        });

        jokesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    fabRefreshJokes.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && fabRefreshJokes.isShown()) {
                    fabRefreshJokes.hide();
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        return root;
    }

    @Override
    public void onJokesReload(int value) {
        progressBar.setVisibility(View.VISIBLE);
        int totalJokes = getTotalCountOfJokes();
        if (totalJokes != 0 && value > totalJokes) {
            value = totalJokes;
        }

        String _url = JOKES_URL + value;
        try {
            String jokes = new DownloadFromServerAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, _url).get();
            if (jokes != null && !jokes.isEmpty()) {
                listOfJokes = new ParseJsonAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, jokes).get();
                if (listOfJokes != null && !listOfJokes.isEmpty()) {
                    displayJokes(listOfJokes);
                }
            }
        } catch (ExecutionException | InterruptedException e) {
            progressBar.setVisibility(View.INVISIBLE);
            Log.e("JOKES_FRAGMENT", "Exception: " + "(" + e.getClass() + "): " + e.getMessage());
        }
    }

    private int getTotalCountOfJokes() {
        int amount = 0;
        try {
            String count = new DownloadFromServerAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, TOTAL_COUNT_URL).get();
            if (count != null && !count.isEmpty()) {
                JSONObject responseJson = new JSONObject(count);
                amount = responseJson.getInt("value");
            }
        } catch (ExecutionException | InterruptedException | JSONException e) {
            Log.e("JOKES_FRAGMENT", "JSON Exception: " + "(" + e.getClass() + "): " + e.getMessage());
        }
        return amount;
    }

    private void displayJokes(List<String> list) {
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            jokeAdapter = new JokeAdapter(list);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            jokesRecyclerView.setLayoutManager(layoutManager);
            jokesRecyclerView.setItemAnimator(new DefaultItemAnimator());
            jokesRecyclerView.setAdapter(jokeAdapter);
            progressBar.setVisibility(View.INVISIBLE);
        }, 200);
    }
}

























