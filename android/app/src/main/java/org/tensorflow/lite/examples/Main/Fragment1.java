package org.tensorflow.lite.examples.Main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

import org.tensorflow.lite.examples.Common.PageInfo;
import org.tensorflow.lite.examples.R;

public class Fragment1 extends Fragment {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1, container, false);

        final WebView webView = view.findViewById(R.id.webview1);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);

        // 세로 scroll 제거
        webView.setHorizontalScrollBarEnabled(false);
        // 가로 scroll 제거
        webView.setVerticalScrollBarEnabled(false);

        webView.loadUrl(PageInfo.INDEX_PAGE);





        return view;
    }
}