package com.pharmamobi.joao.rotas.FireBase;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.pharmamobi.joao.rotas.MenuPrincipal;
import com.pharmamobi.joao.rotas.R;
import com.pharmamobi.joao.rotas.activity.Activity_detalhes_Orcamento;
import com.pharmamobi.joao.rotas.activity.Activity_detalhes_entrega;
import com.pharmamobi.joao.rotas.model.Orcamentos;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by joao on 02/11/2017.
 */


public class MFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "notificação";
    private Intent intent;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String from = remoteMessage.getFrom();
        Log.d(TAG,"Mensagem de: "+ from);
        Log.d(TAG, String.valueOf(remoteMessage.getData()));
        showNotification(
                remoteMessage.getData().get("title"),
                remoteMessage.getData().get("body"),
                remoteMessage.getData().get("categoria"),
                remoteMessage.getData().get("id"));


//        if(remoteMessage.getNotification() != null){
//            Log.d(TAG, "Notificação: " + remoteMessage.getNotification().getBody());
//            showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
//        }
        if(remoteMessage.getData().size() > 0){
            Log.d(TAG, "Data: " + remoteMessage.getData());
        }


    }

    private void showNotification(String title, String body, String categoria, String id) {

        if(categoria.equals("orcamento")){
            intent = new Intent(this, Activity_detalhes_Orcamento.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("cod_orcamento",id);
        }if(categoria.equals("entrega")){
            intent = new Intent(this, Activity_detalhes_entrega.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("cv_entrega",id);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.icone_app)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_HIGH)
                .setColor(getResources().getColor(R.color.colorPrimary))
                .setLights(Color.RED, 1000, 500)
                .setSound(soundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notifiManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notifiManager.notify(0,notificationBuilder.build());
    }
}
