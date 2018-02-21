package com.pharmamobi.joao.rotas.Adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pharmamobi.joao.rotas.R;
import com.pharmamobi.joao.rotas.model.Orcamentos;

import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ItensOrcamentoAdapter extends BaseAdapter {

    private final ArrayList<Orcamentos> itens;
    private final Activity act;

    public ItensOrcamentoAdapter(ArrayList<Orcamentos> itens, Activity act) {
        this.itens = itens;
        this.act = act;
    }

    @Override
    public int getCount() {
        return itens.size();
    }

    @Override
    public Object getItem(int position) {
        return itens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.item_linha_item_orc, parent, false);

        Orcamentos orc = itens.get(position);
        TextView txt_item_pedido = (TextView)view.findViewById(R.id.txt_item_pedido);
        TextView txt_valor_item = (TextView)view.findViewById(R.id.txt_valor_item);

        double valor_item = orc.getVALOR_ITEM();
        valor_item = Double.valueOf(String.format(Locale.US,"%.2f",Math.ceil(valor_item)));

        txt_item_pedido.setText(orc.getNOME());
        txt_valor_item.setText("R$ " +String.valueOf(valor_item));

        return view;
    }
}