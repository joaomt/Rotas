package com.pharmamobi.joao.rotas.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pharmamobi.joao.rotas.DateActivity;
import com.pharmamobi.joao.rotas.Domain.WebApiUrl;
import com.pharmamobi.joao.rotas.Interfaces.ClientesApi;
import com.pharmamobi.joao.rotas.R;
import com.pharmamobi.joao.rotas.model.MEntregas;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.pharmamobi.joao.rotas.DateActivity.dataReagendada;
import static com.pharmamobi.joao.rotas.activity.Activity_detalhes_entrega.entrega;


public class Activity_reagendar_entrega extends AppCompatActivity {
    private Toolbar tb_avaliar_entrega;
    private LinearLayout ll_reagendar,ll_retirar;
    private EditText edt_rb_1, edt_rb_2, edt_rb_3;
    private RadioButton rb1, rb2, rb3;
    private TextView txt_qtd_entregas_faltantes;
    private Button btn_reagendar, btn_retirar_loja;
    private ImageButton btn_salvar_alt, btn_cancel_alt, btn_reagendar_calendario;
    private TextView txt_data_agendada,txt_data_agendada_info;
    private DatePickerDialog.OnDateSetListener listener;
    private WebApiUrl webApi = new WebApiUrl();
    private String URL_BASE = webApi.GetUrlBase();

    protected void onCreate(final Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_reagendar_entrega);

        GetTentativasEntrega(entrega.getCOD_ENTREGA());

        ll_reagendar = (LinearLayout)findViewById(R.id.ll_reagendar);
        ll_retirar = (LinearLayout)findViewById(R.id.ll_retirar);
        tb_avaliar_entrega = (Toolbar) findViewById(R.id.tb_avaliar_entrega);
        tb_avaliar_entrega.setTitle("Reagendar Entrega");
        setSupportActionBar(tb_avaliar_entrega);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt_qtd_entregas_faltantes = (TextView) findViewById(R.id.txt_qtd_entregas_faltantes);
        btn_salvar_alt = (ImageButton) findViewById(R.id.btn_salvar_alt);
        btn_cancel_alt = (ImageButton) findViewById(R.id.btn_cancel_alt);
        btn_reagendar = (Button) findViewById(R.id.btn_reagendar);
        btn_retirar_loja = (Button) findViewById(R.id.btn_retirar_loja);
        btn_reagendar_calendario = (ImageButton) findViewById(R.id.btn_reagendar_calendario);

        rb1 = (RadioButton) findViewById(R.id.rb_1);
        rb2 = (RadioButton) findViewById(R.id.rb_2);
        rb3 = (RadioButton) findViewById(R.id.rb_3);

        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verified_ck(view);
            }
        });
        rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verified_ck(view);
            }
        });
        rb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verified_ck(view);
            }
        });


        btn_reagendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reagendar_layout(view);
            }
        });
        btn_retirar_loja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reagendar_layout(view);
            }
        });

        btn_cancel_alt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelar_alteracoes();
            }
        });
        btn_salvar_alt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarAlt();
            }
        });
        btn_reagendar_calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DialogFragment nFrag = new DateActivity();
