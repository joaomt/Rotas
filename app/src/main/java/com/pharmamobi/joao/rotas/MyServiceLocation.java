package com.pharmamobi.joao.rotas;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.test.espresso.core.deps.guava.eventbus.EventBus;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.pharmamobi.joao.rotas.Domain.WebApiUrl;
import com.pharmamobi.joao.rotas.Interfaces.MotoBoyApi;
import com.pharmamobi.joao.rotas.model.LocationLatLng;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.maps.model.LatLng;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by joao on 20/11/2017.
 */

public class MyServiceLocation extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    private static final String TAG = "Teste Service";
    private static final String TAG_p = "METODO POST";
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private WebApiUrl webApi = new WebApiUrl();
    private String URL_BASE = webApi.GetUrlBase();
    private LatLng Location_atual;
    private static int tempo_animacao=3000;
    private Boolean ative_send = true;
    private int cont = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        buildGoogleApiClient();
        Log.d(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        if (!mGoogleApiClient.isConnected())
            mGoogleApiClient.connect();
        return START_STICKY;
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected(" + bundle + ")");

        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location != null) {
            Log.d(TAG, "latidude: " + location.getLatitude());
            Log.d(TAG, "longitude: " + location.getLongitude());
        }
        startLocationUpdate();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "onConnectionSuspended(" + i + ")");
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "latitude " + location.getLatitude());
        Log.d(TAG, "longitude " + location.getLongitude());
        Location_atual = (new LatLng(location.getLatitude(), location.getLongitude()));
        if(ative_send) {
            ative_send=false;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    PostLocation(Location_atual);
                }
            }, tempo_animacao);
        }
    }

    private void PostLocation(LatLng mlocation){
        Date curDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String DateToStr = format.format(curDate);
        Log.d(TAG_p, "Date simpleDateFormat"+DateToStr);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE + "Motoboy/LocationMotoBoy/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MotoBoyApi motoboyResource = retrofit.create(MotoBoyApi.class);
        LocationLatLng location = new LocationLatLng(
                1,
                mlocation.lat,
                mlocation.lng,
                DateToStr
        );

        Call<LocationLatLng> call = motoboyResource.postLocation(location);
        call.enqueue(new Callback<LocationLatLng>() {
            @Override
            public void onResponse(Call<LocationLatLng> call, Response<LocationLatLng> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG_p, "response code:" + String.valueOf(response.code()));
                    Log.d(TAG_p, "response raw:" + String.valueOf(response.raw()));
                    Log.d(TAG_p, "response raw message:" + response.raw().message());
                    ative_send = true;
                }
            }

            @Override
            public void onFailure(Call<LocationLatLng> call, Throwable t) {
                Log.d(TAG_p, "onFaiure Retrofit", t);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed(" + connectionResult + ")");
    }

    private void initLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(2000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void startLocationUpdate() {
        initLocationRequest();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    private void stopLocationUpdate() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

    }

    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
    }
}


























