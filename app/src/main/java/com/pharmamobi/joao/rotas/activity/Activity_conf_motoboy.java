package com.pharmamobi.joao.rotas.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.pharmamobi.joao.rotas.R;

public class Activity_conf_motoboy extends AppCompatActivity implements View.OnClickListener {
    private ImageButton btn_conf_boy,btn_rotas_boy,btn_relacao_boy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conf_motoboy);
        Typeface r_light = Typeface.createFromAsset(getAssets(),"Roboto-Light.ttf");
        TextView txt_temp_ajustes = (TextView)findViewById(R.id.txt_temp_ajustes);
        txt_temp_ajustes.setTypeface(r_light);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_rotas_boy:
                Intent it = new Intent(Activity_conf_motoboy.this, Activity_rotas_motoboy.class);
                startActivity(it);
                overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
                break;
            case R.id.ll_home_boy:
                Intent it_conf = new Intent(Activity_conf_motoboy.this, Activity_home_motoboy.class);
                startActivity(it_conf);
                overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
                break;
        }
    }
}
