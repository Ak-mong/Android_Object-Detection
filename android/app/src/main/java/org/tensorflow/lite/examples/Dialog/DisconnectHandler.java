package org.tensorflow.lite.examples.Dialog;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import org.tensorflow.lite.examples.R;

public class DisconnectHandler extends FragmentActivity implements Runnable {

    Activity activity;
    Context context;
    boolean cancelChk = false;
    Toast_Custom tc;
    Toast_Custom tc2;
    boolean chk = false;
    boolean test = false;

    public DisconnectHandler(Activity _activity, Context _context) {
        this.activity = _activity;
        this.context = _context;
    }

    @Override
    public void run() {
        isNetworkConnected(context);

        tc = new Toast_Custom();
        if (!Variables.isNetwork) {
            cancelChk = true;
            tc.createToast(activity, "네트워크 연결 상태를 확인해주세요");
        } else {
            if (cancelChk) tc.cancelToast();
        }
    }

    public void isNetworkConnected(Context context) {
        try {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkRequest.Builder builder = new NetworkRequest.Builder();

            manager.registerNetworkCallback(builder.build(), new ConnectivityManager.NetworkCallback() {
                @Override
                public void onAvailable(@NonNull Network network) {
                    Variables.isNetwork = true;
                }

                @Override
                public void onLost(@NonNull Network network) {
                    Variables.isNetwork = false;
                }
            });
            Variables.isNetwork = false;
        } catch (Exception e) {
            Variables.isNetwork = false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void netWorkChecking(Context context, FragmentManager fragmentManager) {
        NetworkRequest.Builder builder = null;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        builder = new NetworkRequest.Builder();
        BottomSheetDialogCustom bsd = new BottomSheetDialogCustom();
        FragmentManager fm = fragmentManager;

        manager.registerNetworkCallback(builder.build(), new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                // 네트워크를 사용할 준비가 되었을 때
                try {
                    if (chk) bsd.dismissAllowingStateLoss();
                } catch (IllegalStateException e) {
                    Log.e("TAG", e.toString());
                }
            }

            @Override
            public void onLost(@NonNull Network network) {
                // 네트워크가 끊겼을 때
                if (!fm.isDestroyed()) {
//                    FragmentTransaction ft = fm.beginTransaction();
//                    ft.addToBackStack(null);
                    fm.beginTransaction().add(bsd, "networkDisconnected").commitAllowingStateLoss();
//                    bsd.show(fm, "networkDisconnected");
                    bsd.setCancelable(false);
                    bsd.setStyle(DialogFragment.STYLE_NORMAL, R.style.bottomSheetTheme);
                    chk = true;
                }
            }
        });
    }

    public static class Variables {
        public static boolean isNetwork = false;
    }
}
