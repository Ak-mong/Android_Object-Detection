package com.flash21.For_wooSung.Splash;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.flash21.For_wooSung.Main.MainActivity;
import com.flash21.For_wooSung.R;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.util.List;

public class SplashAct extends Activity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        WebView webView;
        webView = findViewById(R.id.splash_WebView);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.loadUrl("https://dico-icerink.flash21.com/splash");

        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setRationaleMessage("기기 접근 권한이 필요합니다.")
                .setDeniedMessage("기기 접근권한을 허용하지 않아 종료됩니다.\n[설정] > " + getResources().getString(R.string.app_name) + " > [권한] 에서 권한을 허용할 수 있습니다.")
                .setPermissions(
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .check();

    }

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Intent intent = new Intent(SplashAct.this, MainActivity.class);
            startActivity(intent);
        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {
            Toast.makeText(SplashAct.this, deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            finish();
        }
    };
}
