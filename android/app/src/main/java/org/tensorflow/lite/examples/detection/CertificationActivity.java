package org.tensorflow.lite.examples.detection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import java.util.Arrays;
import java.util.List;

public class CertificationActivity extends AppCompatActivity {

    private Button btn_move;
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);

        getData();
    }


    private void getData() {
        // 임의의 데이터입니다.
        List<String> listTitle = Arrays.asList("북문", "정문", "석탑", "일청담", "test", "test", "test", "test", "test");
        List<String> listCertification = Arrays.asList(
                "인증 미완료",
                "인증 미완료",
                "인증 미완료",
                "인증 미완료",
                "인증 미완료",
                "인증 미완료",
                "인증 미완료",
                "인증 미완료",
                "인증 미완료"
        );
        List<Integer> listResId = Arrays.asList(
                R.drawable.north,
                R.drawable.front,
                R.drawable.top,
                R.drawable.onebluedam,
                R.drawable.caret,
                R.drawable.caret,
                R.drawable.caret,
                R.drawable.caret,
                R.drawable.caret
        );
        for (int i = 0; i < listTitle.size(); i++) {
            // 각 List의 값들을 data 객체에 set 해줍니다.
            Data data = new Data();
            data.setTitle(listTitle.get(i));
            data.setCertification(listCertification.get(i));
            data.setResId(listResId.get(i));

            // 각 값이 들어간 data를 adapter에 추가합니다.
            adapter.addItem(data);
        }

        // adapter의 값이 변경되었다는 것을 알려줍니다.
        adapter.notifyDataSetChanged();
    }
}

