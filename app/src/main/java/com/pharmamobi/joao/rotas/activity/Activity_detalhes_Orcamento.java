package com.pharmamobi.joao.rotas.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pharmamobi.joao.rotas.Adapter.ItensOrcamentoAdapter;
import com.pharmamobi.joao.rotas.Domain.ConverterImage;
import com.pharmamobi.joao.rotas.Domain.WebApiUrl;
import com.pharmamobi.joao.rotas.FragmentOrcamentos;
import com.pharmamobi.joao.rotas.Interfaces.ClientesApi;
import com.pharmamobi.joao.rotas.R;
import com.pharmamobi.joao.rotas.model.OrcamentoAux;
import com.pharmamobi.joao.rotas.model.Orcamentos;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.attr.id;
import static com.pharmamobi.joao.rotas.model.Orcamentos.*;

public class Activity_detalhes_Orcamento extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onResume() {
        super.onResume();
        getOrcamento(cod_orcamento);
        getItensOrcamentos(cod_orcamento);
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        getOrcamento(cod_orcamento);
        getItensOrcamentos(cod_orcamento);
        }

    private ConverterImage converterImage = new ConverterImage();
    private Toolbar tb_detalhes_entrega;
    private WebApiUrl webApi = new WebApiUrl();
    private String URL_BASE = webApi.GetUrlBase();
    private int positionItemList;
    private ArrayList<Orcamentos>listItensOrcamentos = new ArrayList<>();
    private ListView lv_itens_orcamento;
    private TextView txt_pedido_tb,txt_status_pedido,txt_valor_total,txt_endereco,txt_exibir_anexo,txt_obs_itens;
    private CircleImageView img_status_orcamento,img_endereco_recebimento;
    private DisplayMetrics metrics;
    private Button btn_orcamento_aprovado,btn_orcamento_cancelado,btn_aprovar_orc,btn_cancelar_orc;
    private ImageView btn_fechar_orc;
    private LinearLayout ll_aprovar_orc;
    private int width = 0;
    private OrcamentoAux orcamento;
    private ProgressDialog progressDialog;
    private String cod_orcamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes__orcamento);
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;

        tb_detalhes_entrega = (Toolbar)findViewById(R.id.tb_detalhes_orcamento);
        setSupportActionBar(tb_detalhes_entrega);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lv_itens_orcamento = (ListView)findViewById(R.id.lv_itens);
        ll_aprovar_orc = (LinearLayout)findViewById(R.id.ll_aprovar_orc);

        Typeface R_light = Typeface.createFromAsset(getAssets(),"Roboto-Light.ttf");

        btn_orcamento_aprovado = (Button)findViewById(R.id.btn_orcamento_aprovado);
        btn_orcamento_cancelado = (Button)findViewById(R.id.btn_orcamento_cancelado);
        btn_aprovar_orc = (Button)findViewById(R.id.btn_aprovar_orcamento);
        btn_cancelar_orc = (Button)findViewById(R.id.btn_cancelar_orcamento);
        btn_fechar_orc = (ImageView)findViewById(R.id.btn_fechar_orc);

        txt_exibir_anexo = (TextView)findViewById(R.id.txt_exibir_anexo);
        txt_pedido_tb = (TextView)findViewById(R.id.txt_pedido_tb);
        txt_status_pedido = (TextView)findViewById(R.id.txt_status_pedido);
        txt_valor_total = (TextView)findViewById(R.id.txt_valor_total);
        img_status_orcamento = (CircleImageView)findViewById(R.id.img_status_orcamento);
        txt_endereco = (TextView)findViewById(R.id.txt_endereco);
        txt_obs_itens = (TextView)findViewById(R.id.txt_obs_itens);
        img_endereco_recebimento = (CircleImageView)findViewById(R.id.img_endereco_recebimento);

        progressDialog = new ProgressDialog(this);
        cod_orcamento = getIntent().getStringExtra("cod_orcamento");
        if(cod_orcamento != null){
            Log.d("TAG cod_orcamento",cod_orcamento);
            onRestart();
        }

        txt_status_pedido.setTypeface(R_light);
        txt_pedido_tb.setTypeface(R_light);
        txt_obs_itens.setTypeface(R_light);
        btn_orcamento_aprovado.setTypeface(R_light);
        btn_orcamento_cancelado.setTypeface(R_light);
        btn_aprovar_orc.setTypeface(R_light);
        btn_cancelar_orc.setTypeface(R_light);

        txt_exibir_anexo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog_Img_Anexo();
            }
        });
    }

    private void getOrcamento(String id){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE + "Orcamento/GetOrcamento/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        progressDialog.setMessage("Aguarde......");
        progressDialog.setTitle("Detalhando Orçamento");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        ClientesApi getOrcamentoService = retrofit.create(ClientesApi.class);
        Call<OrcamentoAux> call = getOrcamentoService.getOrcamento(id); //id do orçamento
        call.enqueue(new Callback<OrcamentoAux>() {
            @Override
            public void onResponse(Call<OrcamentoAux> call, Response<OrcamentoAux>response) {
                if (response.isSuccessful()) {
                    OrcamentoAux orcamentoResponse = response.body();

                    orcamento = new OrcamentoAux(
                            orcamentoResponse.getCOD_ORCAMENTO(),
                            orcamentoResponse.getCOD_CLIENTE(),
                            orcamentoResponse.getCOD_EMPRESA(),
                            orcamentoResponse.getTITULO(),
                            orcamentoResponse.getANEXO(),
                            orcamentoResponse.getOBSERVACAO(),
                            orcamentoResponse.getSTATUS_PEDIDO(),
                            orcamentoResponse.getDATA_PEDIDO(),
                            orcamentoResponse.getVALOR_TOTAL(),
                            orcamentoResponse.getLOCAL_RETIRADA(),
                            orcamentoResponse.getCEP(),
                            orcamentoResponse.getRUA(),
                            orcamentoResponse.getNUMERO(),
                            orcamentoResponse.getCOMPLEMENTO(),
                            orcamentoResponse.getBAIRRO(),
                            orcamentoResponse.getCIDADE(),
                            orcamentoResponse.getESTADO()
                    );
                    popularCampos();
                    Log.d("TAG body orçamento",String.valueOf(response.body()));
                    Log.d("TAG code orçamento",String.valueOf(response.code()));
                    Log.d("TAG raw orçamento",String.valueOf(response.raw()));

                }else{
                    Log.d("TAG body orçamento",String.valueOf(response.body()));
                    Log.d("TAG code orçamento",String.valueOf(response.code()));
                    Log.d("TAG raw orçamento",String.valueOf(response.raw()));
                }
            }

            @Override
            public void onFailure(Call<OrcamentoAux> call, Throwable t) {

            }
        });
    }

    private void getItensOrcamentos(String id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE + "Orcamento/itens/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ClientesApi getOrcamentoService = retrofit.create(ClientesApi.class);
        Call<List<Orcamentos>> call = getOrcamentoService.getItensOrcamentosCliente(id); //id do orçamento
        call.enqueue(new Callback<List<Orcamentos>>() {
            @Override
            public void onResponse(Call<List<Orcamentos>> call, Response<List<Orcamentos>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Orcamentos> ItensResponse = (ArrayList<Orcamentos>) response.body();

                    listItensOrcamentos.clear();
                    for (int x = 0; x < ItensResponse.size(); x++) {
                        Orcamentos itens_orcamentos = new Orcamentos(
                                ItensResponse.get(x).getNOME(),
                                ItensResponse.get(x).getVALOR_ITEM()
                                );
                        listItensOrcamentos.add(itens_orcamentos);
                    }
                    Adapter adapter = new ItensOrcamentoAdapter(listItensOrcamentos, Activity_detalhes_Orcamento.this);
                    lv_itens_orcamento.setAdapter((ListAdapter) adapter);
                    if(listItensOrcamentos.size() == 0)
                    {
                        txt_obs_itens.setVisibility(View.VISIBLE);
                        lv_itens_orcamento.setVisibility(View.INVISIBLE);
                    }else{
                        txt_obs_itens.setVisibility(View.INVISIBLE);
                        lv_itens_orcamento.setVisibility(View.VISIBLE);
                    }
                    progressDialog.dismiss();
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
            public void onFailure(Call<List<Orcamentos>> call, Throwable t) {
                Toast.makeText(Activity_detalhes_Orcamento.this,
                        "Não foi possível carregar seu orçamento",
                        Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void popularCampos(){

        txt_pedido_tb.setText("Pedido "+String.valueOf(orcamento.getCOD_ORCAMENTO()));
        txt_valor_total.setText("R$ "+ String.valueOf(orcamento.getVALOR_TOTAL()));
        String enderecoEntrega =
                String.valueOf(orcamento.getRUA())+", "+
                        String.valueOf(orcamento.getNUMERO())+", "+
                        String.valueOf(orcamento.getBAIRRO())+" - "+
                        String.valueOf(orcamento.getCIDADE())+" - "+
                        String.valueOf(orcamento.getESTADO());
        txt_endereco.setText(enderecoEntrega);

        if(orcamento.getLOCAL_RETIRADA().equals("ENTREGA")) {
            img_endereco_recebimento.setImageResource(R.drawable.ic_home_orc_roxo);
        }if(orcamento.getLOCAL_RETIRADA().equals("LOJA")) {
            img_endereco_recebimento.setImageResource(R.drawable.ic_maps_pin_roxo);
        }

        if (orcamento.getSTATUS_PEDIDO() == 1) {
            txt_status_pedido.setText("Orçamento Enviado");
            img_status_orcamento.setImageResource(R.drawable.ic_send);
            btn_aprovar_orc.setVisibility(View.GONE);
            btn_cancelar_orc.setVisibility(View.GONE);
            btn_orcamento_aprovado.setVisibility(View.GONE);
            btn_orcamento_cancelado.setVisibility(View.VISIBLE);
            btn_orcamento_cancelado.setText("Cancelar Orçamento");
        }if (orcamento.getSTATUS_PEDIDO() == 2){
            txt_status_pedido.setText("Orçamento Calculado");
            img_status_orcamento.setImageResource(R.drawable.ic_dindin);
            btn_aprovar_orc.setVisibility(View.VISIBLE);
            btn_cancelar_orc.setVisibility(View.VISIBLE);
            btn_orcamento_aprovado.setVisibility(View.GONE);
            btn_orcamento_cancelado.setVisibility(View.GONE);
            ll_aprovar_orc.setVisibility(View.VISIBLE);
        }if (orcamento.getSTATUS_PEDIDO() == 3){
            txt_status_pedido.setText("Aguardando Aprovação");
            img_status_orcamento.setImageResource(R.drawable.ic_clocking);
            btn_aprovar_orc.setVisibility(View.VISIBLE);
            btn_cancelar_orc.setVisibility(View.VISIBLE);
            btn_orcamento_aprovado.setVisibility(View.GONE);
            btn_orcamento_cancelado.setVisibility(View.GONE);
        }if (orcamento.getSTATUS_PEDIDO() == 4){
            txt_status_pedido.setText("Orçamento Confirmado");
            img_status_orcamento.setImageResource(R.drawable.ic_confirm_orc);
            btn_aprovar_orc.setVisibility(View.GONE);
            btn_cancelar_orc.setVisibility(View.GONE);
            btn_orcamento_aprovado.setVisibility(View.VISIBLE);
            btn_orcamento_cancelado.setVisibility(View.VISIBLE);
        }if (orcamento.getSTATUS_PEDIDO() == 5) {
            txt_status_pedido.setText("Orçamento Cancelado");
            img_status_orcamento.setImageResource(R.drawable.ic_cancel);
            btn_aprovar_orc.setVisibility(View.GONE);
            btn_cancelar_orc.setVisibility(View.GONE);
            btn_orcamento_aprovado.setVisibility(View.GONE);
            btn_orcamento_cancelado.setVisibility(View.VISIBLE);
            txt_valor_total.setTextColor(Color.RED);
            btn_orcamento_cancelado.setText("Orçamento Cancelado");
        }
    }

    private void Dialog_Img_Anexo(){
        AlertDialog.Builder dialog_img = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_img_receita,null);
        dialog_img.setView(dialogView);

        final AlertDialog alertDialog = dialog_img.create();
        Button btn_fechar_dialog = (Button)dialogView.findViewById(R.id.btn_dialog_receita);
        ImageView img_anexo_dialog = (ImageView)dialogView.findViewById(R.id.img_dialog_receita);
        if(orcamento.getANEXO() != null) {
            Bitmap bitMap = converterImage.base64TobitMap(orcamento.getANEXO());
            if (bitMap != null) {
                //img_anexo_dialog.setImageBitmap(Bitmap.createScaledBitmap(bitMap,width,350,true));
                img_anexo_dialog.setImageBitmap(bitMap);
                alertDialog.show();
                btn_fechar_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
            } else {
                final AlertDialog.Builder dialog_erro = new AlertDialog.Builder(this);
                dialog_erro.create();
                dialog_erro.setTitle("Este orçamento não possuí anexo");
                dialog_erro.setPositiveButton("Entendi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                dialog_erro.show();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_aprovar_orcamento:
                btn_orcamento_aprovado.setVisibility(View.VISIBLE);
                btn_orcamento_cancelado.setVisibility(View.GONE);
                btn_aprovar_orc.setVisibility(View.GONE);
                btn_cancelar_orc.setVisibility(View.GONE);
                aprovarOrcamento(4); //status orçamento aprovado
                break;
            case R.id.btn_fechar_orc:
                finish();
                break;
            case R.id.btn_cancelar_orcamento:
                btn_orcamento_aprovado.setVisibility(View.GONE);
                btn_orcamento_cancelado.setVisibility(View.VISIBLE);
                btn_aprovar_orc.setVisibility(View.GONE);
                btn_cancelar_orc.setVisibility(View.GONE);
                btn_orcamento_cancelado.setText("Orçamento Cancelado");
                aprovarOrcamento(5); //status orçamento cancelado
                break;
            case R.id.btn_orcamento_cancelado:
                btn_orcamento_aprovado.setVisibility(View.GONE);
                btn_orcamento_cancelado.setVisibility(View.VISIBLE);
                btn_aprovar_orc.setVisibility(View.GONE);
                btn_cancelar_orc.setVisibility(View.GONE);
                btn_orcamento_cancelado.setText("Orçamento Cancelado");
                aprovarOrcamento(5);

            case R.id.btn_orcamento_aprovado:
                Intent it = new Intent(this, Activity_detalhes_entrega.class);
                it.putExtra("cv_entrega",String.valueOf(orcamento.getCOD_ORCAMENTO()));
                startActivity(it);
        }
    }

    private void aprovarOrcamento(int cod_status) {
        progressDialog.setMessage("Aguarde......");
        progressDialog.setTitle("Aprovando Orçamento");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE + "Orcamento/putStatusOrcamento/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ClientesApi clienteResource = retrofit.create(ClientesApi.class);
        Confir_Orc orc_atualizado = new Confir_Orc(
                orcamento.getCOD_ORCAMENTO(),
                cod_status
        );
        Call <Confir_Orc> call = clienteResource.putOrcamento(orc_atualizado);
        call.enqueue(new Callback<Confir_Orc>() {
            @Override
            public void onResponse(Call<Confir_Orc> call, Response<Confir_Orc> response) {
                if(response.isSuccessful())
                {
                    Log.d("response code",String.valueOf(response.code()));
                    Log.d("response raw",String.valueOf(response.raw()));
                    //Log.d("response body",String.valueOf(response.body().getCOD_ORCAMENTO()));
                    Log.d("response raw message",response.raw().message());
                    Log.d("response raw message",String.valueOf(response.body()));
                    progressDialog.dismiss();
                    onRestart();
                }else{
                    Log.d("response code",String.valueOf(response.code()));
                    Log.d("response raw",String.valueOf(response.raw()));
                    //Log.d("response body",String.valueOf(response.body().getCOD_ORCAMENTO()));
                    Log.d("response raw message",response.raw().message());
                    Log.d("response raw message",String.valueOf(response.body()));
                    progressDialog.dismiss();
                    onRestart();
                }
            }

            @Override
            public void onFailure(Call<Confir_Orc> call, Throwable t) {
                alertaDialog("Ops","Não foi possível aprovar seu orçamento ):");
                progressDialog.dismiss();
                onRestart();
            }
        });
    }

    private void cancelarOrcamento() {
    }

    private void acompanharOrcamento() {
    }

    private void alertaDialog(String titulo, String mensagem) {
        AlertDialog alerta = new AlertDialog.Builder(this).create();
        alerta.setTitle(titulo);
        alerta.setMessage(mensagem);
        alerta.setIcon(R.mipmap.ic_atencao);
        alerta.show();
    }

    public class Confir_Orc{
        private int ID_ORCAMENTO;
        private int ID_STATUS_ORCAMENTO;

        public Confir_Orc(int ID_ORCAMENTO, int ID_STATUS_ORCAMENTO) {
            this.ID_ORCAMENTO = ID_ORCAMENTO;
            this.ID_STATUS_ORCAMENTO = ID_STATUS_ORCAMENTO;
        }

        public int getID_ORCAMENTO() {
            return ID_ORCAMENTO;
        }

        public void setID_ORCAMENTO(int ID_ORCAMENTO) {
            this.ID_ORCAMENTO = ID_ORCAMENTO;
        }

        public int getID_STATUS_ORCAMENTO() {
            return ID_STATUS_ORCAMENTO;
        }

        public void setID_STATUS_ORCAMENTO(int ID_STATUS_ORCAMENTO) {
            this.ID_STATUS_ORCAMENTO = ID_STATUS_ORCAMENTO;
        }
    }
}
