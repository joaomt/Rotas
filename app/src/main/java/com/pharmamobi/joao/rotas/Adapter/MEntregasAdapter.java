package com.pharmamobi.joao.rotas.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pharmamobi.joao.rotas.Interfaces.RecyclerViewOnclickListenerHack;
import com.pharmamobi.joao.rotas.R;
import com.pharmamobi.joao.rotas.activity.Activity_detalhes_entrega;
import com.pharmamobi.joao.rotas.model.MEntregas;

import java.util.List;

/**
 * Created by joao on 09/06/2017.
 */

public class MEntregasAdapter extends RecyclerView.Adapter<MEntregasAdapter.MyViewHolder> {
    private List<MEntregas> mList;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnclickListenerHack mRecyclerViewOnclickListenerHack;
    private int cor_verde,cor_amarelo, cor_vermelho;
    private final String status_entregue = "ENTREGUE";
    private final String status_enviado= "ENVIADO";
    private final String status_cancelado= "CANCELADO";
    private Context context;


    public MEntregasAdapter(Context c, List<MEntregas> m)
    {
        context = c;
        mList = m;
        mLayoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Log.i("LOG","onCreateViewHolder");
        View v = mLayoutInflater.inflate(R.layout.item_m_ntregas,viewGroup,false);
        MyViewHolder mvh = new MyViewHolder(v);

        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        Log.i("LOG","onBindHolder");
        cor_vermelho = Color.parseColor("#ec1515");
        cor_verde = Color.parseColor("#FF40EC29");
        cor_amarelo = Color.parseColor("#FFEEFA00");

        if(mList.get(position).getSTATUS_ENTREGA().equals(status_entregue))
        {
            myViewHolder.txt_status.setTextColor(cor_verde);
            myViewHolder.txt_status.setText("Entregue");
        }
        if(mList.get(position).getSTATUS_ENTREGA().equals(status_enviado))
        {
            myViewHolder.txt_status.setTextColor(cor_amarelo);
            myViewHolder.txt_status.setText("Enviado");
        }
        if(mList.get(position).getSTATUS_ENTREGA().equals(status_cancelado))
        {
            myViewHolder.txt_status.setTextColor(cor_vermelho);
            myViewHolder.txt_status.setText("Cancelado");
        }
        myViewHolder.txt_comprovante.setText(String.valueOf(mList.get(position).getNUM_COMPRO_VENDA()));
        myViewHolder.txt_valor_entrega.setText(String.valueOf(mList.get(position).getVALOR_ENTREGA()));
        myViewHolder.txt_data_entrega.setText(mList.get(position).getDATA_RELACAO());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addListEntrega(MEntregas e,int position)
    {
        mList.add(e);
        notifyItemInserted(position);
    }

    public void removeListItem(int position)
    {
        Log.i("LOG","Metodo remover item: "+position);
    }

    public void setRecyclerViewOnclickListenerHack(RecyclerViewOnclickListenerHack r)
    {
        mRecyclerViewOnclickListenerHack = r;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView txt_status, txt_comprovante, txt_valor_entrega,txt_data_entrega;
        public Button btn_mais_detalhes,btn_avalie;

        public MyViewHolder(View itemView) {
            super(itemView);
            Typeface R_light = Typeface.createFromAsset(context.getAssets(),"Roboto-Light.ttf");
            txt_status = (TextView)itemView.findViewById(R.id.txt_status);
            txt_comprovante = (TextView)itemView.findViewById(R.id.txt_comprovante);
            txt_valor_entrega = (TextView)itemView.findViewById(R.id.txt_valor_entrega);
            txt_data_entrega = (TextView)itemView.findViewById(R.id.txt_data_entrega);
            btn_mais_detalhes = (Button)itemView.findViewById(R.id.btn_mais_detalhes);
            btn_avalie = (Button)itemView.findViewById(R.id.btn_avalie_entrega);

            txt_status.setTypeface(R_light);
            txt_comprovante.setTypeface(R_light);
            txt_valor_entrega.setTypeface(R_light);
            txt_data_entrega.setTypeface(R_light);




            btn_mais_detalhes.setOnClickListener(this);
            btn_avalie.setOnClickListener(this);
            //itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_mais_detalhes)
            {
                Intent it = new Intent(context, Activity_detalhes_entrega.class);
               // startActivity(it);
            }

            if(mRecyclerViewOnclickListenerHack != null)
            {
                mRecyclerViewOnclickListenerHack.OnClickListener(v, getAdapterPosition());
            }
        }
    }



}
