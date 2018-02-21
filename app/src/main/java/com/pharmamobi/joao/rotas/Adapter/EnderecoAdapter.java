package com.pharmamobi.joao.rotas.Adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.pharmamobi.joao.rotas.Interfaces.RecyclerViewOnclickListenerHack;
import com.pharmamobi.joao.rotas.R;
import com.pharmamobi.joao.rotas.model.Endereco;

import java.util.ArrayList;


/**
 * Created by joao on 31/08/2017.
 */

public class EnderecoAdapter extends BaseAdapter {

    private final ArrayList<Endereco> enderecos;
    private final Activity act;

    public EnderecoAdapter(ArrayList<Endereco> enderecos, Activity act) {
        this.enderecos = enderecos;
        this.act = act;
    }

    @Override
    public int getCount() {
        return enderecos.size();
    }

    @Override
    public Object getItem(int position) {
        return enderecos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.linha_listview_endereco, parent, false);

        Endereco end = enderecos.get(position);
        Typeface R_light = Typeface.createFromAsset(act.getAssets(),"Roboto-Light.ttf");
        RadioButton rb_rua = (RadioButton)view.findViewById(R.id.rb_rua_list);
        TextView txt_bairro = (TextView)view.findViewById(R.id.txt_bairro_list);

        txt_bairro.setTypeface(R_light);
        rb_rua.setTypeface(R_light);



        rb_rua.setText(end.getRUA()+", "+end.getNUMERO());
        txt_bairro.setText(end.getBAIRRO()+", "+end.getCIDADE()+" - "+end.getESTADO());
        rb_rua.setCompoundDrawablesWithIntrinsicBounds(end.getDrawable(),0,0,0);

        return view;
    }
}