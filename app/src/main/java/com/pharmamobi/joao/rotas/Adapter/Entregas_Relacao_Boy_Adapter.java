package com.pharmamobi.joao.rotas.Adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pharmamobi.joao.rotas.R;
import com.pharmamobi.joao.rotas.model.RelacaoEntregas;
import com.pharmamobi.joao.rotas.model.RelacaoEntregasInfo;

import java.util.ArrayList;

public class Entregas_Relacao_Boy_Adapter extends BaseAdapter {

    private final ArrayList<RelacaoEntregasInfo> RelacaoentregasInfo;
    private final Activity act;

    public Entregas_Relacao_Boy_Adapter(ArrayList<RelacaoEntregasInfo> Relacaoentregas, Activity act) {
        this.RelacaoentregasInfo = Relacaoentregas;
        this.act = act;
    }

    @Override
    public int getCount() {
        return RelacaoentregasInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return RelacaoentregasInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.item_linha_entregas_relacao, parent, false);

        RelacaoEntregasInfo entrega = RelacaoentregasInfo.get(position);
        Typeface r_ligh = Typeface.createFromAsset(act.getAssets(), "Roboto-Light.ttf");

        TextView txt_nome_cliente = (TextView) view.findViewById(R.id.txt_nome_cliente);
        TextView txt_valor_entrega_relacao = (TextView) view.findViewById(R.id.txt_valor_entrega_relacao);
        TextView txt_endereco_cliente = (TextView) view.findViewById(R.id.txt_endereco_cliente);

        txt_nome_cliente.setTypeface(r_ligh);
        txt_valor_entrega_relacao.setTypeface(r_ligh);
        txt_endereco_cliente.setTypeface(r_ligh);


        txt_nome_cliente.setText(String.valueOf(entrega.getNOME()));
        txt_valor_entrega_relacao.setText(String.valueOf(entrega.getVALOR_ENTREGA()));
        txt_endereco_cliente.setText(String.valueOf(
                        entrega.getRUA()+", "+
                        entrega.getNUMERO()+", "+
                        entrega.getBAIRRO()+" - "+
                        entrega.getCIDADE()));

        return view;
    }
}