package com.yaroslav.chucknorristest.ui.web;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.yaroslav.chucknorristest.R;

public class WebFragment extends Fragment /*implements OnBackPressed*/ {

    private WebViewModel webViewModel;
    private WebView browser;
    private CustomWebViewClient customWebViewClient;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        webViewModel = ViewModelProviders.of(this).get(WebViewModel.class);
        View root = inflater.inflate(R.layout.fragment_web, container, false);
        browser = root.findViewById(R.id.webview);

        customWebViewClient = new CustomWebViewClient();
        browser.setWebViewClient(customWebViewClient);
        browser.getSettings().setLoadWithOverviewMode(true);
        browser.getSettings().setUseWideViewPort(true);
        browser.getSettings().setBuiltInZoomControls(true);
        browser.getSettings().setDisplayZoomControls(false);
        browser.getSettings().setJavaScriptEnabled(true);

        webViewModel.getURL().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String url) {
                browser.loadUrl(url);
            }
        });

        return root;
    }

    /*
    @Override
    public void onBackPressed() {
        if (myWebView.copyBackForwardList().getCurrentIndex() > 0) {
            myWebView.goBack();
        } else {
            getActivity().onBackPressed();
        }
        //if (myWebView.canGoBack()) {
        //    myWebView.goBack();
        //} else {
        //    getActivity().onBackPressed();
        //}
    }
    */
}