//                nFrag.show(getSupportFragmentManager(), "Reagendar");
                DateActivity fragment = new DateActivity();
                fragment.setListener(listener);
                fragment.show(getSupportFragmentManager(), "Choose booking date");

            }
        });

    }

    private void salvarAlt() {
        String local;
            if(rb1.isChecked() || rb2.isChecked() || rb3.isChecked()) {
                local = "LOJA";
            }else{
                local = "CASA";
            final ProgressDialog progressDoalog;
            progressDoalog = new ProgressDialog(this);
            progressDoalog.setMessage("Aguarde......");
            progressDoalog.setTitle("Salvando Alterações");
            progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            // show it
            progressDoalog.show();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL_BASE + "Entregas/ReagendarEntrega/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ClientesApi entregaResource = retrofit.create(ClientesApi.class);
            MEntregas new_entrega = new MEntregas(
                    entrega.getCOD_ENTREGA(),
                    entrega.getCOD_ENDERECO(),
                    entrega.getOBSERVACAO(),
                    entrega.getSITUACAO_PAGAMENTO(),
                    entrega.getSTATUS_ENTREGA(),
                    dataReagendada,
                    entrega.getCOD_CLIENTE(),
                    entrega.getCOD_ORCAMENTO(),
                    entrega.getNUM_COMPRO_VENDA(),
                    entrega.getVALOR_ENTREGA(),
                    local
            );
            Call<MEntregas> call = entregaResource.ReagendarEntrega(new_entrega);
            call.enqueue(new Callback<MEntregas>() {
                @Override
                public void onResponse(Call<MEntregas> call, Response<MEntregas> response) {
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
                public void onFailure(Call<MEntregas> call, Throwable t) {
                    Toast.makeText(Activity_reagendar_entrega.this,
                            "Não foi possível atulizar sua entrega",
                            Toast.LENGTH_SHORT).show();

                    progressDoalog.dismiss();
                }
            });
        }
    }

    private void GetTentativasEntrega(int id) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE + "Entregas/GetTentativasEntrega/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ClientesApi getEntregaService = retrofit.create(ClientesApi.class);
        Call<String> call = getEntregaService.GetTentativasEntrega(String.valueOf(id));

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Activity_reagendar_entrega.this);
        //progressDoalog.setMax(100);
        progressDoalog.setMessage("Aguarde......");
        progressDoalog.setTitle("Procurando Entrega");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<String>() {
                         @Override
                         public void onResponse(Call<String> call, Response<String> response) {
                             if (response.isSuccessful()) {
                                 String entregaResponse = response.body();
                                 if (response.body() != null) {
                                     txt_qtd_entregas_faltantes.setText(entregaResponse);
                                     progressDoalog.dismiss();
                                 } else {
                                     // Get response errorBody
                                     progressDoalog.dismiss();
                                     Log.d("Response errorBody", String.valueOf(response.errorBody()));
                                 }
                             }
                         }

                         @Override
                         public void onFailure(Call<String> call, Throwable t) {
                             progressDoalog.dismiss();
                         }
                     }

        );
    }


    private void reagendar_layout(View view) {
        if (view.getId() == R.id.btn_retirar_loja) {
            ll_retirar.setVisibility(View.VISIBLE);
            ll_reagendar.setVisibility(View.INVISIBLE);
            btn_retirar_loja.setAlpha((float) 1.0);
            btn_reagendar.setAlpha((float) 0.3);
            btn_retirar_loja.setBackgroundResource(R.drawable.round_corner_button_left);
            btn_retirar_loja.setTextColor(Color.WHITE);
            btn_reagendar.setTextColor(Color.BLUE);
            btn_reagendar.setBackgroundResource(R.drawable.round_corner_button_off_left);
        }
        if (view.getId() == R.id.btn_reagendar) {
            ll_reagendar.setVisibility(View.VISIBLE);
            ll_retirar.setVisibility(View.INVISIBLE);
            btn_reagendar.setAlpha((float) 1.0);
            btn_retirar_loja.setAlpha((float) 0.3);
            btn_reagendar.setBackgroundResource(R.drawable.round_corner_button);
            btn_reagendar.setTextColor(Color.WHITE);
            btn_retirar_loja.setTextColor(Color.BLUE);
            btn_retirar_loja.setBackgroundResource(R.drawable.round_corner_button_off);
        }
    }

    private void verified_ck(View v) {
        if (v.getId() == R.id.rb_1) {
            if (rb1.isChecked()) {
                rb2.setChecked(false);
                rb3.setChecked(false);
                rb1.setAlpha((float) 1.0);
                rb2.setAlpha((float) 0.3);
                rb3.setAlpha((float) 0.3);
                rb1.setTextColor(Color.RED);
                rb2.setTextColor(Color.GRAY);
                rb3.setTextColor(Color.GRAY);
                return;
            }
        }
        if (v.getId() == R.id.rb_2) {
            if (rb2.isChecked()) {
                rb1.setChecked(false);
                rb3.setChecked(false);
                rb1.setAlpha((float) 0.3);
                rb2.setAlpha((float) 1.0);
                rb3.setAlpha((float) 0.3);
                rb1.setTextColor(Color.GRAY);
                rb2.setTextColor(Color.RED);
                rb3.setTextColor(Color.GRAY);
                return;
            }
        }
        if (v.getId() == R.id.rb_3) {
            if (rb3.isChecked()) {
                rb2.setChecked(false);
                rb1.setChecked(false);
                rb1.setAlpha((float) 0.3);
                rb2.setAlpha((float) 0.3);
                rb3.setAlpha((float) 1.0);
                rb1.setTextColor(Color.GRAY);
                rb2.setTextColor(Color.GRAY);
                rb3.setTextColor(Color.RED);
                return;
            }
        }
    }

    private void cancelar_alteracoes() {
        rb1.setTextColor(Color.BLACK);
        rb2.setTextColor(Color.BLACK);
        rb3.setTextColor(Color.BLACK);
        rb1.setChecked(false);
        rb2.setChecked(false);
        rb3.setChecked(false);
        rb1.setAlpha((float) 1.0);
        rb2.setAlpha((float) 1.0);
        rb3.setAlpha((float) 1.0);
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
