package com.pharmamobi.joao.rotas.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.baoyz.widget.RefreshDrawable;
import com.pharmamobi.joao.rotas.Adapter.EnderecoAdapter;
import com.pharmamobi.joao.rotas.Domain.EnderecoDef;
import com.pharmamobi.joao.rotas.Domain.WebApiUrl;
import com.pharmamobi.joao.rotas.Interfaces.ClientesApi;
import com.pharmamobi.joao.rotas.R;
import com.pharmamobi.joao.rotas.model.Endereco;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class Activity_definir_end_entrega extends AppCompatActivity {
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
        if(ck_selecionado == 1) {
            definirEndCasa();
        }if(ck_selecionado == 2){
            definirEndLoja();
        }
    }
    private FloatingActionButton fab_anexo, fab_obs, fab_entrega;
    private Toolbar tb_selec_end;
    private Button btn_novo_endereco;
    private ArrayList<Endereco> ListEndereco = new ArrayList<>();
    private ListView listViewEnderecos;
    Endereco end = new Endereco();
    private WebApiUrl webApi = new WebApiUrl();
    private String URL_BASE = webApi.GetUrlBase();
    private PullRefreshLayout layout;
    private int ck_selecionado;
    private RadioButton rb_rua;
    private TextView txt_bairro;

    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_def_end_entrega);
        Intent intent = getIntent();
        ck_selecionado = intent.getIntExtra("int_value", 0);
        layout = (PullRefreshLayout) findViewById(R.id.refresh_def_endereco);
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(ck_selecionado == 1) {
                    definirEndCasa();
                }if(ck_selecionado == 2){
                    definirEndLoja();
                }
            }
        });

        listViewEnderecos = (ListView) findViewById(R.id.lv_enderecos_entregas);

        btn_novo_endereco = (Button) findViewById(R.id.btn_adc_end);

        tb_selec_end = (Toolbar) findViewById(R.id.tb_selec_end);
        setSupportActionBar(tb_selec_end);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tb_selec_end.setTitle("Definir endereço");

        btn_novo_endereco = (Button) findViewById(R.id.btn_adc_end);
        btn_novo_endereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Activity_definir_end_entrega.this, Activity_buscar_end_cep.class);
                startActivity(it);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
        if (ck_selecionado == 1) {
            definirEndCasa();
            btn_novo_endereco.setVisibility(VISIBLE);
            tb_selec_end.setTitle("Retirar em casa");
        }
        if (ck_selecionado == 2) {
            definirEndLoja();
            btn_novo_endereco.setVisibility(GONE);
            tb_selec_end.setTitle("Loja para retirar");
        }
        listViewEnderecos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                EnderecoDef.ID = ListEndereco.get(position).getID();
                EnderecoDef.COD_CLIENTE = ListEndereco.get(position).getCOD_CLIENTE();
                EnderecoDef.CEP = ListEndereco.get(position).getCEP();
                EnderecoDef.RUA = ListEndereco.get(position).getRUA();
                EnderecoDef.NUMERO = ListEndereco.get(position).getNUMERO();
                EnderecoDef.COMPLEMENTO = ListEndereco.get(position).getCOMPLEMENTO();
                EnderecoDef.BAIRRO = ListEndereco.get(position).getBAIRRO();
                EnderecoDef.CIDADE = ListEndereco.get(position).getCIDADE();
                EnderecoDef.ESTADO = ListEndereco.get(position).getESTADO();
                EnderecoDef.valida = ck_selecionado;
                if(ck_selecionado ==1){
                    EnderecoDef.LOCAL_RETIRADA = "ENTREGA";
                }if(ck_selecionado ==2){
                    EnderecoDef.LOCAL_RETIRADA = "LOJA";
                }
                finish();
            }
        });
    }

    private void definirEndCasa() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE + "Clientes/getEnderecos/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ClientesApi getEnderecoService = retrofit.create(ClientesApi.class);
        Call<List<Endereco>> call = getEnderecoService.getEnderecosCliente("2"); //id do cliente

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(this);
        //progressDoalog.setMax(100);
        progressDoalog.setMessage("Aguarde......");
        progressDoalog.setTitle("Atualizando seus endereços");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<List<Endereco>>() {
            @Override
            public void onResponse(Call<List<Endereco>> call, Response<List<Endereco>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Endereco> enderecoResponse = (ArrayList<Endereco>) response.body();

                        ListEndereco.clear();
                        for (int x = 0; x < enderecoResponse.size(); x++) {
                            Endereco enderecos = new Endereco(
                                    enderecoResponse.get(x).getID(),
                                    enderecoResponse.get(x).getCOD_CLIENTE(),
                                    enderecoResponse.get(x).getCEP(),
                                    enderecoResponse.get(x).getRUA(),
                                    enderecoResponse.get(x).getNUMERO(),
                                    enderecoResponse.get(x).getCOMPLEMENTO(),
                                    enderecoResponse.get(x).getBAIRRO(),
                                    enderecoResponse.get(x).getCIDADE(),
                                    enderecoResponse.get(x).getESTADO(),
                                    R.drawable.ic_home_orc_roxo);
                            ListEndereco.add(enderecos);
                        }
                    Adapter adapter = new EnderecoAdapter(ListEndereco, Activity_definir_end_entrega.this);
                    listViewEnderecos.setAdapter((ListAdapter) adapter);
                    layout.setRefreshing(false);
                    progressDoalog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<Endereco>> call, Throwable t) {
                Toast.makeText(Activity_definir_end_entrega.this,
                        "Não foi possível realizar a requisição",
                        Toast.LENGTH_SHORT).show();
                progressDoalog.dismiss();
            }
        });
    }

    private void definirEndLoja() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE + "Empresa/GetEndFiliais/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ClientesApi getEnderecoService = retrofit.create(ClientesApi.class);
        Call<List<Endereco>> call = getEnderecoService.getEnderecosCliente("1"); //id da empresa

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(this);
        //progressDoalog.setMax(100);
        progressDoalog.setMessage("Aguarde......");
        progressDoalog.setTitle("Atualizando endereços");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<List<Endereco>>() {
            @Override
            public void onResponse(Call<List<Endereco>> call, Response<List<Endereco>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Endereco> enderecoResponse = (ArrayList<Endereco>) response.body();

                    ListEndereco.clear();
                    for (int x = 0; x < enderecoResponse.size(); x++) {
                        Endereco enderecos = new Endereco(
                                enderecoResponse.get(x).getID(),
                                enderecoResponse.get(x).getCOD_CLIENTE(),
                                enderecoResponse.get(x).getCEP(),
                                enderecoResponse.get(x).getRUA(),
                                enderecoResponse.get(x).getNUMERO(),
                                enderecoResponse.get(x).getCOMPLEMENTO(),
                                enderecoResponse.get(x).getBAIRRO(),
                                enderecoResponse.get(x).getCIDADE(),
                                enderecoResponse.get(x).getESTADO(),
                                R.drawable.ic_maps_pin_roxo);
                        ListEndereco.add(enderecos);
                    }
                    Adapter adapter = new EnderecoAdapter(ListEndereco, Activity_definir_end_entrega.this);
                    listViewEnderecos.setAdapter((ListAdapter) adapter);
                    layout.setRefreshing(false);
                    progressDoalog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<Endereco>> call, Throwable t) {
                Toast.makeText(Activity_definir_end_entrega.this,
                        "Não foi possível realizar a requisição",
                        Toast.LENGTH_SHORT).show();
                progressDoalog.dismiss();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            overridePendingTransition(0, android.R.anim.slide_out_right);
        }
        return true;
    }


}
