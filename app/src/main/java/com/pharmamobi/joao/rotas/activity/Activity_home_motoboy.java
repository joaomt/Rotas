package com.pharmamobi.joao.rotas.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.pharmamobi.joao.rotas.Adapter.Entregas_Boy_Adapter;
import com.pharmamobi.joao.rotas.MyServiceLocation;
import com.pharmamobi.joao.rotas.Domain.WebApiUrl;
import com.pharmamobi.joao.rotas.Interfaces.MotoBoyApi;
import com.pharmamobi.joao.rotas.R;
import com.pharmamobi.joao.rotas.model.RelacaoEntregas;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_home_motoboy extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        getRelacaoEntregas(id_motoboy);
    }


    private ArrayList<RelacaoEntregas>listRelacaoEntregas = new ArrayList<>();
    private ListView lv_entregas_boy;
    private WebApiUrl webApi = new WebApiUrl();
    private String URL_BASE = webApi.GetUrlBase();
    public static String id_motoboy;
    private PullRefreshLayout refresh_relacao_entregas;
    private TextView txt_relacao_boy,txt_rotas_boy,txt_conf_boy;
    private Button btn_conf_boy,btn_rotas_boy,btn_relacao_boy;
    private Toolbar tb_home_boy;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_motoboy);

        startService(new Intent(Activity_home_motoboy.this, MyServiceLocation.class));

        tb_home_boy = (Toolbar) findViewById(R.id.tb_home_boy);
        tb_home_boy.setTitle("Home");
        tb_home_boy.setSubtitle("Relações de entregas");
        setSupportActionBar(tb_home_boy);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv_entregas_boy = (ListView)findViewById(R.id.lv_entregas_boy);
        id_motoboy = getIntent().getStringExtra("id_login");
        getRelacaoEntregas(id_motoboy);
        refresh_relacao_entregas = (PullRefreshLayout)findViewById(R.id.refresh_relacao_entregas);
        refresh_relacao_entregas.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRelacaoEntregas(id_motoboy);
            }
        });
        Typeface r_light = Typeface.createFromAsset(getAssets(),"Roboto-Light.ttf");

        txt_relacao_boy = (TextView)findViewById(R.id.txt_relacao_boy);
        txt_rotas_boy = (TextView)findViewById(R.id.txt_rotas_boy);
        txt_conf_boy = (TextView)findViewById(R.id.txt_conf_boy);
        txt_relacao_boy.setTypeface(r_light);
        txt_rotas_boy.setTypeface(r_light);
        txt_conf_boy.setTypeface(r_light);

        btn_relacao_boy = (Button)findViewById(R.id.btn_relacao_boy);
        btn_rotas_boy = (Button)findViewById(R.id.btn_rotas_boy);
        btn_conf_boy = (Button)findViewById(R.id.btn_conf_boy);

        lv_entregas_boy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(Activity_home_motoboy.this,Activity_entregas_relacao_boy.class);
                it.putExtra("cod_relacao",String.valueOf(listRelacaoEntregas.get(i).getID()));
                startActivity(it);
            }
        });
    }

    private void getRelacaoEntregas(String id) {
        Log.d("TAG id motoboy",id);
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Aguarde");
        progress.setMessage("Carregando Relações de Entregas");
        progress.setProgress(ProgressDialog.STYLE_SPINNER);
        progress.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE + "RelacaoEntregas/getRelacaoEntregasBoy/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MotoBoyApi getRelacaoService = retrofit.create(MotoBoyApi.class);
        Call<List<RelacaoEntregas>> call = getRelacaoService.getRelacaoEntrega(id); //id do motoboy
        call.enqueue(new Callback<List<RelacaoEntregas>>() {
            @Override
            public void onResponse(Call<List<RelacaoEntregas>> call, Response<List<RelacaoEntregas>> response) {
                if (response.isSuccessful()) {
                    ArrayList<RelacaoEntregas> RelacaoResponse = (ArrayList<RelacaoEntregas>) response.body();
                    listRelacaoEntregas.clear();
                    for (int x = 0; x < RelacaoResponse.size(); x++) {
                        RelacaoEntregas itens_orcamentos = new RelacaoEntregas(
                                RelacaoResponse.get(x).getID(),
                                RelacaoResponse.get(x).getQTD_ENTREGAS(),
                                RelacaoResponse.get(x).getQTD_ENTREGAS_REALIZADAS(),
                                RelacaoResponse.get(x).getQTD_ENTREGAS_RETORNADAS(),
                                RelacaoResponse.get(x).getVALOR_TOTAL(),
                                RelacaoResponse.get(x).getVALOR_TOTAL_RECEBIDO(),
                                RelacaoResponse.get(x).getDATA_RELACAO()
                        );
                        listRelacaoEntregas.add(itens_orcamentos);
                    }
                    Adapter adapter = new Entregas_Boy_Adapter(listRelacaoEntregas, Activity_home_motoboy.this);
                    lv_entregas_boy.setAdapter((ListAdapter) adapter);
                    progress.dismiss();
                    Log.d("response body itens",String.valueOf(response.body()));
                    Log.d("response code itens",String.valueOf(response.code()));
                    Log.d("response raw itens",String.valueOf(response.raw()));

                }else{
                    Log.d("response body itens",String.valueOf(response.body()));
                    Log.d("response code itens",String.valueOf(response.code()));
                    Log.d("response raw itens",String.valueOf(response.raw()));
                }
            }

            @Override
            public void onFailure(Call<List<RelacaoEntregas>> call, Throwable t) {
                Toast.makeText(Activity_home_motoboy.this,
                        "Não foi possível carregar as relações",
                        Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_rotas_boy:
                Intent it = new Intent(Activity_home_motoboy.this, Activity_rotas_motoboy.class);
                startActivity(it);
                break;
            case R.id.ll_conf_boy:
                Intent it_conf = new Intent(Activity_home_motoboy.this, Activity_conf_motoboy.class);
                startActivity(it_conf);
                break;
        }

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
