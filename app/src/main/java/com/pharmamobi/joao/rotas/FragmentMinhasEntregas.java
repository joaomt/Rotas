package com.pharmamobi.joao.rotas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.pharmamobi.joao.rotas.Adapter.MEntregasAdapter;
import com.pharmamobi.joao.rotas.Domain.WebApiUrl;
import com.pharmamobi.joao.rotas.Interfaces.ClientesApi;
import com.pharmamobi.joao.rotas.Interfaces.RecyclerViewOnclickListenerHack;
import com.pharmamobi.joao.rotas.activity.Activity_avaliar_entrega;
import com.pharmamobi.joao.rotas.activity.Activity_detalhes_entrega;
import com.pharmamobi.joao.rotas.activity.Activity_novo_orc;
import com.pharmamobi.joao.rotas.model.MEntregas;

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
public class FragmentMinhasEntregas extends Fragment implements RecyclerViewOnclickListenerHack {

    private RecyclerView mRecyclerView;
    private List<MEntregas> mList = new ArrayList<>();
    private EditText edt_buscar_entrega;
    private Button btn_buscar, btn_orcamento;
    private String idCliente = "2";
    private RelativeLayout rl_bad_entregas;
    private WebApiUrl webApi = new WebApiUrl();
    private String URL_BASE = webApi.GetUrlBase();
    private PullRefreshLayout refresh_layout;
    private int codAux = 0;
    private int x = 0;

    public FragmentMinhasEntregas() {
    }

    public class CliEntrega {
        private String COD_CLI;
        private String COMP_ENTREGA;

        public CliEntrega(String COD_CLI, String COMP_ENTREGA) {
            this.COD_CLI = COD_CLI;
            this.COMP_ENTREGA = COMP_ENTREGA;
        }

        public String getCOD_CLI() {
            return COD_CLI;
        }

        public void setCOD_CLI(String COD_CLI) {
            this.COD_CLI = COD_CLI;
        }

        public String getCOMP_ENTREGA() {
            return COMP_ENTREGA;
        }

        public void setCOMP_ENTREGA(String COMP_ENTREGA) {
            this.COMP_ENTREGA = COMP_ENTREGA;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_minhas_entregas, container, false);
        rl_bad_entregas = (RelativeLayout) view.findViewById(R.id.rl_bad_entregas);
        btn_orcamento = (Button) view.findViewById(R.id.btn_orcamento);
        getEntregas();

