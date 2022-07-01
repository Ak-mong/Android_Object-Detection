package com.flash21.For_wooSung.Main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

import com.flash21.For_wooSung.Common.PageInfo;
import com.flash21.For_wooSung.R;


public class Fragment3 extends Fragment {

    String ScanResult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_1, container, false);

        final WebView webView = view.findViewById(R.id.webview1);

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                super.onGeolocationPermissionsShowPrompt(origin, callback);
                callback.invoke(origin, true, false);
            }
        });


        if(getArguments() != null){
            ScanResult = getArguments().getString("ScanResult",null);
        }

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if(ScanResult != null){
                    StringBuilder script = new StringBuilder();
                    script.append("javascript:output('" + ScanResult + "')");
                    webView.evaluateJavascript(String.valueOf(script), null);
                    webView.reload();
                    ScanResult = null;
                }
            }
        });

        webView.loadUrl(PageInfo.PAGE2);

//        Toast.makeText(getContext(), ScanResult, Toast.LENGTH_LONG).show();

        return view;

    }
}