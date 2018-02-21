package com.pharmamobi.joao.rotas.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.manager.SupportRequestManagerFragment;
import com.pharmamobi.joao.rotas.R;

/**
 * Created by joao on 14/06/2017.
 */

public class Activity_acompanhar_entrega extends AppCompatActivity {
    private Toolbar tb_dtl_entrega;
    private TextView txt_che,txt_chegada_estimada,txt_hrs,txt_ed,txt_endereco_entrega,txt_info_localizacao,txt_qtd,txt_qtd_entregas_faltantes,txt_localizacao;
    private Button btn_acompanhar_entrega, btn_reagendar_entrega, btn_avaliar;
    private int cor_vermelho,cor_verde,cor_amarelo;
    private final int status_entregue = 1;
    private final int status_enviado=2;
    private final int status_cancelado=3;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acompanhar_entrega);

        tb_dtl_entrega = (Toolbar) findViewById(R.id.tb_acompanhar_entrega);
        tb_dtl_entrega.setTitle("Acompanhar Entrega");
        setSupportActionBar(tb_dtl_entrega);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Typeface r_light = Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");
        Typeface r_lightItalic = Typeface.createFromAsset(getAssets(), "Roboto-LightItalic.ttf");

        txt_che = (TextView)findViewById(R.id.txt_che);
        txt_chegada_estimada = (TextView)findViewById(R.id.txt_chegada_estimada);
        txt_hrs = (TextView)findViewById(R.id.txt_hrs);
        txt_ed = (TextView)findViewById(R.id.txt_ed);
        txt_endereco_entrega = (TextView)findViewById(R.id.txt_endereco_entrega);
        txt_qtd = (TextView)findViewById(R.id.txt_qtd);
        txt_qtd_entregas_faltantes = (TextView)findViewById(R.id.txt_qtd_entregas_faltantes);
        txt_localizacao = (TextView)findViewById(R.id.txt_localizacao);
        txt_info_localizacao = (TextView)findViewById(R.id.txt_info_localizacao);

        txt_che.setTypeface(r_light);
        txt_chegada_estimada.setTypeface(r_lightItalic);
        txt_hrs.setTypeface(r_light);
        txt_ed.setTypeface(r_light);
        txt_endereco_entrega.setTypeface(r_light);
        txt_qtd.setTypeface(r_light);
        txt_qtd_entregas_faltantes.setTypeface(r_light);
        txt_localizacao.setTypeface(r_light);
        txt_info_localizacao.setTypeface(r_light);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }
}
