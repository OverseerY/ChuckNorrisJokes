package com.yaroslav.chucknorristest.ui.web;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.yaroslav.chucknorristest.R;

public class WebFragment extends Fragment /*implements OnBackPressed*/ {

    private WebViewModel webViewModel;
    private WebView browser;
    private ProgressBar progressBar;
    private CustomWebViewClient customWebViewClient;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        webViewModel = ViewModelProviders.of(this).get(WebViewModel.class);
        View root = inflater.inflate(R.layout.fragment_web, container, false);

        browser = root.findViewById(R.id.webview);

        progressBar = root.findViewById(R.id.webview_progressbar);

        customWebViewClient = new CustomWebViewClient(progressBar);
        browser.setWebViewClient(customWebViewClient);
        browser.getSettings().setLoadWithOverviewMode(true);
        browser.getSettings().setUseWideViewPort(true);
        browser.getSettings().setBuiltInZoomControls(true);
        browser.getSettings().setDisplayZoomControls(false);
        browser.getSettings().setJavaScriptEnabled(true);

        if (savedInstanceState != null) {
            browser.restoreState(savedInstanceState);
        } else {
            webViewModel.getURL().observe(this, url -> browser.loadUrl(url));
        }

        return root;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        browser.saveState(outState);
    }
}