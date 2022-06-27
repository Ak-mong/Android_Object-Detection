package org.tensorflow.lite.examples.detection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class CertificationActivity extends AppCompatActivity {

    static List<String> listTitle = Arrays.asList("정문", "북문", "석탑", "일청담", "test", "test", "test", "test", "test");
    static List<String> listCertification = Arrays.asList(
            "인증 미완료",
            "인증 미완료",
            "인증 완료",
            "인증 미완료",
            "인증 미완료",
            "인증 미완료",
            "인증 미완료",
            "인증 미완료",
            "인증 미완료"
    );
    static List<Integer> listResId = Arrays.asList(
            R.drawable.front,
            R.drawable.north,
            R.drawable.top,
            R.drawable.onebluedam,
            R.drawable.caret,
            R.drawable.caret,
            R.drawable.caret,
            R.drawable.caret,
            R.drawable.caret
    );

    static List<Double> listLat = Arrays.asList(
            35.835292,//35.88517,
            35.835265,//35.8919,
            35.835265,
            35.835265,
            35.3245,
            10.324,
            10.3244,
            10.32442,
            1.2345

    );
    static List<Double> listLong = Arrays.asList(
            128.682395,//128.61447
            128.682495,//128.610129,
            128.682495,
            128.682495,
            125.324,
            100.234,
            129.324,
            130.59,
            150.2345
    );

    private Button btn_move;
    public static Context context_certi;
    public RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        int d_flag = getIntent().getIntExtra("d_flag", -1);

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if(permissionCheck == PackageManager.PERMISSION_DENIED){ //위치 권한 확인
            //위치 권한 요청
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }

        if(d_flag == 1){
            Toast.makeText(getApplicationContext(), "인증이 완료되었습니다.", Toast.LENGTH_SHORT).show();
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);

        getData();

        context_certi = this;
    }

    private void getData() {
        // 임의의 데이터입니다.

        for (int i = 0; i < listTitle.size(); i++) {
            // 각 List의 값들을 data 객체에 set 해줍니다.
            Data data = new Data();
            data.setTitle(listTitle.get(i));
            data.setCertification(listCertification.get(i));
            data.setResId(listResId.get(i));
            data.setLat(listLat.get(i));
            data.setLon(listLong.get(i));

            // 각 값이 들어간 data를 adapter에 추가합니다.
            adapter.addItem(data);
        }

        // adapter의 값이 변경되었다는 것을 알려줍니다.
        adapter.notifyDataSetChanged();
    }

    public void changeCert(int i){
        listCertification.set(i, "인증 완료");
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==2) {
            if(resultCode==RESULT_OK) {
                Toast.makeText(CertificationActivity.this, "result ok!", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(CertificationActivity.this, "result cancle!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String getTitle(int i) { return listTitle.get(i); }

}


