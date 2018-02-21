package com.pharmamobi.joao.rotas.Adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pharmamobi.joao.rotas.R;
import com.pharmamobi.joao.rotas.model.OrcamentoAux;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrcamentoAdapter extends BaseAdapter {

    private final ArrayList<OrcamentoAux> orcamentos;
    private final Activity act;

    public OrcamentoAdapter(ArrayList<OrcamentoAux> orcamentos, Activity act) {
        this.orcamentos = orcamentos;
        this.act = act;
    }

    @Override
    public int getCount() {
        return orcamentos.size();
    }

    @Override
    public Object getItem(int position) {
        return orcamentos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.item_linha_orcamento, parent, false);

        OrcamentoAux orc = orcamentos.get(position);
        CircleImageView img_status_orcamento = (CircleImageView)view.findViewById(R.id.img_status_orcamento);
        TextView txt_num_pedido = (TextView)view.findViewById(R.id.txt_num_pedido);
        TextView txt_status_orcamento = (TextView)view.findViewById(R.id.txt_status_orcamento);
        TextView txt_valor_orcamento = (TextView)view.findViewById(R.id.txt_valor_orcamento);

        if(orc.getSTATUS_PEDIDO() == 1){
            txt_status_orcamento.setText("Orçamento Enviado");
            img_status_orcamento.setBackgroundResource(R.drawable.ic_send);
        }if(orc.getSTATUS_PEDIDO() == 2){
            txt_status_orcamento.setText("Orçamento Calculado");
            img_status_orcamento.setBackgroundResource(R.drawable.ic_dindin);
        }if(orc.getSTATUS_PEDIDO() == 3){
            txt_status_orcamento.setText("Aguardando Aprovação");
            img_status_orcamento.setBackgroundResource(R.drawable.ic_clocking);
        }if(orc.getSTATUS_PEDIDO() == 4){
            txt_status_orcamento.setText("Orçamento Confirmado");
            img_status_orcamento.setBackgroundResource(R.drawable.ic_confirm_orc);
        }if(orc.getSTATUS_PEDIDO() == 5){
            txt_status_orcamento.setText("Orçamento Cancelado");
            img_status_orcamento.setBackgroundResource(R.drawable.ic_cancel);
        }
        double valor_total = orc.getVALOR_TOTAL();
        valor_total = Double.valueOf(String.format(Locale.US,"%.2f",Math.ceil(valor_total)));


        txt_num_pedido.setText("Pedido " + String.valueOf(orc.getCOD_ORCAMENTO()));
        txt_valor_orcamento.setText("R$ "+ String.valueOf(valor_total));


        return view;
    }
}