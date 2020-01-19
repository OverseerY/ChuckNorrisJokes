package com.yaroslav.chucknorristest.ui.jokes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yaroslav.chucknorristest.R;

public class JokesFragment extends Fragment {

    private JokesViewModel jokesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        jokesViewModel = ViewModelProviders.of(this).get(JokesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_jokes, container, false);

        RecyclerView jokesList = root.findViewById(R.id.recycler_view_jokes);
        FloatingActionButton fabRefreshJokes = root.findViewById(R.id.fab_refresh);

        fabRefreshJokes.setOnClickListener(v -> {
            JokesDialogFragment jokesDialogFragment = new JokesDialogFragment();
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
}