package com.pharmamobi.joao.rotas;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentConta extends Fragment {
    private EditText edt_senha,edt_repita_senha, edt_numero_cli, edt_celular_cliente,edt_telefone_cliente,edt_telefone_cliente_inflate;
    private TextView txt_numero_cliente;
    private RadioButton rb_mostrar_senha;
    private CheckBox ck_celular, ck_telefone, ck_ambos;
    private KeyListener listener;
    private ViewStub vs_edtTxt;
    private View Edt_inflated;
    private Button btn_logof;
    private GoogleApiClient mGoogleApiClient;

    public FragmentConta() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_perfil_conta, container, false);

        edt_celular_cliente = (EditText)v.findViewById(R.id.edt_celular_cliente);
        edt_telefone_cliente = (EditText)v.findViewById(R.id.edt_telefone_cliente);
        txt_numero_cliente = (TextView) v.findViewById(R.id.txt_numero_cliente);
        edt_senha = (EditText) v.findViewById(R.id.edt_senha);
        edt_repita_senha = (EditText)v.findViewById(R.id.edt_repita_senha);
        edt_numero_cli = (EditText) v.findViewById(R.id.edt_numero_cliente);
        listener = edt_numero_cli.getKeyListener();
        edt_numero_cli.setKeyListener(null);

        btn_logof = (Button)v.findViewById(R.id.btn_logof);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build();
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity()).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
        btn_logof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logof();
            }
        });

        edt_numero_cli.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if (!ck_celular.isChecked() && !ck_telefone.isChecked() && !ck_ambos.isChecked()) {
                        Toast.makeText(getActivity(), "Escolha um meio de contato, se caso precisarmos ligar para você.", Toast.LENGTH_SHORT).show();
                    }
                    return false;
                }
                return false;

            }
        });
        edt_celular_cliente.addTextChangedListener(Mask.insert(Mask.MaskType.CEL, edt_celular_cliente));
        edt_telefone_cliente.addTextChangedListener(Mask.insert(Mask.MaskType.TEL, edt_telefone_cliente));


        rb_mostrar_senha = (RadioButton) v.findViewById(R.id.rb_mostrar_senha);
        ck_celular = (CheckBox) v.findViewById(R.id.ck_celular);
        ck_telefone = (CheckBox) v.findViewById(R.id.ck_telefone);
        ck_ambos = (CheckBox) v.findViewById(R.id.ck_ambos);

        rb_mostrar_senha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrar_senha();
            }
        });

        ck_celular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_button_ctt(view);
            }
        });
        ck_telefone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_button_ctt(view);
            }
        });
        ck_ambos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_button_ctt(view);
            }
        });

        return v;
    }
    @Override
    public void onStart(){
        super.onStart();
        mGoogleApiClient.connect();
    }
    private void Logof() {
            LoginManager.getInstance().logOut();

        if(mGoogleApiClient.isConnected()){
            Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                        }
                    }
            );}
        Intent intent = new Intent(getActivity(),ActivityLogin.class);
        startActivity(intent);
        }

    private void mostrar_senha() {
        CountDownTimer meuContador = new CountDownTimer(3000, 1000) {
            public void onTick(long millisUntilFinished) {
                edt_senha.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                edt_senha.setSelection(edt_senha.getText().length());
                edt_repita_senha.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                //if(edt_repita_senha.getText().length()>0) {
                    edt_repita_senha.setSelection(edt_repita_senha.getText().length());
                //}
            }

            public void onFinish() {
                edt_senha.setInputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PASSWORD);
                edt_senha.setSelection(edt_senha.getText().length());
                rb_mostrar_senha.setChecked(false);
            }
        };
        meuContador.start();
    }

    private void check_button_ctt(View v) {
        if (v.getId() == R.id.ck_celular) {
            if (ck_celular.isChecked()) {
                txt_numero_cliente.setText("Celular");
                if (vs_edtTxt != null) {
                    vs_edtTxt.setVisibility(View.GONE);
                }
                edt_celular_cliente.setVisibility(View.VISIBLE);
                edt_numero_cli.setVisibility(View.GONE);
                edt_telefone_cliente.setVisibility(View.GONE);
                //edt_numero_cli.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_celular, 0, 0, 0);
                ck_celular.setClickable(false);
                ck_telefone.setClickable(true);
                ck_telefone.setChecked(false);
                ck_ambos.setClickable(true);
                ck_ambos.setChecked(false);
            }
        }
        if (v.getId() == R.id.ck_telefone) {
            if (ck_telefone.isChecked()) {
                txt_numero_cliente.setText("Telefone");
                if (vs_edtTxt != null) {
                    vs_edtTxt.setVisibility(View.GONE);
                }
                edt_telefone_cliente.setVisibility(View.VISIBLE);
                edt_numero_cli.setVisibility(View.GONE);
                edt_celular_cliente.setVisibility(View.GONE);
                ck_telefone.setClickable(false);
                ck_celular.setClickable(true);
                ck_celular.setChecked(false);
                ck_ambos.setClickable(true);
                ck_ambos.setChecked(false);
            }

        }
        if (v.getId() == R.id.ck_ambos) {
            edt_celular_cliente.setVisibility(View.VISIBLE);
            edt_telefone_cliente.setVisibility(View.GONE);
            edt_numero_cli.setVisibility(View.GONE);
            ck_ambos.setClickable(false);
            ck_celular.setClickable(true);
            ck_celular.setChecked(false);
            ck_telefone.setClickable(true);
            ck_telefone.setChecked(false);
            inflarEdtContato();
            //edt_numero_cli.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_agenda_tel,0,0,0);
        }

        /*switch (v.getId())
        {
            case R.id.ck_celular:
                edt_numero_cli.addTextChangedListener(Mask.insert(Mask.MaskType.CEL,edt_numero_cli));
                ck_celular.setChecked(true);
                ck_telefone.setChecked(false);
                ck_ambos.setChecked(false);
                edt_numero_cli.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_celular,0,0,0);
                break;
            case R.id.ck_telefone:
                edt_numero_cli.addTextChangedListener(Mask.insert(Mask.MaskType.TEL,edt_numero_cli));
                ck_telefone.setChecked(true);
                ck_celular.setChecked(false);
                ck_ambos.setChecked(false);
                edt_numero_cli.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_telefone,0,0,0);
                break;
            case R.id.ck_ambos:
                edt_numero_cli.setText("Ambos");
                ck_ambos.setChecked(true);
                ck_telefone.setChecked(false);
                ck_celular.setChecked(false);
                edt_numero_cli.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_agenda_tel,0,0,0);
                break;
        }*/
    }

    private void inflarEdtContato() {
        txt_numero_cliente.setText("Celular");
        edt_numero_cli.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_celular, 0, 0, 0);
        if (vs_edtTxt == null) {
            vs_edtTxt = (ViewStub) getView().findViewById(R.id.stub_import);
            Edt_inflated = vs_edtTxt.inflate();
            edt_telefone_cliente_inflate = (EditText) Edt_inflated.findViewById(R.id.edt_telefone_cliente_inflate);
            edt_telefone_cliente_inflate.addTextChangedListener(Mask.insert(Mask.MaskType.TEL, edt_telefone_cliente_inflate));
        } else {
            Edt_inflated.setVisibility(View.VISIBLE);
        }

    }
}
