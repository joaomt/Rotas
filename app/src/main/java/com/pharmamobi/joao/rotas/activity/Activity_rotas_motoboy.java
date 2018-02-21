package com.pharmamobi.joao.rotas.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.pharmamobi.joao.rotas.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.android.PolyUtil;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.TravelMode;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.pharmamobi.joao.rotas.activity.Activity_entregas_relacao_boy.listEntregasRelacao;

public class Activity_rotas_motoboy extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {
    private GoogleApiClient mGoogleApiClient;
    private GoogleMap maps_rota;
    private LocationManager locationManager;
    private static final int overview = 0;
    String origen, destino, waypoint_1, waypoint_2, waypoint_3, waypoint_4,waypoint_5;
    private String[] enderecos_entregas;
    private DirectionsResult results;
    private List<LatLng> latLngsList;
    //private ArrayList<String> enderecos_entregas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotas_motoboy);

        enderecos_entregas = new String[listEntregasRelacao.size()];
        for (int x = 0; x < listEntregasRelacao.size(); x++) {
            enderecos_entregas[x] = (String.valueOf(
                    listEntregasRelacao.get(x).getRUA() + ", " +
                            listEntregasRelacao.get(x).getNUMERO() + " - " +
                            listEntregasRelacao.get(x).getBAIRRO() + ", " +
                            listEntregasRelacao.get(x).getCIDADE() + "-" +
                            listEntregasRelacao.get(x).getESTADO()));
        }

