package com.pharmamobi.joao.rotas.activity;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pharmamobi.joao.rotas.Domain.WebApiUrl;
import com.pharmamobi.joao.rotas.Interfaces.ClientesApi;
import com.pharmamobi.joao.rotas.R;
import com.pharmamobi.joao.rotas.model.AvaliacaoEntrega;
import com.pharmamobi.joao.rotas.model.MEntregas;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Activity_avaliar_entrega extends AppCompatActivity implements View.OnClickListener {
    private WebApiUrl webApi = new WebApiUrl();
    private String URL_BASE = webApi.GetUrlBase();
    private Toolbar tb_avaliar_entrega;
    private CheckBox cb_1, cb_2, cb_3;
    private EditText edt_obs1, edt_obs2, edt_obs3, edt_sugestao;
    private TextView txt_sugestao, txt_problema, txt_nota,txt_nota_entrega;
    private ProgressDialog progressDialog;
    private RatingBar nota;
    private String cod_entrega;
    private Button btn_enviarAvaliacao;

    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_avaliar_entrega);
        progressDialog = new ProgressDialog(this);
        cod_entrega = String.valueOf(getIntent().getIntExtra("cod_entrega",0));
        Log.d("TAG oncreate",cod_entrega);
        getAvaliacao(cod_entrega);

        Typeface R_light = Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");
        tb_avaliar_entrega = (Toolbar) findViewById(R.id.tb_avaliar_entrega);
        tb_avaliar_entrega.setTitle("Avaliar Entrega");
        setSupportActionBar(tb_avaliar_entrega);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_enviarAvaliacao = (Button)findViewById(R.id.btn_enviarAvaliacao);
        nota = (RatingBar) findViewById(R.id.rt_nota);
        edt_sugestao = (EditText) findViewById(R.id.edt_sugestao);
        edt_obs1 = (EditText) findViewById(R.id.edt_obs1);
        edt_obs2 = (EditText) findViewById(R.id.edt_obs2);
        edt_obs3 = (EditText) findViewById(R.id.edt_obs3);
        txt_nota = (TextView) findViewById(R.id.txt_nota);
        txt_problema = (TextView) findViewById(R.id.txt_problema);
        txt_sugestao = (TextView) findViewById(R.id.txt_sugestao);
        txt_nota_entrega = (TextView) findViewById(R.id.txt_nota_entrega);

        txt_problema.setTypeface(R_light);
        txt_sugestao.setTypeface(R_light);
        txt_nota.setTypeface(R_light);
        txt_nota_entrega.setTypeface(R_light);

        cb_1 = (CheckBox) findViewById(R.id.cb_1);
        cb_2 = (CheckBox) findViewById(R.id.cb_2);
        cb_3 = (CheckBox) findViewById(R.id.cb_3);

        cb_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inflaEdtTexet(view);
            }
        });
        cb_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inflaEdtTexet(view);
            }
        });
        cb_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inflaEdtTexet(view);
            }
        });
    }

    private void getAvaliacao(String cod_entrega) {
        progressDialog.setMessage("Carregando");
        progressDialog.setProgress(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE + "AvaliacaoEntregas/GetAvaliacao/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ClientesApi Avaliacao = retrofit.create(ClientesApi.class);
        Call<AvaliacaoEntrega> call = Avaliacao.getAvaliacao(cod_entrega); //get avaliação com cod entrega

        call.enqueue(new Callback<AvaliacaoEntrega>() {
                         @Override
                         public void onResponse(Call<AvaliacaoEntrega> call, Response<AvaliacaoEntrega> response) {
                             if (response.isSuccessful()) {
                                 AvaliacaoEntrega avaliacaoResponse = response.body();
                                 if (response.body() != null) {
                                     AvaliacaoEntrega avaliacao = new AvaliacaoEntrega(
                                             avaliacaoResponse.getCOD_ENTREGA(),
                                             avaliacaoResponse.getNOTA(),
                                             avaliacaoResponse.getOBS_1(),
                                             avaliacaoResponse.getOBS_2(),
                                             avaliacaoResponse.getOBS_3(),
                                             avaliacaoResponse.getSUGESTAO()
                                     );
                                     popularCampos(avaliacao);
                                 }
                                 progressDialog.dismiss();
                             } else {
                                 // Get response errorBody
                                 progressDialog.dismiss();
                                 Log.d("Response errorBody", String.valueOf(response.errorBody()));
                             }
                         }

                         @Override
                         public void onFailure(Call<AvaliacaoEntrega> call, Throwable t) {
                             Toast.makeText(Activity_avaliar_entrega.this,
                                     "Não encontramos sua entrega",
                                     Toast.LENGTH_SHORT).show();
                             progressDialog.dismiss();
                         }
                     }

        );
    }

    private void popularCampos(AvaliacaoEntrega avaliacao) {
        if(avaliacao != null){
            txt_nota.setText("Sua avaliação");
            txt_nota_entrega.setText("Nota atribuida");
            nota.setRating(avaliacao.getNOTA());
            nota.setClickable(false);
            nota.setFocusable(false);
            if(avaliacao.getOBS_1().length() >1 || avaliacao.getOBS_2().length() >1 || avaliacao.getOBS_3().length() >1){
                txt_problema.setText("Observações sobre a entrega");
                if(avaliacao.getOBS_1().length() >1){
                    cb_1.setChecked(true);
                    cb_1.setClickable(false);
                    edt_obs1.setHint(avaliacao.getOBS_1());
                    edt_obs1.setVisibility(View.VISIBLE);
                    edt_obs1.setClickable(false);
                    edt_obs1.setFocusable(false);
                }else{
                    edt_obs1.setVisibility(View.GONE);
                    cb_1.setChecked(false);
                    cb_1.setClickable(false);
                }
                if(avaliacao.getOBS_2().length() >1){
                    cb_2.setChecked(true);
                    cb_2.setClickable(false);
                    edt_obs2.setClickable(false);
                    edt_obs2.setFocusable(false);
                    edt_obs2.setHint(avaliacao.getOBS_2());
                    edt_obs2.setVisibility(View.VISIBLE);
                }else{
                    cb_2.setClickable(false);
                    edt_obs2.setVisibility(View.GONE);
                    cb_2.setChecked(false);
                }
                if(avaliacao.getOBS_3().length() >1){
                    cb_3.setChecked(true);
                    cb_3.setClickable(false);
                    edt_obs3.setClickable(false);
                    edt_obs3.setFocusable(false);
                    edt_obs3.setHint(avaliacao.getOBS_3());
                    edt_obs3.setVisibility(View.VISIBLE);
                }else{
                    cb_3.setClickable(false);
                    edt_obs3.setVisibility(View.GONE);
                    cb_3.setChecked(false);
                }
            }
            if(avaliacao.getSUGESTAO().length() >1){
                txt_sugestao.setVisibility(View.VISIBLE);
                edt_sugestao.setHint(avaliacao.getSUGESTAO());
                edt_sugestao.setClickable(false);
                edt_sugestao.setFocusable(false);
            }
            btn_enviarAvaliacao.setVisibility(View.GONE);

        }
    }


    private void inflaEdtTexet(View view) {
        if (view.getId() == R.id.cb_1) {
            if (cb_1.isChecked()) {
                edt_obs1.setVisibility(View.VISIBLE);
            } else {
                edt_obs1.setVisibility(View.GONE);
            }
        }
        if (view.getId() == R.id.cb_2) {
            if (cb_1.isChecked()) {
                edt_obs2.setVisibility(View.VISIBLE);
            } else {
                edt_obs2.setVisibility(View.GONE);
            }
        }
        if (view.getId() == R.id.cb_3) {
            if (cb_3.isChecked()) {
                edt_obs3.setVisibility(View.VISIBLE);
            } else {
                edt_obs3.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.btn_enviarAvaliacao):
                enviarAvaliacao(cod_entrega);
        }
    }

    private void enviarAvaliacao(String id_entrega) {
        Log.d("TAG oncreate",id_entrega);
        progressDialog.setMessage("Aguarde......");
        progressDialog.setTitle("Enviando Avaliação");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE + "AvaliacaoEntregas/SalvarAvaliacao/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ClientesApi clienteResource = retrofit.create(ClientesApi.class);
        AvaliacaoEntrega avaliacao = new AvaliacaoEntrega(
                id_entrega,
                nota.getRating(),
                edt_obs1.getText().toString(),
                edt_obs2.getText().toString(),
                edt_obs3.getText().toString(),
                edt_sugestao.getText().toString()
        );
        Call<AvaliacaoEntrega> call = clienteResource.postAvaliacao(avaliacao);
        call.enqueue(new Callback<AvaliacaoEntrega>() {
            @Override
            public void onResponse(Call<AvaliacaoEntrega> call, Response<AvaliacaoEntrega> response) {
                if (response.isSuccessful()) {
                    Log.d("response code", String.valueOf(response.code()));
                    Log.d("response raw", String.valueOf(response.raw()));
                    //Log.d("response body",String.valueOf(response.body().getCOD_ORCAMENTO()));
                    Log.d("response raw message", response.raw().message());
                    Log.d("response raw message", String.valueOf(response.body()));
                    progressDialog.dismiss();
                    alertaDialog("Obrigado", "Sua avaliação foi enviada com sucesso");
                    finish();
                } else {
                    Log.d("response code", String.valueOf(response.code()));
                    Log.d("response raw", String.valueOf(response.raw()));
                    //Log.d("response body",String.valueOf(response.body().getCOD_ORCAMENTO()));
                    Log.d("response raw message", response.raw().message());
                    Log.d("response raw message", String.valueOf(response.body()));
                    progressDialog.dismiss();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<AvaliacaoEntrega> call, Throwable t) {
                alertaDialog("Ops", "Não foi possível aprovar seu orçamento ):");
                progressDialog.dismiss();
                onRestart();
            }
        });
    }

    private void alertaDialog(String titulo, String mensagem) {
        AlertDialog alerta = new AlertDialog.Builder(this).create();
        alerta.setTitle(titulo);
        alerta.setMessage(mensagem);
        alerta.setIcon(R.mipmap.ic_atencao);
        alerta.show();
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
