package org.tensorflow.lite.examples.Dialog;

import android.app.Activity;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import org.tensorflow.lite.examples.databinding.ToastCustomBinding;


public class Toast_Custom {

    ToastCustomBinding binding;
    LayoutInflater inflaters;
    public static Activity activity;
    Toast customToast;
    CountDownTimer cdt;
    public void createToast(Activity activitys, String msg){

        this.activity = activitys;

        inflaters = LayoutInflater.from(activity);
        binding = ToastCustomBinding.inflate(activity.getLayoutInflater());
        binding.tvSample.setText(msg);
        View view = binding.getRoot();

        customToast = Toast.makeText(activity.getApplicationContext(),msg,Toast.LENGTH_SHORT);
//      customToast.setGravity(Gravity.BOTTOM,convertDP(20),convertDP(20));
        customToast.setView(view);
        customToast.show();

    }

    public void cancelToast(){
        customToast.cancel();
        cdt.cancel();
    }

    public static int convertDP(int dp){
        float density = activity.getApplicationContext().getResources().getDisplayMetrics().density;

        return Math.round((float) dp * density);
    }

    public void ToastTimer(){
        cdt = new CountDownTimer(350000,3500){
            @Override
            public void onTick(long l) {
                customToast.show();
            }

            @Override
            public void onFinish() {
                customToast.show();
            }
        }.start();
    }
}
