package org.tensorflow.lite.examples.detection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import org.tensorflow.lite.examples.Main.MainActivity;
import org.tensorflow.lite.examples.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {

    public static final int REQUEST_CODE = 100;

    // adapter에 들어갈 list(CertificationFragment 내의 리스트와 연결)
    public ArrayList<Data> listData = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listData.size();
    }

    void addItem(Data data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textView;
        private TextView certView;
        private ImageView imageView;
        private Data data;

        ItemViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textView);
            certView = itemView.findViewById(R.id.certView);
            imageView = itemView.findViewById(R.id.imageView);
        }

        void onBind(Data data) {
            this.data = data;

            textView.setText(data.getTitle());
            certView.setText(data.getCertification());
            imageView.setImageResource(data.getResId());

            itemView.setOnClickListener(this);
            textView.setOnClickListener(this);
            certView.setOnClickListener(this);
            imageView.setOnClickListener(this);
        }

        // 리스트에서 인증대상 클릭시 동작
        @Override
        public void onClick(View view) {
            // GPS를 이용해 현재 위치정보 받아옴 (lat2, lon2)
            GpsTracker gpsTracker = ((MainActivity)MainActivity.context_certi).getGpsTracker();
            double lat2 = gpsTracker.getLatitude();
            double lon2 = gpsTracker.getLongitude();

            // 클릭한 데이터의 인증여부 체크
            if (data.getCertification() == "인증 완료")
                Toast.makeText(context, "이미 인증 완료된 스팟입니다", Toast.LENGTH_SHORT).show();
            else {
                // 인증대상의 위치정보 받아옴 (lat1, lon1)
                if (gpsTracker != null) {
                    Log.d("test present GPS","경도는 " + lat2 + " ,위도는 " + lon2);
                    double lat1 = data.getLat();
                    double lon1 = data.getLon();

                    // 현재 위치정보와 인증대상의 위치정보 비교(거리계산) ->
                    // 거리 100m 이내일시 그 인증대상을 타겟으로 설정후 ->
                    // DetectorActivity 호출 (타겟의 인덱스정보 (getAdapterPosition()) 를 넘겨줌)
                    if (Integer.parseInt(getDistance(lat1, lon1, lat2, lon2)) < 100) {
                        Intent intent = new Intent(context, DetectorActivity.class);
                        intent.putExtra("targetI", getAdapterPosition());
                        gpsTracker.stopUsingGPS();
                        context.startActivity(intent);
//                        ((MainActivity)MainActivity.context_certi).startActivityForResult(intent,REQUEST_CODE);
                    }
                    else
                        Toast.makeText(context, "올바른 장소에서 인증을 시도해주세요", Toast.LENGTH_SHORT).show();
                }
                //lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, gpsLocationListener);
                //lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, gpsLocationListener);
            }
            //gpsTracker.locationManager.removeUpdates(gpsTracker);
        }

        // 두 위치정보간의 거리 계산하는 함수
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
}
