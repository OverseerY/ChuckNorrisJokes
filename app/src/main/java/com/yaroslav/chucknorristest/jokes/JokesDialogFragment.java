package com.yaroslav.chucknorristest.jokes;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yaroslav.chucknorristest.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class JokesDialogFragment extends DialogFragment {
    public interface JokesCountListener {
        void onJokesReload(int value);
    }

    private JokesCountListener listener;

    private EditText jokesCount;
    private Button reloadJokes;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            listener = (JokesCountListener) getTargetFragment();
        } catch (ClassCastException e) {
            Log.e("JOKES_DIALOG_FRAGMENT", e.getMessage());
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_jokes, container, false);

        jokesCount = view.findViewById(R.id.jokes_count_field);
        reloadJokes = view.findViewById(R.id.jokes_reload_button);

        reloadJokes.setOnClickListener(v -> {
            String value = jokesCount.getText().toString();
            if (!value.isEmpty()) {
                listener.onJokesReload(Integer.parseInt(value));
                this.dismiss();
            } else {
                Toast.makeText(getContext(), "You should input number to get jokes", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
