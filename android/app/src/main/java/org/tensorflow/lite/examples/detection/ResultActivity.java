package org.tensorflow.lite.examples.detection;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.tensorflow.lite.examples.R;

public class ResultActivity extends AppCompatActivity {

    Button yesButton, noButton;
    TextView targetname;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        yesButton = findViewById(R.id.yesBtn);
        noButton = findViewById(R.id.noBtn);
        targetname = findViewById(R.id.target);

        int checked_target = getIntent().getIntExtra("checked_target", -1);
        String checked_target_name = getIntent().getStringExtra("checked_target_name");

        targetname.setText(checked_target_name);

        byte[] bytes = getIntent().getByteArrayExtra("BMP");
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        ImageView iv = findViewById(R.id.resultImage);
        iv.setImageBitmap(bmp);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), org.tensorflow.lite.examples.Main.MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("d_flag", 1); // 메인으로 넘겨줄 d_flag(detect flag)를 1로 설정, 카메라 인증 완료임을 의미
                intent.putExtra("checked_target", checked_target); // 인증 완료된 타겟을 메인으로 넘겨줌
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), org.tensorflow.lite.examples.Main.MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("d_flag", 2); // 메인으로 넘겨줄 d_flag를 2로 설정, 인증 완료가 아닌 뒤로가기로 인해 액티비티가 전환됨을 의미
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }



}
