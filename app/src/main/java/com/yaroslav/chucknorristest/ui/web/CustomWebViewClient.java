package com.yaroslav.chucknorristest.ui.web;

import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CustomWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return !"www.icndb.com".equals(Uri.parse(url).getHost());
        //Navigation will be working only in target site. Outer links will not work
    }
}
