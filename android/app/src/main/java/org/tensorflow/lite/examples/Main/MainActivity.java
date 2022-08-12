package org.tensorflow.lite.examples.Main;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
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


import org.tensorflow.lite.examples.R;
import org.tensorflow.lite.examples.detection.CertificationFragment;
import org.tensorflow.lite.examples.detection.DetectorActivity;
import org.tensorflow.lite.examples.detection.GpsTracker;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;


public class MainActivity extends BaseActivity {
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    BottomNavigationView bottomNavigationView;
    private long backKeyPressedTime = 0;
    private String bottomMenu = "home";
    private Integer DISTANCE_ERROR_RANGE = 50; // GPS 계산 시 인증 가능 범위, 단위 : meter (안드로이드 gps는 기본 20m 오차)
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

    public GpsTracker gpsTracker; // GPS를 위한 class(GpsTracker.java)
    public static Context context_certi; // 메인 액티비티의 Context

    public Integer target;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_forWooSung);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavi);

        gpsTracker = new GpsTracker(this);
        Log.d("initiate","onCreate " + gpsTracker.getLatitude() + " " + gpsTracker.getLongitude());

        context_certi = this;

        int d_flag = getIntent().getIntExtra("d_flag", -1);
        // detect flag, -1=디폴트, 카메라 액티비티와 무관함, 1=카메라 액티비티에서 인식 완료 후 복귀, 2=카메라 액티비티에서 뒤로가기 키로 복귀(인식 미완료)

        AndroidBridge androidBridge = new AndroidBridge(this);

//        DisconnectHandler dh = new DisconnectHandler(this, this);
//        dh.netWorkChecking(this, getSupportFragmentManager());

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
            // 앱 실행 시 홈화면(Fragment1)
            loadFragment(new Fragment1());
        }
        else {
            // 카메라 액티비티에서 메인 액티비티로 복귀 시(인증 완료 or 뒤로가기)
            if(d_flag == 1) {
                // 인증 완료로 인한 복귀 + 인증메시지 발생
                Toast.makeText(this, "인증이 완료되었습니다.", Toast.LENGTH_SHORT).show();
            }
            loadFragment(new CertificationFragment());
            bottomNavigationView.getMenu().findItem(R.id.item5).setChecked(true);
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
                    camera_icon_to_certifcation();
                    break;
                case R.id.item4:
                    loadFragment(new Fragment3());
                    bottomMenu = "menu3";
                    break;
                case R.id.item5:
                    loadFragment(new CertificationFragment());
                    bottomMenu = "menu4";
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

    private void camera_icon_to_certifcation(){
        GpsTracker gpsTracker = ((org.tensorflow.lite.examples.Main.MainActivity) MainActivity.context_certi).getGpsTracker();
        double lat2 = gpsTracker.getLatitude();
        double lon2 = gpsTracker.getLongitude();
        Log.d("test current GPS","경도는 " + lat2 + " ,위도는 " + lon2);

        if (gpsTracker != null) {
            for(int i=0;i<CertificationFragment.listCertification.size();i++){
                double lat1 = CertificationFragment.listLat.get(i);
                double lon1 = CertificationFragment.listLong.get(i);

                // 현재 위치정보와 인증대상의 위치정보 비교(거리계산) ->
                // 거리 100m 이내일시 그 인증대상을 타겟으로 설정후 ->
                // DetectorActivity 호출 (타겟의 인덱스정보 (getAdapterPosition()) 를 넘겨줌)
                if (Integer.parseInt(getDistance(lat1, lon1, lat2, lon2)) < DISTANCE_ERROR_RANGE) {
                    if (CertificationFragment.listCertification.get(i) == "인증 완료") {
                        Toast.makeText(getApplicationContext(), "이미 인증 완료된 스팟입니다", Toast.LENGTH_SHORT).show();
                        loadFragment(new CertificationFragment());
                        return;
                    }
                    else{
                        Intent intent = new Intent(getApplicationContext(), DetectorActivity.class);
                        intent.putExtra("targetI", i);
                        gpsTracker.stopUsingGPS();
                        startActivity(intent);
                        return;
                    }
                }
            }
            Toast.makeText(getApplicationContext(), "올바른 장소에서 인증을 시도해주세요", Toast.LENGTH_SHORT).show();
            loadFragment(new CertificationFragment());
        }
    }

    public String getDistance(double lat1, double lng1, double lat2, double lng2) {
        double distance;
        Location locationA = new Location("point A");
        locationA.setLatitude(lat1);
        locationA.setLongitude(lng1);
        Location locationB = new Location("point B");
        locationB.setLatitude(lat2);
        locationB.setLongitude(lng2);
        distance = locationA.distanceTo(locationB);
        String num = String.format("%.0f", distance);
        return num;
    }

    final LocationListener gpsLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            // 위치 리스너는 위치정보를 전달할 때 호출되므로 onLocationChanged()메소드 안에 위지청보를 처리를 작업을 구현 해야합니다.
            String provider = location.getProvider();  // 위치정보
            double longitude = location.getLongitude(); // 위도
            double latitude = location.getLatitude(); // 경도
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        public void onProviderEnabled(String provider) {

        }

        public void onProviderDisabled(String provider) {

        }
    };

}