        origen = "Rua Santa Cruz, 33 - Cambuí, Campinas-SP";
        destino = "Rua Santa Cruz, 33 - Cambuí, Campinas-SP";
        waypoint_1 = "Rua Antoniô Zancanella, 68 - Cidade Satélite Iris, Campinas-SP";
        waypoint_2 = "R. Geni Marcolino Pinto, 2-288 - Chacara Santa Leticia, Campinas-SP";
        waypoint_3 = "R. Lazaro Marchete - Swiss Park, Campinas - SP";
        waypoint_4 = "UNIP Campinas - Campus II, Av. Comendador Enzo Ferrari, 280 - Swift, Campinas - SP";
        waypoint_5 = "Rua Barão de Jaguara,1010 - Centro, Campinas-SP";

//        https://maps.googleapis.com/maps/api/directions/json?origin=Rua Santa Cruz, 33 - Cambuí, Campinas-SP
//        &waypoints=Rua Antoniô Zancanella, 68 - Cidade Satélite Iris, Campinas-SP
//        |R. Geni Marcolino Pinto, 2-288 - Chacara Santa Leticia
//        |R. Lazaro Marchete - Swiss Park, Campinas - SP
//         &destination=UNIP Campinas - Campus II, Av. Comendador Enzo Ferrari, 280 - Swift, Campinas - SP
//         &key=AIzaSyCrNmTbLj2I8tq9VUQkoQkXDJt1na61fvM


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Typeface r_light = Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");

    }

    @Override
    public void onMapReady(GoogleMap map) {
        setupGoogleMapScreenSetting(map);
        results = getDirectionDetails(origen, destino, TravelMode.DRIVING); // requisição de rota para automovel
        if (results != null) {
            addPolyline(results, map);
            positionCamera(results.routes[overview], map);
            addMarkersToMap(results, map);
        }
    }

    private DirectionsResult getDirectionDetails(String origin, String destination, TravelMode mode) { // faz a requisição pra obter o caminho completo
        DateTime now = DateTime.now();
        try {
            return DirectionsApi
                    .newRequest(getGeoContext())
                    .mode(mode)
                    .origin(origin)
                    .destination(destination)
                    .waypoints(enderecos_entregas)
                    .departureTime(now)
                    .await();
        } catch (ApiException e) {
            e.printStackTrace();
            Log.d("TAG - printStackTrace", e.toString());
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("TAG InterruptedExceptio", e.toString());
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("TAG - IOException", e.toString());
            return null;
        }
    }

    private void setupGoogleMapScreenSetting(GoogleMap map) { //define algumas opções pra quando criar o maps
        map.setBuildingsEnabled(false);
        //map.setIndoorEnabled(true);
        map.setTrafficEnabled(false);
        UiSettings uiSettings = map.getUiSettings();
        //uiSettings.setZoomControlsEnabled(true);
        uiSettings.setCompassEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setScrollGesturesEnabled(true);
        uiSettings.setZoomGesturesEnabled(true);
        uiSettings.setTiltGesturesEnabled(true);
        uiSettings.setRotateGesturesEnabled(true);
    }


    private void addMarkersToMap(DirectionsResult results, GoogleMap map) { // adciona os marcadores
        Log.d("TAG for legs", String.valueOf(results.routes[0].legs.length));

        for (int x = 0; x < results.routes[0].legs.length; x++) {
            if (x == 0) {
                Log.d("Marker if == primeiro: ", String.valueOf(x));
                map.addMarker(new MarkerOptions()
                        .position(new LatLng(results.routes[0].legs[x].startLocation.lat, results.routes[0].legs[x].startLocation.lng))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_farmacia_maps_red))
                        .title(results.routes[0].legs[x].startAddress));
                x++;
            }
            if (x == results.routes[0].legs.length - 1) {
                Log.d("Marker if == ultimo: ", String.valueOf(x));
                map.addMarker(new MarkerOptions()
                        .position(new LatLng(results.routes[0].legs[x].endLocation.lat, results.routes[0].legs[x].endLocation.lng))
                        .title(results.routes[0].legs[x].endAddress)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_farmacia_maps_red))
                        .snippet(getEndLocationTitle(results)));
            }
            Log.d("Marker else: ", String.valueOf(x));
            map.addMarker(new MarkerOptions()
                    .position(new LatLng(results.routes[0].legs[x].startLocation.lat, results.routes[0].legs[x].startLocation.lng))
                    .title(results.routes[0].legs[x].startAddress)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin_maps_casa)));
        }

    }

    private void positionCamera(DirectionsRoute route, GoogleMap map) { //define o zoom de quando o mapa é criado, e onde eu quero que esteja esse zoom
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(route.legs[overview].startLocation.lat, route.legs[overview].startLocation.lng), 13));
    }

    private void addPolyline(DirectionsResult results, GoogleMap mMap) { // aqui desenha a linha da rota com base no resultado da requisição
        List<LatLng> decodePath = PolyUtil.decode(results.routes[overview].overviewPolyline.getEncodedPath());
        mMap.addPolyline(new PolylineOptions().addAll(decodePath));
    }

    private String getEndLocationTitle(DirectionsResult results) { //retorna alguns dados sobre a rota inteira inteira
        return "Time :" + results.routes[overview].legs[overview].duration.humanReadable + "Distancia :" + results.routes[overview].legs[overview].distance.humanReadable;
    }

    private GeoApiContext getGeoContext() {
        GeoApiContext geoApiContext = new GeoApiContext();
        return geoApiContext
                .setQueryRateLimit(3)
                .setApiKey(getString(R.string.direcionsApiKey))
                .setConnectTimeout(10, TimeUnit.SECONDS)
                .setReadTimeout(10, TimeUnit.SECONDS)
                .setWriteTimeout(10, TimeUnit.SECONDS);
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) { // esse metodo aqui transforma um endereço string em Lat e Lng, achei na net mas não to usando mais

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_relacao_boy:
                Intent it = new Intent(Activity_rotas_motoboy.this, Activity_home_motoboy.class);
                startActivity(it);
                break;
            case R.id.ll_conf_boy:
                Intent it_conf = new Intent(Activity_rotas_motoboy.this, Activity_conf_motoboy.class);
                startActivity(it_conf);
                break;
            case R.id.btn_iniciar_maps:
                abrirMaps(enderecos_entregas);
        }
    }

    private void abrirMaps(String[]enderecos) {
        StringBuffer sb = new StringBuffer();
        for(int x = 0 ; x < enderecos.length;x++){
            sb.append("+to:"+enderecos[x]); // aqui eu crio uma string com todos os endereços que eu quero
        }
        Log.d("Abrir Maps StringBugger",sb.toString());
//        Uri uri = Uri.parse("https://maps.google.ch/maps?saddr=" + origen + "&daddr=" + results.routes[0].legs[1].startLocation +
//                "+to:" + results.routes[0].legs[1].startLocation.lat + "," + results.routes[0].legs[1].startLocation.lng);

      //  Uri.parse("https://maps.google.ch/maps?saddr=[address1]&daddr=[address2] to:[address3] to: [address4]");
       // Uri gmmIntentUri = Uri.parse("google.navigation:q=Taronga+Zoo,+Sydney+Australia");

        Uri uri = Uri.parse("https://maps.google.ch/maps?daddr=" + destino + sb.toString()); // desse jeito aqui ele abre o maps com a minha localização atual, o destino que eu quero e todos os waypoints
                //"+to:" + waypoint_2 +"+to:"+waypoint_3+"+to:"+waypoint_4+"+to:"+waypoint_5+"+to:"+destino);
        Log.d("Abrir Maps Uri",uri.toString());
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri); // aqui ele abre o maps
        startActivity(intent);
    }
}
