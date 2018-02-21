package com.pharmamobi.joao.rotas;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.Calendar;

/**
 * Created by joao on 13/05/2017.
 */
//DatePickerDialog.OnDateSetListener, DialogInterface.OnCancelListener
public class DateActivity extends DialogFragment{
    private int year = 2000, month = 5, day = 5, ano, mes, dia;
    private int anoAtual, mesAtual, diaAtual;
    private TextView txt_data_agendada, txt_data_agendada_info;
    private Calendar calendar;
    DatePickerDialog dialog;
    private View txt_inflated;
    private ViewStub stub_import;
    final long today = System.currentTimeMillis() - 1000;
    public static String dataReagendada;

    public DateActivity() {
    }

    private DatePickerDialog.OnDateSetListener listener;

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int year    = calendar.get(Calendar.YEAR);
        int month   = calendar.get(Calendar.MONTH);
        int day     = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(getContext(), listener, year, month, day);
        dialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
                set_datareagendada(ano,mes,dia);
                dataReagendada = ano+"-"+mes+"-"+dia+" 00:00:00.000";
            }
        });
        Field mDatePickerField;
        try {
            mDatePickerField = dialog.getClass().getDeclaredField("mDatePicker");
            mDatePickerField.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        return dialog;
    }

    private void set_datareagendada(int ano, int mes, int dia) {
        txt_data_agendada = (TextView) getActivity().findViewById(R.id.txt_data_agendada);
        txt_data_agendada.setText(dia + "/" + mes + "/" + ano);
        txt_data_agendada.setVisibility(View.VISIBLE);

        txt_data_agendada_info = (TextView) getActivity().findViewById(R.id.txt_data_agendada_info);
        txt_data_agendada.setTextColor(Color.BLACK);
        txt_data_agendada_info.setVisibility(View.VISIBLE);
    }
}
//}@Override
//public Dialog onCreateDialog(Bundle savedInstanceState) {
//    calendar = Calendar.getInstance();
//    anoAtual = calendar.get(Calendar.YEAR);
//    mesAtual = calendar.get(Calendar.MONTH);
//    diaAtual = calendar.get(Calendar.DAY_OF_MONTH);
//
//
//    DatePickerDialog dpd = new DatePickerDialog(getActivity(),
//            new DatePickerDialog.OnDateSetListener() {
//                @Override
//                public void onDateSet(DatePicker view, int year, int month, int day) {
//                    set_datareagendada(year,month,day);
//                    ano = year;
//                    mes = month;
//                    dia = day;
//                    calendar.set(year, month, day);
//                    anoAtual = calendar.get(Calendar.YEAR);
//                    mesAtual = calendar.get(Calendar.MONTH);
//                    diaAtual = calendar.get(Calendar.DAY_OF_MONTH);
//                }
//            }, year, month, day);
//    Calendar d = Calendar.getInstance();
//
//    dpd.updateDate(d.get(Calendar.YEAR),d.get(Calendar.MONTH),d.get(Calendar.DAY_OF_MONTH));
//    return dpd;
//}