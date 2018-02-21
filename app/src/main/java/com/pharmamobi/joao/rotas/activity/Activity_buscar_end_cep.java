package com.pharmamobi.joao.rotas.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.pharmamobi.joao.rotas.Domain.WebApiUrl;
import com.pharmamobi.joao.rotas.Interfaces.ClientesApi;
import com.pharmamobi.joao.rotas.Interfaces.GetEnderecoApi;
import com.pharmamobi.joao.rotas.R;
import com.pharmamobi.joao.rotas.model.Endereco;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by joao on 29/08/2017.
 */
public class Activity_buscar_end_cep extends AppCompatActivity {

    private FloatingActionButton fab_anexo,fab_obs,fab_entrega;
    private Toolbar tb_busca_end;
    private Button btn_salvar_end;
    private EditText edt_cep,edt_rua,edt_numero,edt_complemento,edt_bairro,edt_cidade,edt_estado;
    private TextView txt_cep,txt_rua,txt_numero,txt_complemento,txt_bairro,txt_cidade,txt_estado,txt_busc_end;
    private LinearLayout ll_campos;
    private WebApiUrl webApi = new WebApiUrl();
    private String URL_BASE = webApi.GetUrlBase();
    private PullRefreshLayout layout;

    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_busca_end_cep);

        tb_busca_end = (Toolbar)findViewById(R.id.tb_busca_end);
        tb_busca_end.setTitle("Adcionar Endereço");
        setSupportActionBar(tb_busca_end);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layout = (PullRefreshLayout) findViewById(R.id.refreshLayout);

        btn_salvar_end = (Button)findViewById(R.id.btn_salvar_end);

        ll_campos = (LinearLayout)findViewById(R.id.ll_campos);
        edt_cep = (EditText)findViewById(R.id.edt_busca_end_cep);
        edt_rua = (EditText)findViewById(R.id.edt_rua);
        edt_numero = (EditText)findViewById(R.id.edt_numero);
        edt_complemento = (EditText)findViewById(R.id.edt_complemento);
        edt_bairro = (EditText)findViewById(R.id.edt_bairro);
        edt_cidade = (EditText)findViewById(R.id.edt_cidade);
        edt_estado = (EditText)findViewById(R.id.edt_estado);

        txt_cep = (TextView)findViewById(R.id.txt_cep);
        txt_rua = (TextView)findViewById(R.id.txt_rua);
        txt_numero = (TextView)findViewById(R.id.txt_numero);
        txt_complemento = (TextView)findViewById(R.id.txt_complemento);
        txt_bairro = (TextView)findViewById(R.id.txt_bairro);
        txt_cidade = (TextView)findViewById(R.id.txt_cidade);
        txt_estado = (TextView)findViewById(R.id.txt_estado);
        txt_busc_end = (TextView)findViewById(R.id.txt_busc_end);

        verificaCampos(edt_cep,txt_cep);
        verificaCampos(edt_rua,txt_rua);
        verificaCampos(edt_numero,txt_numero);
        verificaCampos(edt_complemento,txt_complemento);
        verificaCampos(edt_bairro,txt_bairro);
        verificaCampos(edt_cidade,txt_cidade);
        verificaCampos(edt_estado,txt_estado);

        edt_cep.addTextChangedListener(new TextWatcher() {
            // depois de terminar de escrever
            public void afterTextChanged(Editable s) {
            }

            // antes da ultima edição
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            //edição em tempo real
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edt_cep.getText().length() > 7) {
                    buscarCep(edt_cep.getText().toString());
                    txt_busc_end.setVisibility(View.GONE);
                }else{
                    txt_busc_end.setVisibility(View.VISIBLE);
                }
            }
        });
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                buscarCep(edt_cep.getText().toString());
            }
        });

        btn_salvar_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    try {
                        salvarEndereco();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        });

        }
    private void verificaCampos(final EditText edt_text,final TextView txt)
        {
            edt_text.addTextChangedListener(new TextWatcher() {
                // depois de terminar de escrever
                public void afterTextChanged(Editable s) {
                }

                // antes da ultima edição
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                //edição em tempo real
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (edt_text.getText().length() > 1) {
                        txt.setVisibility(View.VISIBLE);
                    }else{
                        txt.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }

    private void salvarEndereco() throws Exception {
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(this);
        progressDoalog.setMessage("Aguarde......");
        progressDoalog.setTitle("Salvando Endereço");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE + "Clientes/PostEnderecoClientes/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ClientesApi clienteResource = retrofit.create(ClientesApi.class);
        Endereco endereco = new Endereco(
                2,
                edt_cep.getText().toString(),
                edt_rua.getText().toString(),
                edt_numero.getText().toString(),
                edt_complemento.getText().toString(),
                edt_bairro.getText().toString(),
                edt_cidade.getText().toString(),
                edt_estado.getText().toString()
        );
        Call <Endereco> call = clienteResource.postEnderecoCliente(endereco);
        call.enqueue(new Callback<Endereco>() {
            @Override
            public void onResponse(Call<Endereco> call, Response<Endereco> response) {
                if(response.isSuccessful())
                {
                    Log.d("response code",String.valueOf(response.code()));
                    Log.d("response raw",String.valueOf(response.raw()));
                    //Log.d("response body",String.valueOf(response.body().getCOD_ORCAMENTO()));
                    Log.d("response raw message",response.raw().message());
                    progressDoalog.dismiss();
                    finish();
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                }
            }

            @Override
            public void onFailure(Call<Endereco> call, Throwable t) {
                alertaDialog("Ops","Não foi possível salvar seu endereço ):");

                progressDoalog.dismiss();
            }
        });
    }

    private void buscarCep(String cep) {
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Activity_buscar_end_cep.this);
        //progressDoalog.setMax(100);
        progressDoalog.setMessage("Aguarde......");
        progressDoalog.setTitle("Localizando seu endereço");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.postmon.com.br/v1/cep/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetEnderecoApi getEnderecoService = retrofit.create(GetEnderecoApi.class);
        Call<EnderecoCep> call = getEnderecoService.getCep(cep);

        call.enqueue(new Callback<EnderecoCep>() {
            @Override
            public void onResponse(Call<EnderecoCep> call, Response<EnderecoCep> response) {
                if (response.isSuccessful()) {
                    EnderecoCep enderecoResponse = response.body();
                    ll_campos.setVisibility(View.VISIBLE);
                    edt_rua.setText(enderecoResponse.getLogradouro());
                    edt_bairro.setText(enderecoResponse.getBairro());
                    edt_cidade.setText(enderecoResponse.getCidade());
                    edt_estado.setText(enderecoResponse.getEstado());
                    layout.setRefreshing(false);
                    progressDoalog.dismiss();
                }else{
                    layout.setRefreshing(false);
                    progressDoalog.dismiss();
                    ll_campos.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<EnderecoCep> call, Throwable t) {
                Toast.makeText(Activity_buscar_end_cep.this,
                        "Não foi possível buscar seu endereço",
                        Toast.LENGTH_SHORT).show();
                progressDoalog.dismiss();
                layout.setRefreshing(false);
                ll_campos.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }
    private void alertaDialog(String titulo, String mensagem) {
        AlertDialog alerta = new AlertDialog.Builder(this).create();
        alerta.setTitle(titulo);
        alerta.setMessage(mensagem);
        alerta.show();
    }

    public class EnderecoCep{
        private String bairro;
        private String cidade;
        private String estado;
        private String logradouro;

        public EnderecoCep(String bairro, String cidade, String estado, String logradouro) {
            this.bairro = bairro;
            this.cidade = cidade;
            this.estado = estado;
            this.logradouro = logradouro;
        }

        public String getBairro() {
            return bairro;
        }

        public void setBairro(String bairro) {
            this.bairro = bairro;
        }

        public String getCidade() {
            return cidade;
        }

        public void setCidade(String cidade) {
            this.cidade = cidade;
        }

        public String getEstado() {
            return estado;
        }

        public void setEstado(String estado) {
            this.estado = estado;
        }

        public String getLogradouro() {
            return logradouro;
        }

        public void setLogradouro(String logradouro) {
            this.logradouro = logradouro;
        }
    }
}
