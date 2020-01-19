package com.yaroslav.chucknorristest.ui.web;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WebViewModel extends ViewModel {
    private MutableLiveData<String> mURL;

    public WebViewModel() {
        mURL = new MutableLiveData<>();
        mURL.setValue("http://www.icndb.com/api/");
    }

    public LiveData<String> getURL() {
        return mURL;
    }
}