        refresh_layout = (PullRefreshLayout) view.findViewById(R.id.refresh_minhas_entregas);
        refresh_layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getEntregas();
            }
        });
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rc_list);
        mRecyclerView.setHasFixedSize(true);


        btn_orcamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getActivity(), Activity_novo_orc.class);
                startActivity(it);
            }
        });

        btn_buscar = (Button) view.findViewById(R.id.btn_buscar);

        edt_buscar_entrega = (EditText) view.findViewById(R.id.edt_buscar_entrega);


        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String CV = (edt_buscar_entrega.getText().toString());
                getEntregaCV(CV);
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edt_buscar_entrega.getWindowToken(), 0);

            }
        });

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        return view;
    }

    private void getEntregas() {
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(getActivity());
        //progressDoalog.setMax(100);
        progressDoalog.setMessage("Aguarde um instante......");
        progressDoalog.setTitle("Carregando suas entregas");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE + "Entregas/GetEntregasIdCliente/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ClientesApi getEntregaService = retrofit.create(ClientesApi.class);

        Call<List<MEntregas>> call = getEntregaService.getEntregaIdCliente(idCliente);

        call.enqueue(new Callback<List<MEntregas>>() {
            @Override
            public void onResponse(Call<List<MEntregas>> call, Response<List<MEntregas>> response) {
                if (response.isSuccessful()) {
                    ArrayList<MEntregas> entregaResponse = (ArrayList<MEntregas>) response.body();
                    mList.clear();
                    if (response.body() != null) {
                        for (int x = 0; x < entregaResponse.size(); x++) {
                            MEntregas entrega = new MEntregas(
                                    entregaResponse.get(x).getCOD_ENTREGA(),
                                    entregaResponse.get(x).getCOD_ENDERECO(),
                                    entregaResponse.get(x).getOBSERVACAO(),
                                    entregaResponse.get(x).getSITUACAO_PAGAMENTO(),
                                    entregaResponse.get(x).getSTATUS_ENTREGA(),
                                    entregaResponse.get(x).getDATA_RELACAO(),
                                    entregaResponse.get(x).getCOD_CLIENTE(),
                                    entregaResponse.get(x).getCOD_ORCAMENTO(),
                                    entregaResponse.get(x).getNUM_COMPRO_VENDA(),
                                    entregaResponse.get(x).getVALOR_ENTREGA(),
                                    entregaResponse.get(x).getLOCAL_RETIRADA());
                            rl_bad_entregas.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);
                            if (!mList.contains(entrega.getCOD_ENTREGA())) {
                                mList.add(entrega);
                            }
                        }
                        MEntregasAdapter adapter = new MEntregasAdapter(getActivity(), mList);
                        adapter.setRecyclerViewOnclickListenerHack(FragmentMinhasEntregas.this);
                        mRecyclerView.setAdapter(adapter);
                        progressDoalog.dismiss();
                        refresh_layout.setRefreshing(false);
                    } else {
                        rl_bad_entregas.setVisibility(View.VISIBLE);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<MEntregas>> call, Throwable t) {
                rl_bad_entregas.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(),
                        "Não foi possível carregar suas entregas.",
                        Toast.LENGTH_SHORT).show();
                refresh_layout.setRefreshing(false);
                progressDoalog.dismiss();
            }
        });
    }

    private void getEntregaCV(String CV) {
        edt_buscar_entrega.setText("");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE + "Entregas/GetEntregaComprovante/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CliEntrega cliEntrega = new CliEntrega("2", CV);
        ClientesApi getEntregaService = retrofit.create(ClientesApi.class);
        Call<MEntregas> call = getEntregaService.getEntregaCV(cliEntrega);

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(getActivity());
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
                                     MEntregas entrega = new MEntregas(
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
                                     rl_bad_entregas.setVisibility(View.GONE);

                                     if(mList.contains(entrega)){
                                         Toast.makeText(getActivity(),
                                                 "Você já resgatou essa entrega",
                                                 Toast.LENGTH_SHORT).show();
                                     }else{mList.add(entrega);}

                                 }
                                 MEntregasAdapter adapter = new MEntregasAdapter(getActivity(), mList);
                                 adapter.setRecyclerViewOnclickListenerHack(FragmentMinhasEntregas.this);
                                 mRecyclerView.setAdapter(adapter);
                                 progressDoalog.dismiss();
                             } else {
                                 // Get response errorBody
                                 progressDoalog.dismiss();
                                 Log.d("Response errorBody", String.valueOf(response.errorBody()));
                             }
                         }

                         @Override
                         public void onFailure(Call<MEntregas> call, Throwable t) {
                             rl_bad_entregas.setVisibility(View.VISIBLE);
                             mRecyclerView.setVisibility(View.GONE);
                             Toast.makeText(getActivity(),
                                     "Não encontramos sua entrega",
                                     Toast.LENGTH_SHORT).show();
                             progressDoalog.dismiss();
                         }
                     }

        );
    }

    @Override
    public void OnClickListener(View v, int position) {

        if (v.getId() == R.id.btn_mais_detalhes) {
            Intent it = new Intent(getActivity(), Activity_detalhes_entrega.class);
            it.putExtra("cv_entrega", String.valueOf(mList.get(position).getNUM_COMPRO_VENDA()));
            startActivity(it);
        }
        if (v.getId() == R.id.btn_avalie_entrega) {
            Intent it = new Intent(getActivity(), Activity_avaliar_entrega.class);
            startActivity(it);
        }

//        MEntregasAdapter adapter = new MEntregasAdapter(getActivity(), mList);
//        adapter.removeListItem(position);
    }

    private void alertaDialog(String titulo, String mensagem) {
        AlertDialog alerta = new AlertDialog.Builder(getActivity()).create();
        alerta.setTitle(titulo);
        alerta.setMessage(mensagem);
        alerta.setIcon(R.mipmap.ic_atencao);
        alerta.show();
    }
}
