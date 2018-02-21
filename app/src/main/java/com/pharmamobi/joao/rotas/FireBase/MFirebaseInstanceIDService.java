package com.pharmamobi.joao.rotas.FireBase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by joao on 02/11/2017.
 */

public class MFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "notificação";
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG,"Token = "+token);

        Log.d(TAG,"FirebaseInstanceIdService");
    }
}
