package com.yaroslav.chucknorristest.ui.jokes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class JokesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public JokesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}