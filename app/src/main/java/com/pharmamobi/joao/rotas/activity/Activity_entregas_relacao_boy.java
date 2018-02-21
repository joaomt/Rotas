package com.pharmamobi.joao.rotas.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pharmamobi.joao.rotas.Adapter.Entregas_Boy_Adapter;
import com.pharmamobi.joao.rotas.Adapter.Entregas_Relacao_Boy_Adapter;
import com.pharmamobi.joao.rotas.Domain.WebApiUrl;
import com.pharmamobi.joao.rotas.Interfaces.MotoBoyApi;
import com.pharmamobi.joao.rotas.R;
import com.pharmamobi.joao.rotas.model.RelacaoEntregas;
import com.pharmamobi.joao.rotas.model.RelacaoEntregasInfo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.pharmamobi.joao.rotas.activity.Activity_home_motoboy.id_motoboy;

public class Activity_entregas_relacao_boy extends AppCompatActivity {
    private RelacaoEntregasInfo entregasRelacao;
    public static ArrayList<RelacaoEntregasInfo>listEntregasRelacao = new ArrayList<>();
    private WebApiUrl webApi = new WebApiUrl();
    private String URL_BASE = webApi.GetUrlBase();
    private Toolbar tb_entregas_relacao_boy;
    private ListView lv_entregas_relacao_boy;
    private Button btn_iniciar_entregas;
    private TextView txt_total_entregas,txt_entregas_retornadas,txt_entregas_aguardando,txt_valor_total_relacao,txt_valor_total_recebido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entregas_relacao_boy);
        tb_entregas_relacao_boy = (Toolbar)findViewById(R.id.tb_entregas_relacao_boy);
        tb_entregas_relacao_boy.setTitle("Relação");
        setSupportActionBar(tb_entregas_relacao_boy);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String cod_relacao = getIntent().getStringExtra("cod_relacao");
        Log.d("TAG cod_relacao", cod_relacao);
        getRelacaoInfo(cod_relacao);

        lv_entregas_relacao_boy = (ListView)findViewById(R.id.lv_entregas_relacao_boy);

        btn_iniciar_entregas = (Button)findViewById(R.id.btn_iniciar_entregas);

        txt_total_entregas = (TextView)findViewById(R.id.txt_total_entregas);
        txt_entregas_retornadas = (TextView)findViewById(R.id.txt_entregas_retornadas);
        txt_entregas_aguardando = (TextView)findViewById(R.id.txt_entregas_aguardando);
        txt_valor_total_relacao = (TextView)findViewById(R.id.txt_valor_total_relacao);
        txt_valor_total_recebido = (TextView)findViewById(R.id.txt_valor_total_recebido);

        btn_iniciar_entregas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Activity_entregas_relacao_boy.this,Activity_rotas_motoboy.class);
                startActivity(it);
            }
        });
    }

    private void getRelacaoInfo(String cod_relacao) {
            final ProgressDialog progress = new ProgressDialog(this);
            progress.setTitle("Aguarde");
            progress.setMessage("Carregando Relação");
            progress.setProgress(ProgressDialog.STYLE_SPINNER);
            progress.show();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL_BASE + "RelacaoEntregasInfo/getRelacaoEntregas/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            MotoBoyApi getRelacaoService = retrofit.create(MotoBoyApi.class);
            Call<List<RelacaoEntregasInfo>> call = getRelacaoService.getEntregasRelacao(cod_relacao);
            call.enqueue(new Callback<List<RelacaoEntregasInfo>>() {
                @Override
                public void onResponse(Call<List<RelacaoEntregasInfo>> call, Response<List<RelacaoEntregasInfo>> response) {
                    if (response.isSuccessful()) {
                        ArrayList<RelacaoEntregasInfo> RelacaoResponse = (ArrayList<RelacaoEntregasInfo>) response.body();
                        listEntregasRelacao.clear();
                        for(int x=0; x < RelacaoResponse.size();x++) {
                            entregasRelacao = new RelacaoEntregasInfo(
                                    RelacaoResponse.get(x).getID(),
                                    RelacaoResponse.get(x).getQTD_ENTREGAS(),
                                    RelacaoResponse.get(x).getQTD_ENTREGAS_REALIZADAS(),
                                    RelacaoResponse.get(x).getQTD_ENTREGAS_RETORNADAS(),
                                    RelacaoResponse.get(x).getVALOR_TOTAL(),
                                    RelacaoResponse.get(x).getVALOR_TOTAL_RECEBIDO(),
                                    RelacaoResponse.get(x).getDATA_RELACAO(),
                                    RelacaoResponse.get(x).getOBSERVACAO(),
                                    RelacaoResponse.get(x).getSITUACAO_PAGAMENTO(),
                                    RelacaoResponse.get(x).getSTATUS_ENTREGA(),
                                    RelacaoResponse.get(x).getNOME(),
                                    RelacaoResponse.get(x).getCOD_CLIENTE(),
                                    RelacaoResponse.get(x).getCOD_ORCAMENTO(),
                                    RelacaoResponse.get(x).getNUM_COMPRO_VENDA(),
                                    RelacaoResponse.get(x).getCOD_ENTREGA(),
                                    RelacaoResponse.get(x).getVALOR_ENTREGA(),
                                    RelacaoResponse.get(x).getCEP(),
                                    RelacaoResponse.get(x).getRUA(),
                                    RelacaoResponse.get(x).getNUMERO(),
                                    RelacaoResponse.get(x).getCOMPLEMENTO(),
                                    RelacaoResponse.get(x).getBAIRRO(),
                                    RelacaoResponse.get(x).getCIDADE(),
                                    RelacaoResponse.get(x).getESTADO(),
                                    RelacaoResponse.get(x).getLOCAL_RETIRADA()
                            );
                            listEntregasRelacao.add(entregasRelacao);
                        }
                        Adapter adapter = new Entregas_Relacao_Boy_Adapter(listEntregasRelacao, Activity_entregas_relacao_boy.this);
                        lv_entregas_relacao_boy.setAdapter((ListAdapter) adapter);
                        popularCampos(entregasRelacao);
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
                public void onFailure(Call<List<RelacaoEntregasInfo>> call, Throwable t) {
                    Toast.makeText(Activity_entregas_relacao_boy.this,
                            "Não foi possível carregar a relação",
                            Toast.LENGTH_SHORT).show();
                    progress.dismiss();
                }
            }



    );
    }

    private void popularCampos(RelacaoEntregasInfo entregasRelacao) {
        tb_entregas_relacao_boy.setSubtitle(String.valueOf(entregasRelacao.getDATA_RELACAO()));
        txt_total_entregas.setText(String.valueOf(entregasRelacao.getQTD_ENTREGAS()));
        txt_entregas_retornadas.setText(String.valueOf(entregasRelacao.getQTD_ENTREGAS_RETORNADAS()));
        int aguardando = entregasRelacao.getQTD_ENTREGAS_REALIZADAS() - entregasRelacao.getQTD_ENTREGAS_RETORNADAS();
        txt_entregas_aguardando.setText(String.valueOf(aguardando));
        txt_valor_total_relacao.setText(String.valueOf(entregasRelacao.getVALOR_TOTAL()));
        txt_valor_total_recebido.setText(String.valueOf(entregasRelacao.getVALOR_TOTAL_RECEBIDO()));

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
