package com.pharmamobi.joao.rotas;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.pharmamobi.joao.rotas.Adapter.OrcamentoAdapter;
import com.pharmamobi.joao.rotas.Domain.WebApiUrl;
import com.pharmamobi.joao.rotas.Interfaces.ClientesApi;
import com.pharmamobi.joao.rotas.activity.Activity_detalhes_Orcamento;
import com.pharmamobi.joao.rotas.activity.Activity_novo_orc;
import com.pharmamobi.joao.rotas.model.OrcamentoAux;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOrcamentos extends Fragment {

    private Button btn_novo_orc;
    private PullRefreshLayout refresh_layout;
    public static ArrayList<OrcamentoAux> ListOrcamento = new ArrayList<>();
    private ListView listViewOrcamentos;
    private WebApiUrl webApi = new WebApiUrl();
    private String URL_BASE = webApi.GetUrlBase();
    private RelativeLayout rl_orcamentos_bad;
    private View view;
    private TextView txt_bad;
    private OrcamentoAux orcamentos;


    public FragmentOrcamentos() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_orcamentos, container, false);
        Typeface R_medium = Typeface.createFromAsset(getActivity().getAssets(),"Roboto-Medium.ttf");

        rl_orcamentos_bad = (RelativeLayout)view.findViewById(R.id.rl_orcamentos_bad);
        txt_bad = (TextView)view.findViewById(R.id.txt_bad);
        txt_bad.setTypeface(R_medium);

        btn_novo_orc = (Button)view.findViewById(R.id.btn_novo_orc);

        listViewOrcamentos = (ListView)view.findViewById(R.id.lv_orcamentos);

        refresh_layout = (PullRefreshLayout)view.findViewById(R.id.refresh_orcamentos);
        refresh_layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ListOrcamento.clear();
                getOrcamentos();
            }
        });

        getOrcamentos();

        btn_novo_orc.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                Intent it = new Intent(getActivity(),Activity_novo_orc.class);
                startActivity(it);
            }
        });

        listViewOrcamentos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                Intent it = new Intent(getActivity(), Activity_detalhes_Orcamento.class);
                it.putExtra("cod_orcamento",String.valueOf(ListOrcamento.get(position).getCOD_ORCAMENTO()));
                startActivity(it);
                //EnderecoDef.COD_CLIENTE = ListEndereco.get(position).getCOD_CLIENTE();
            }
        });
        // Inflate the layout for this fragment
        return view;
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
        progressDoalog.setMessage("Aguarde......");
        progressDoalog.setTitle("Atualizando seus Orçamentos");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<List<OrcamentoAux>>() {
            @Override
            public void onResponse(Call<List<OrcamentoAux>> call, Response<List<OrcamentoAux>> response) {
                if (response.isSuccessful()) {
                    ArrayList<OrcamentoAux> orcamentoResponse = (ArrayList<OrcamentoAux>) response.body();
                    ListOrcamento.clear();
                    for (int x = 0; x < orcamentoResponse.size(); x++) {
                        orcamentos = new OrcamentoAux(
                                orcamentoResponse.get(x).getCOD_ORCAMENTO(),
                                orcamentoResponse.get(x).getCOD_CLIENTE(),
                                orcamentoResponse.get(x).getCOD_EMPRESA(),
                                orcamentoResponse.get(x).getTITULO(),
                                orcamentoResponse.get(x).getANEXO(),
                                orcamentoResponse.get(x).getOBSERVACAO(),
                                orcamentoResponse.get(x).getSTATUS_PEDIDO(),
                                orcamentoResponse.get(x).getDATA_PEDIDO(),
                                orcamentoResponse.get(x).getVALOR_TOTAL(),
                                orcamentoResponse.get(x).getLOCAL_RETIRADA(),
                                orcamentoResponse.get(x).getCEP(),
                                orcamentoResponse.get(x).getRUA(),
                                orcamentoResponse.get(x).getNUMERO(),
                                orcamentoResponse.get(x).getCOMPLEMENTO(),
                                orcamentoResponse.get(x).getBAIRRO(),
                                orcamentoResponse.get(x).getCIDADE(),
                                orcamentoResponse.get(x).getESTADO()
                        );
                        ListOrcamento.add(orcamentos);
                        refresh_layout.setRefreshing(false);
                    }
                    if(ListOrcamento.size() != 0) {
                        rl_orcamentos_bad.setVisibility(View.GONE);
                    }else{
                        rl_orcamentos_bad.setVisibility(View.VISIBLE);
                    }
                    Adapter adapter = new OrcamentoAdapter(ListOrcamento, getActivity());
                    listViewOrcamentos.setAdapter((ListAdapter) adapter);
                    refresh_layout.setRefreshing(false);
                    progressDoalog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<OrcamentoAux>> call, Throwable t) {
                if(ListOrcamento.size() == 0) {
                    rl_orcamentos_bad.setVisibility(View.GONE);
                }else{
                    rl_orcamentos_bad.setVisibility(View.VISIBLE);
                }
                Toast.makeText(getActivity(),
                        "Não foi possível realizar a requisição",
                        Toast.LENGTH_SHORT).show();
                progressDoalog.dismiss();
                refresh_layout.setRefreshing(false);
            }
        });
    }

}
