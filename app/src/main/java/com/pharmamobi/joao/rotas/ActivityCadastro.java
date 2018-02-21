package com.pharmamobi.joao.rotas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pharmamobi.joao.rotas.Domain.WebApiUrl;
import com.pharmamobi.joao.rotas.Interfaces.ClientesApi;
import com.pharmamobi.joao.rotas.model.Clientes;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static junit.framework.Assert.assertTrue;

public class ActivityCadastro extends AppCompatActivity implements View.OnClickListener {
    private Toolbar tb_cadastro;
    private TextView txt_email, txt_nome, txt_cpf, txt_senha, txt_repita_senha;
    private TextView txt_info_email, txt_info_nome, txt_info_cpf, txt_info_senha, txt_info_repita_senha;
    private EditText edt_email, edt_nome, edt_cpf, edt_senha, edt_rep_senha;
    private int red, green;
    private Button btn_cadastrar;
    private WebApiUrl webApi = new WebApiUrl();
    private String URL_BASE = webApi.GetUrlBase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //transitions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Explode trans1 = new Explode();
            trans1.setDuration(1000);
            Slide trans2 = new Slide();
            trans2.setDuration(50);

            getWindow().setEnterTransition(trans1);
            getWindow().setReturnTransition(null);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        tb_cadastro = (Toolbar) findViewById(R.id.tb_cadastro);
        tb_cadastro.setTitle("Cadastre-se");
        setSupportActionBar(tb_cadastro);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        red = Color.parseColor("#ec1515");
        green = Color.parseColor("#1ac104");

        //Edit_Text
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_nome = (EditText) findViewById(R.id.edt_nome);
        edt_cpf = (EditText) findViewById(R.id.edt_cpf);
        edt_senha = (EditText) findViewById(R.id.edt_senha);
        edt_rep_senha = (EditText) findViewById(R.id.edt_rept_senha);

        //Text View
        txt_email = (TextView) findViewById(R.id.txt_email);
        txt_nome = (TextView) findViewById(R.id.txt_nome);
        txt_cpf = (TextView) findViewById(R.id.txt_cpf);
        txt_senha = (TextView) findViewById(R.id.txt_senha);
        txt_repita_senha = (TextView) findViewById(R.id.txt_repita_senha);

        //Text View infos
        txt_info_email = (TextView) findViewById(R.id.txt_info_email);
        txt_info_nome = (TextView) findViewById(R.id.txt_info_nome);
        txt_info_cpf = (TextView) findViewById(R.id.txt_info_cpf);
        txt_info_senha = (TextView) findViewById(R.id.txt_info_senha);
        txt_info_repita_senha = (TextView) findViewById(R.id.txt_info_repita_senha);

        //botão
        btn_cadastrar = (Button) findViewById(R.id.btn_cadastrar);

