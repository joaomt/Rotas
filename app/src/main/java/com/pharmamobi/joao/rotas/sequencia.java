package com.pharmamobi.joao.rotas;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;

public class sequencia extends AppCompatActivity implements Runnable{

    public void onCreate(Bundle instancia){
        super.onCreate(instancia);
        setContentView(R.layout.sequencia);

       /* Bundle inBundle = getIntent().getExtras();
        String nome = inBundle.get("nome").toString();
        String sobrenome = inBundle.get("sobrenome").toString();
        String imgPerfil = inBundle.get("imageUrl").toString();


        ImageView img_Perfil = (ImageView) findViewById(R.id.imgProfile);
        new DownloadImage((img_Perfil)).execute(imgPerfil);
        TextView txt_nomePerfil = (TextView)findViewById(R.id.txt_nomePerfil);
        txt_nomePerfil.setText(nome + "" + sobrenome);



        ImageView img =(ImageView)findViewById(R.id.img_view_animacao);
        img.setBackgroundResource(R.drawable.animacao);

        AnimationDrawable animation=(AnimationDrawable)img.getBackground();
        animation.start();*/


    }
    public void voltar_principal(View view){
        run();
    }

    @Override
        public void run(){
            LoginManager.getInstance().logOut();
            Intent intent = new Intent(this, MenuPrincipal.class);
            startActivity(intent);
            finish();
    }


}
