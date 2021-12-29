package com.cjh.finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;

import java.util.ArrayList;


public class blackbox extends Fragment {

    private WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_blackbox, container, false);

        webView = v.findViewById(R.id.webview);

        String url = "http://172.30.1.14:8087/app_server_test/videoCont";

        webView.setWebViewClient(new WebViewClient()); // 클라이언트 생성
        webView.getSettings().setJavaScriptEnabled(true); // JS 사용 활성화

        webView.loadUrl(url);

        return v;
    }
}