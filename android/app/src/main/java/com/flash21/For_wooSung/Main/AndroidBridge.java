package com.flash21.For_wooSung.Main;

import android.os.Handler;
import android.webkit.JavascriptInterface;

public class AndroidBridge {
    private final Handler handler = new Handler();
    private MainActivity mContext;

    public AndroidBridge(MainActivity _mContext) {
        this.mContext = _mContext;
    }

    @JavascriptInterface
    public void QR_Btn() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                mContext.QR_Btn();
            }
        });
    }
}
