package org.tensorflow.lite.examples.detection;


import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;
import org.tensorflow.lite.examples.R;

public class CertificationFragment extends Fragment {

    // 인증대상의 이름 리스트
    static List<String> listTitle = Arrays.asList("정문", "안내문", "북문", "테크노문", "석탑", "백호관", "당근", "test", "test");

    // 인증대상의 인증여부 리스트
    static List<String> listCertification = Arrays.asList(
            "인증 미완료",
            "인증 미완료",
            "인증 미완료",
            "인증 미완료",
            "인증 미완료",
            "인증 미완료",
            "인증 완료",
            "인증 미완료",
            "인증 미완료"
    );

    // 인증대상의 사진 리스트
    static List<Integer> listResId = Arrays.asList(
            R.drawable.front,
            R.drawable.nine,
            R.drawable.north,
            R.drawable.tech,
            R.drawable.tower,
            R.drawable.west,
            R.drawable.caret,
            R.drawable.caret,
            R.drawable.caret
    );

    // 인증대상의 위도정보 리스트
    static List<Double> listLat = Arrays.asList(
            35.88517,
            35.886786,
            35.8919,
            35.892548,
            35.889417,
            35.888488,
            35.835265,
            35.835265,
            1.2345
    );

    // 인증대상의 경도정보 리스트
    static List<Double> listLong = Arrays.asList(
            128.61447,
            128.608874,
            128.610129,
            128.614867,
            128.612461,
            128.604296,
            128.682495,
            128.682495,
            150.2345
    );

    // 인증대상의 상세내용
    static List<String> listInformation = Arrays.asList(
            "(랜드마크 설명)\n" +
                    "운영진만 편집 가능한 문서입니다. \n" +
                    "편집 분야로서의 나무위키에 대한 문서만 분류합니다. 따라서 나무위키 이름공간 문서는 더 이상 이곳에 분류하지 않습니다.\n" +
                    "다른 뜻 아이콘  나무위키와 관련된 문서들을 분류하는 곳입니다. "
            ,"내용내용내용내용내용내용내용내용"
            ,"내용내용내용내용내용내용내용내용"
            ,"내용내용내용내용내용내용내용내용"
            ,"내용내용내용내용내용내용내용내용"
            ,"내용내용내용내용내용내용내용내용"
            ,"내용내용내용내용내용내용내용내용"
            ,"내용내용내용내용내용내용내용내용"
            ,"내용내용내용내용내용내용내용내용"
            );

    // RecyclerView와 연결할 어댑터
    public RecyclerAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_certification, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        RecyclerView recyclerView = getView().findViewById(R.id.recyclerView);

        int checked_target = getActivity().getIntent().getIntExtra("checked_target", -1);

        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);

        if(permissionCheck == PackageManager.PERMISSION_DENIED){ //위치 권한 확인
            //위치 권한 요청
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }

        if(checked_target != -1){
            listCertification.set(checked_target, "인증 완료");
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);

        getData();
    }

    private void getData() {
        for (int i = 0; i < listTitle.size(); i++) {
            // 각 List의 값들을 data 객체에 set 해줍니다.
            Data data = new Data();
            data.setTitle(listTitle.get(i));
            data.setCertification(listCertification.get(i));
            data.setResId(listResId.get(i));
            data.setLat(listLat.get(i));
            data.setLon(listLong.get(i));
            data.setInformation(listInformation.get(i));

            // 각 값이 들어간 data를 adapter에 추가합니다.
            adapter.addItem(data);
        }
        // adapter의 값이 변경되었다는 것을 알려줍니다.
        adapter.notifyDataSetChanged();
    }


//    public void onActivityResult (int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode==2) {
//            if(resultCode==RESULT_OK) {
//                Toast.makeText(getActivity(), "result ok!", Toast.LENGTH_SHORT).show();
//            }
//            else {
//                Toast.makeText(getActivity(), "result cancle!", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
}


