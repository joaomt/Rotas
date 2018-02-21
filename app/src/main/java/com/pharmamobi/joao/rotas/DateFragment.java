package com.pharmamobi.joao.rotas;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;



/**
 * Created by joao on 13/05/2017.
 */
//DatePickerDialog.OnDateSetListener, DialogInterface.OnCancelListener
public class DateFragment extends DialogFragment{
    private int year = 2000, month = 5, day =5;
    private int anoAtual,mesAtual,diaAtual;
    private TextView txt_nascimento, txt_nascimento_info;
    private Calendar calendar;
    DatePickerDialog dialog;

    public DateFragment() {
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        calendar = Calendar.getInstance();
        anoAtual = calendar.get(Calendar.YEAR);
        mesAtual = calendar.get(Calendar.MONTH);
        diaAtual = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        setEdt_nascimento(year, month, day);
                        calendar.set(year, month, day);
                        anoAtual = calendar.get(Calendar.YEAR);
                        mesAtual = calendar.get(Calendar.MONTH);
                        diaAtual = calendar.get(Calendar.DAY_OF_MONTH);
                    }
                }, year, month, day);
        Calendar d = Calendar.getInstance();

        dpd.updateDate(d.get(Calendar.YEAR),d.get(Calendar.MONTH),d.get(Calendar.DAY_OF_MONTH));
        return dpd;
    }
    private void setEdt_nascimento(int ano,int mes,int dia){
        txt_nascimento = (TextView) getActivity().findViewById(R.id.txt_nascimento);
        txt_nascimento.setText(dia + "/" + mes + "/" + ano);
        txt_nascimento.setTextColor(getResources().getColor(R.color.preto));

        txt_nascimento_info = (TextView) getActivity().findViewById(R.id.txt_info_nascimento);
        txt_nascimento_info.setVisibility(View.VISIBLE);
    }
}