        edt_email.addTextChangedListener(new TextWatcher() {
            // depois de terminar de escrever
            public void afterTextChanged(Editable s) {
            }

            // antes da ultima edição
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            //edição em tempo real
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count < 1) {
                    txt_info_email.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_error, 0, 0, 0);
                    txt_info_email.setText("Digite um email válido.");
                    txt_info_email.setTextColor(red);
                    txt_email.setVisibility(View.INVISIBLE);
                } else {
                    txt_email.setVisibility(View.VISIBLE);
                    txt_info_email.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_yes, 0, 0, 0);
                    txt_info_email.setText("Ok.");
                    txt_info_email.setTextColor(green);
                }
            }
        });
        edt_nome.addTextChangedListener(new TextWatcher() {
            // depois de terminar de escrever
            public void afterTextChanged(Editable s) {
            }

            // antes da ultima edição
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            //edição em tempo real
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count < 1) {
                    txt_info_nome.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_error, 0, 0, 0);
                    txt_info_nome.setText("Digite seu nome.");
                    txt_info_nome.setTextColor(red);
                    txt_nome.setVisibility(View.INVISIBLE);
                } else {
                    txt_nome.setVisibility(View.VISIBLE);
                    txt_info_nome.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_yes, 0, 0, 0);
                    txt_info_nome.setText("Ok.");
                    txt_info_nome.setTextColor(green);
                }
            }
        });
        edt_cpf.addTextChangedListener(new TextWatcher() {
            // depois de terminar de escrever
            public void afterTextChanged(Editable s) {
            }

            // antes da ultima edição
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            //edição em tempo real
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edt_cpf.getText().toString().length() < 11) {
                    txt_info_cpf.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_error, 0, 0, 0);
                    txt_info_cpf.setText("CPF inválido.");
                    txt_info_cpf.setTextColor(red);
                    txt_cpf.setVisibility(View.INVISIBLE);
                } else {
                    txt_cpf.setVisibility(View.VISIBLE);
                    txt_info_cpf.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_yes, 0, 0, 0);
                    txt_info_cpf.setText("Ok");
                    txt_info_cpf.setTextColor(green);
                }
            }
        });

        edt_senha.addTextChangedListener(new TextWatcher() {
            // depois de terminar de escrever
            public void afterTextChanged(Editable s) {
            }

            // antes da ultima edição
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            //edição em tempo real
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edt_senha.getText().length() < 5) {
                    txt_info_senha.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_error, 0, 0, 0);
                    txt_info_senha.setText("Sua senha precisa ter no mínimo 5 caracteres.");
                    txt_info_senha.setTextColor(red);
                    txt_senha.setVisibility(View.VISIBLE);
                    if (edt_senha.getText().length() < 1) {
                        txt_senha.setVisibility(View.INVISIBLE);
                    }
                } else {
                    txt_senha.setVisibility(View.VISIBLE);
                    txt_info_senha.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_yes, 0, 0, 0);
                    txt_info_senha.setText("Ok.");
                    txt_info_senha.setTextColor(green);
                }
            }
        });


        edt_rep_senha.addTextChangedListener(new TextWatcher() {
            // depois de terminar de escrever
            public void afterTextChanged(Editable s) {
            }

            // antes da ultima edição
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            //edição em tempo real
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edt_rep_senha.getText().length() < 1) {
                    txt_info_repita_senha.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_error, 0, 0, 0);
                    txt_info_repita_senha.setText("Repita a senha digitada anteriormente.");
                    txt_info_repita_senha.setTextColor(red);
                    txt_repita_senha.setVisibility(View.INVISIBLE);
                }
                if (edt_rep_senha.getText().length() > 0) {
                    txt_repita_senha.setVisibility(View.VISIBLE);
                    if (edt_rep_senha.getText().toString().equals(edt_senha.getText().toString())) {
                        txt_info_repita_senha.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_yes, 0, 0, 0);
                        txt_info_repita_senha.setText("Ok.");
                        txt_info_repita_senha.setTextColor(green);
                    } else {
                        txt_info_repita_senha.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_error, 0, 0, 0);
                        txt_info_repita_senha.setText("Suas senhas não estão iguais. Por favor digite novamente.");
                        txt_info_repita_senha.setTextColor(red);
                    }
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        String email = edt_email.getText().toString();
        String nome = edt_nome.getText().toString();
        String cpf = edt_cpf.getText().toString();
        String senha = edt_senha.getText().toString();
        String rep_senha = edt_rep_senha.getText().toString();
        if (email.length() > 0 && nome.length() > 0 && cpf.length() > 11 && senha.length() > 4 && rep_senha.length() > 4) {
            try {
                cadastrarUsuario();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            AlertDialog alerta = new AlertDialog.Builder(this).create();
            alerta.setTitle("Atenção");
            alerta.setMessage("Existem campos que não foram preenchidos.");
            alerta.setIcon(R.mipmap.ic_atencao);
            alerta.closeOptionsMenu();
            alerta.show();
        }
    }

    private void cadastrarUsuario() throws IOException {
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(this);
        progressDoalog.setMessage("Aguarde......");
        progressDoalog.setTitle("Salvando cadastro");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE + "Clientes/CadastrarCliente/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ClientesApi clienteResource = retrofit.create(ClientesApi.class);
        Clientes cliente = new Clientes(
                edt_email.getText().toString(),
                edt_nome.getText().toString(),
                edt_cpf.getText().toString(),
                edt_senha.getText().toString()
                );
        Call <Clientes> call = clienteResource.postCliente(cliente);
        call.enqueue(new Callback<Clientes>() {
            @Override
            public void onResponse(Call<Clientes> call, Response<Clientes> response) {
                if(response.isSuccessful())
                {
                    alertaDialog("OK","Cadastro realizado com sucesso :)");
                    Log.d("response code",String.valueOf(response.code()));
                    Log.d("response raw",String.valueOf(response.raw()));
                    Log.d("response body",String.valueOf(response.body().getID()));
                    Log.d("response raw message",response.raw().message());
                    progressDoalog.dismiss();
                    Intent it = new Intent(ActivityCadastro.this,MenuPrincipal.class);
                    it.putExtra("id",response.body().getID());
                    startActivity(it);
                }
            }

            @Override
            public void onFailure(Call<Clientes> call, Throwable t) {
                alertaDialog("Falha","Alguma coisa deu errado ):");
                progressDoalog.dismiss();
            }
        });
    }

    private void alertaDialog(String titulo, String mensagem) {
        AlertDialog alerta = new AlertDialog.Builder(this).create();
        alerta.setTitle(titulo);
        alerta.setMessage(mensagem);
        alerta.show();
    }
}
