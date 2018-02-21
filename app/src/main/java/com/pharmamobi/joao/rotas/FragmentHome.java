package com.pharmamobi.joao.rotas;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pharmamobi.joao.rotas.Domain.WebApiUrl;
import com.pharmamobi.joao.rotas.Interfaces.ClientesApi;
import com.pharmamobi.joao.rotas.model.MEntregas;
import com.pharmamobi.joao.rotas.model.OrcamentoAux;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FragmentHome extends Fragment {
    private RelativeLayout rl_orcamentos_dash,rl_entregas_dash;
    private WebApiUrl webApi = new WebApiUrl();
    private String URL_BASE = webApi.GetUrlBase();
    private int qtdOrcamentos=0,qtdEntregas=0;
    private TextView txtQtdOrc,txtQtdEntregas,txt_dash_orc,txt_dash_entregas;
    private Typeface R_light;

    public FragmentHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_home, container, false);
        rl_orcamentos_dash = (RelativeLayout)v.findViewById(R.id.rl_orcamentos_dash);
        rl_entregas_dash = (RelativeLayout)v.findViewById(R.id.rl_dash_entregas);
        txtQtdEntregas = (TextView)v.findViewById(R.id.txt_qdt_ent);
        txtQtdOrc = (TextView)v.findViewById(R.id.txt_qdt_orc);
        txt_dash_entregas = (TextView)v.findViewById(R.id.txt_entregas_dash);
        txt_dash_orc = (TextView)v.findViewById(R.id.txt_orcamento_dash);

        R_light = Typeface.createFromAsset(getActivity().getAssets(),"Roboto-Light.ttf");
        txtQtdOrc.setTypeface(R_light);
        txtQtdEntregas.setTypeface(R_light);
        txt_dash_orc.setTypeface(R_light);
        txt_dash_entregas.setTypeface(R_light);


        getOrcamentos();
        getEntregas();

        rl_orcamentos_dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getActivity(),MenuPrincipal.class);
                it.putExtra("fragment","orcamentos");
                startActivity(it);
            }
        });
        rl_entregas_dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getActivity(),MenuPrincipal.class);
                it.putExtra("fragment","entregas");
                startActivity(it);
            }
        });

        return v;

    }

    private void getOrcamentos() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE + "Orcamento/GetOrcamentos/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ClientesApi getOrcamentoService = retrofit.create(ClientesApi.class);
        Call<List<OrcamentoAux>> call = getOrcamentoService.getOrcamentosCliente("2"); //id do cliente

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(getActivity());
        progressDoalog.setTitle("Bem vindo");
        progressDoalog.setMessage("Carregando informações");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<List<OrcamentoAux>>() {
            @Override
            public void onResponse(Call<List<OrcamentoAux>> call, Response<List<OrcamentoAux>> response) {
                if (response.isSuccessful()) {
                    ArrayList<OrcamentoAux> orcamentoResponse = (ArrayList<OrcamentoAux>) response.body();
                    for (int x = 0; x < orcamentoResponse.size(); x++) {
                        qtdOrcamentos ++;
                        Log.d("qtd Orçamento",String.valueOf(qtdOrcamentos));
                    }
                    txtQtdOrc.setText("0"+ String.valueOf(qtdOrcamentos));
                    progressDoalog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<OrcamentoAux>> call, Throwable t) {
                progressDoalog.dismiss();
            }
        });
    }

    private void getEntregas() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE + "Entregas/GetEntregasIdCliente/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ClientesApi getEntregaService = retrofit.create(ClientesApi.class);

        Call<List<MEntregas>> call = getEntregaService.getEntregaIdCliente("2");

        call.enqueue(new Callback<List<MEntregas>>() {
            @Override
            public void onResponse(Call<List<MEntregas>> call, Response<List<MEntregas>> response) {
                if (response.isSuccessful()) {
                    ArrayList<MEntregas> entregaResponse = (ArrayList<MEntregas>) response.body();
                    if (response.body() != null) {
                        for (int x = 0; x < entregaResponse.size(); x++) {
                            qtdEntregas ++;
                            Log.d("qtd entregas",String.valueOf(qtdEntregas));
                        }
                        txtQtdEntregas.setText("0"+String.valueOf(qtdEntregas));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<MEntregas>> call, Throwable t) {

            }
        });
    }
}
