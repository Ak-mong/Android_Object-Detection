package org.tensorflow.lite.examples.Main;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.tensorflow.lite.examples.Common.BaseActivity;
import org.tensorflow.lite.examples.Dialog.DisconnectHandler;


import org.tensorflow.lite.examples.R;
import org.tensorflow.lite.examples.detection.CertificationFragment;
import org.tensorflow.lite.examples.detection.GpsTracker;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;


public class MainActivity extends BaseActivity {
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    BottomNavigationView bottomNavigationView;
    private long backKeyPressedTime = 0;
    private String bottomMenu = "home";
    Toast toast;
//    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
//            result -> {
//                if (result.getResultCode() == Activity.RESULT_OK) {
//                    Intent data = result.getData();
//
//                    String qrCode = data.getStringExtra("qrCode");
//
//                    Bundle bundle = new Bundle();
//                    Fragment mapPage = new Fragment3();
//                    bundle.putString("qrCode",qrCode);
//                    loadFragment(mapPage);
//                    bottomNavigationView.getMenu().findItem(R.id.item3).setChecked(true);
//                } else {
//
//                }
//            });

    public GpsTracker gpsTracker;
    public static Context context_certi;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_forWooSung);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavi);

        gpsTracker = new GpsTracker(this);
        Log.d("initiate","onCreate " + gpsTracker.getLatitude() + " " + gpsTracker.getLongitude());

        context_certi = this;

        int d_flag = getIntent().getIntExtra("d_flag", -1);

        AndroidBridge androidBridge = new AndroidBridge(this);

        DisconnectHandler dh = new DisconnectHandler(this, this);
        dh.netWorkChecking(this, getSupportFragmentManager());

        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            if (i == 1) {
                final View iconView = menuView.getChildAt(1).findViewById(com.google.android.material.R.id.navigation_bar_item_icon_view);
                final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
                final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 27, displayMetrics);
                layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 27, displayMetrics);
                iconView.setLayoutParams(layoutParams);
            }
        }
        if(d_flag == -1) {
            loadFragment(new Fragment1());
        }
        else {
            if(d_flag == 1)
                Toast.makeText(this, "인증이 완료되었습니다.", Toast.LENGTH_SHORT).show();
            loadFragment(new CertificationFragment());
            bottomNavigationView.getMenu().findItem(R.id.item4).setChecked(true);
        }

        bottomNavigationView.setOnItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                //item을 클릭시 id값을 가져와 FrameLayout에 fragment.xml띄우기
                case R.id.item1:
                    loadFragment(new Fragment1());
                    bottomMenu = "home";
                    break;
                case R.id.item2:
                    loadFragment(new Fragment2());
                    bottomMenu = "menu1";
                    break;
                case R.id.item3:
                    loadFragment(new Fragment3());
                    bottomMenu = "menu2";
                    break;
                case R.id.item4:
                    loadFragment(new CertificationFragment());
                    bottomMenu = "menu3";
                    break;
            }
            return true;
        });
    }

    public void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, fragment).commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //startLocationService();
            } else {
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if ("home".equals(bottomMenu)) {
            if (System.currentTimeMillis() > backKeyPressedTime + 1000) {
                backKeyPressedTime = System.currentTimeMillis();
                toast = Toast.makeText(this, "뒤로 가기 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
                toast.show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 500);
                return;
            } else if (System.currentTimeMillis() <= backKeyPressedTime + 1000) {
                finishAffinity();
            }
        } else {
            bottomMenu = "home";
            loadFragment(new Fragment1());
            bottomNavigationView.getMenu().findItem(R.id.item1).setChecked(true);
        }
    }

    public void QR_Btn() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(QR_View.class);
        integrator.initiateScan();
    }

    public GpsTracker getGpsTracker() {
        return gpsTracker;
    }

}
