package com.pharmamobi.joao.rotas.Adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pharmamobi.joao.rotas.R;
import com.pharmamobi.joao.rotas.model.OrcamentoAux;
import com.pharmamobi.joao.rotas.model.RelacaoEntregas;

import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class Entregas_Boy_Adapter extends BaseAdapter {

    private final ArrayList<RelacaoEntregas> Relacaoentregas;
    private final Activity act;

    public Entregas_Boy_Adapter(ArrayList<RelacaoEntregas> Relacaoentregas, Activity act) {
        this.Relacaoentregas = Relacaoentregas;
        this.act = act;
    }

    @Override
    public int getCount() {
        return Relacaoentregas.size();
    }

    @Override
    public Object getItem(int position) {
        return Relacaoentregas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.item_linha_motoboy, parent, false);

        RelacaoEntregas entrega = Relacaoentregas.get(position);
        Typeface r_ligh = Typeface.createFromAsset(act.getAssets(), "Roboto-Light.ttf");

        TextView txt_qtd_entregas_boy = (TextView) view.findViewById(R.id.txt_qtd_entregas_boy);
        TextView txt_qtd_entregas_ret_boy = (TextView) view.findViewById(R.id.txt_qtd_entregas_ret_boy);
        TextView txt_qtd_entregas_aguar_boy = (TextView) view.findViewById(R.id.txt_qtd_entregas_aguar_boy);
        txt_qtd_entregas_boy.setTypeface(r_ligh);
        txt_qtd_entregas_ret_boy.setTypeface(r_ligh);
        txt_qtd_entregas_aguar_boy.setTypeface(r_ligh);

        TextView data_relacao = (TextView) view.findViewById(R.id.data_relacao_boy);

        txt_qtd_entregas_boy.setText("Entregues " + String.valueOf(entrega.getQTD_ENTREGAS()));
        txt_qtd_entregas_ret_boy.setText("Retornadas " + String.valueOf(entrega.getQTD_ENTREGAS_RETORNADAS()));
        int entregasAguardando = entrega.getQTD_ENTREGAS() - entrega.getQTD_ENTREGAS_RETORNADAS();
        txt_qtd_entregas_aguar_boy.setText("Aguardando " + String.valueOf(entregasAguardando));
        data_relacao.setText(String.valueOf(entrega.getDATA_RELACAO()));

        return view;
    }
}