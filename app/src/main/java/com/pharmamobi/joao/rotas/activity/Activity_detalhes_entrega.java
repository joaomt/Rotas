package com.pharmamobi.joao.rotas.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pharmamobi.joao.rotas.Adapter.MEntregasAdapter;
import com.pharmamobi.joao.rotas.Domain.WebApiUrl;
import com.pharmamobi.joao.rotas.FragmentMinhasEntregas;
import com.pharmamobi.joao.rotas.Interfaces.ClientesApi;
import com.pharmamobi.joao.rotas.R;
import com.pharmamobi.joao.rotas.model.MEntregas;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by joao on 14/06/2017.
 */

public class Activity_detalhes_entrega extends AppCompatActivity {
    private Toolbar tb_dtl_entrega;
    private TextView txt_n_pedido, txt_status, txt_farmacia, txt_produto, txt_valor_entrega, txt_data_entrega, txt_data_pedido, txt_desc_prod;
    private TextView txt_acomp_entrega, txt_reag_entrega, txt_avaliar_entrega;
    private ImageButton btn_acompanhar_entrega, btn_reagendar_entrega, btn_avaliar_entrega;
    private int cor_vermelho, cor_verde, cor_amarelo;
    private final String status_entregue = "ENTREGUE";
    private final String status_enviado = "ENVIADO";
    private final String status_cancelado = "CANCELADO";
    private WebApiUrl webApi = new WebApiUrl();
    private String URL_BASE = webApi.GetUrlBase();
    public static MEntregas entrega;
    private String cv_entrega;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_entrega);
        Typeface R_light = Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");
        cv_entrega = getIntent().getStringExtra("cv_entrega");
        if (cv_entrega != null) {
            getEntregaCV(cv_entrega);
            Log.d("TAG CV", cv_entrega);
        }

        tb_dtl_entrega = (Toolbar) findViewById(R.id.tb_detalhes_entrega);
        tb_dtl_entrega.setTitle("Detalhes da Entrega");
        setSupportActionBar(tb_dtl_entrega);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_acompanhar_entrega = (ImageButton) findViewById(R.id.btn_acompanhar_entrega);
        btn_reagendar_entrega = (ImageButton) findViewById(R.id.btn_reagendar_entrega);
        btn_avaliar_entrega = (ImageButton) findViewById(R.id.btn_avaliar_entrega);

        btn_acompanhar_entrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Activity_detalhes_entrega.this, Activity_acompanhar_entrega.class);
                startActivity(it);
            }
        });
        btn_avaliar_entrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Activity_detalhes_entrega.this, Activity_avaliar_entrega.class);
                it.putExtra("cod_entrega", entrega.getCOD_ENTREGA());
                Log.d("TAG start",String.valueOf(entrega.getCOD_ENTREGA()));
                startActivity(it);
            }
        });
        btn_reagendar_entrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Activity_detalhes_entrega.this, Activity_reagendar_entrega.class);
                startActivity(it);
            }
        });

        txt_n_pedido = (TextView) findViewById(R.id.txt_n_ped);
        txt_status = (TextView) findViewById(R.id.txt_status);
        txt_produto = (TextView) findViewById(R.id.txt_produto);
        txt_valor_entrega = (TextView) findViewById(R.id.txt_valor_entrega);
        txt_data_entrega = (TextView) findViewById(R.id.txt_data_entrega);
        txt_data_pedido = (TextView) findViewById(R.id.txt_data_pedido);
        txt_desc_prod = (TextView) findViewById(R.id.txt_descricao_produto);
        txt_acomp_entrega = (TextView) findViewById(R.id.txt_acomp_entrega);
        txt_reag_entrega = (TextView) findViewById(R.id.txt_reag_entrega);
        txt_avaliar_entrega = (TextView) findViewById(R.id.txt_avaliar_entrega);

        txt_n_pedido.setTypeface(R_light);
        txt_status.setTypeface(R_light);
        txt_produto.setTypeface(R_light);
        txt_valor_entrega.setTypeface(R_light);
        txt_data_entrega.setTypeface(R_light);
        txt_data_pedido.setTypeface(R_light);
        txt_desc_prod.setTypeface(R_light);
        txt_avaliar_entrega.setTypeface(R_light);
        txt_reag_entrega.setTypeface(R_light);
        txt_acomp_entrega.setTypeface(R_light);
    }

    private void popularCampos(MEntregas entrega) {

        txt_n_pedido.setText(String.valueOf(entrega.getNUM_COMPRO_VENDA()));
        //txt_produto.setText(produto);
        //txt_farmacia.setText(farmacia);
        txt_data_entrega.setText(String.valueOf(entrega.getDATA_RELACAO()));
        txt_valor_entrega.setText(String.valueOf(entrega.getVALOR_ENTREGA()));
        //txt_data_pedido.setText(data_pedido);
        //txt_desc_prod.setText(descricao_produto);

        cor_vermelho = Color.parseColor("#ec1515");
        cor_verde = Color.parseColor("#FF40EC29");
        cor_amarelo = Color.parseColor("#FFEEFA00");

        if (String.valueOf(entrega.getSTATUS_ENTREGA()).equals(status_entregue)) {
            txt_status.setTextColor(cor_verde);
            txt_status.setText("Entregue");
        }
        if (String.valueOf(entrega.getSTATUS_ENTREGA()).equals(status_enviado)) {
            txt_status.setTextColor(cor_amarelo);
            txt_status.setText("Enviado");
        }
        if (String.valueOf(entrega.getSTATUS_ENTREGA()).equals(status_cancelado)) {
            txt_status.setTextColor(cor_vermelho);
            txt_status.setText("Cancelado");
        }
    }

    private void getEntregaCV(String CV) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE + "Entregas/GetEntregaComprovanteSimple/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ClientesApi getEntregaService = retrofit.create(ClientesApi.class);
        Call<MEntregas> call = getEntregaService.getEntregaCV_S(CV);

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Activity_detalhes_entrega.this);
        //progressDoalog.setMax(100);
        progressDoalog.setMessage("Aguarde......");
        progressDoalog.setTitle("Procurando Entrega");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<MEntregas>() {
                         @Override
                         public void onResponse(Call<MEntregas> call, Response<MEntregas> response) {
                             if (response.isSuccessful()) {
                                 MEntregas entregaResponse = response.body();
                                 if (response.body() != null) {
                                     entrega = new MEntregas(
                                             entregaResponse.getCOD_ENTREGA(),
                                             entregaResponse.getCOD_ENDERECO(),
                                             entregaResponse.getOBSERVACAO(),
                                             entregaResponse.getSITUACAO_PAGAMENTO(),
                                             entregaResponse.getSTATUS_ENTREGA(),
                                             entregaResponse.getDATA_RELACAO(),
                                             entregaResponse.getCOD_CLIENTE(),
                                             entregaResponse.getCOD_ORCAMENTO(),
                                             entregaResponse.getNUM_COMPRO_VENDA(),
                                             entregaResponse.getVALOR_ENTREGA(),
                                             entregaResponse.getLOCAL_RETIRADA());
                                 }
                                 popularCampos(entrega);
                                 progressDoalog.dismiss();
                             } else {
                                 // Get response errorBody
                                 progressDoalog.dismiss();
                                 Log.d("Response errorBody", String.valueOf(response.errorBody()));
                             }
                         }

                         @Override
                         public void onFailure(Call<MEntregas> call, Throwable t) {
                             Toast.makeText(Activity_detalhes_entrega.this,
                                     "Não encontramos sua entrega",
                                     Toast.LENGTH_SHORT).show();
                             progressDoalog.dismiss();
                         }
                     }

        );
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