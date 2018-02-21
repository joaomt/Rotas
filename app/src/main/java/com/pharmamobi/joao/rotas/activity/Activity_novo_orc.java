package com.pharmamobi.joao.rotas.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pharmamobi.joao.rotas.Domain.ConverterImage;
import com.pharmamobi.joao.rotas.Domain.EnderecoDef;
import com.pharmamobi.joao.rotas.Domain.WebApiUrl;
import com.pharmamobi.joao.rotas.Interfaces.ClientesApi;
import com.pharmamobi.joao.rotas.R;
import com.pharmamobi.joao.rotas.model.Endereco;
import com.pharmamobi.joao.rotas.model.Orcamentos;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.view.View.*;

public class Activity_novo_orc extends AppCompatActivity{

    private WebApiUrl webApi = new WebApiUrl();
    private String URL_BASE = webApi.GetUrlBase();
    private FloatingActionButton fab_anexo,fab_obs,fab_entrega;
    private Toolbar tb_novo_orc;
    private LinearLayout ll_anexo,ll_obs,ll_entrega,ll_todo_anexo,ll_todo_obs,ll_todo_entrega;
    private View linha_anexo,linha_obs,linha_entrega;
    private int corAnexo = Color.parseColor("#00CD66"),corObs = Color.parseColor("#FF4500"),corEntrega = Color.parseColor("#8B008B");
    private Button btn_ct_obs,btn_ct_anexo,btn_inserir_anexo,btn_enviar_orc;
    private static final int request_galeria = 101;
    private static final int request_camera = 102;
    private String[] array = new String[]{"Tirar foto", "Escolher na galeria"};
    private ImageView img_face_triste,img_anexo_excolhido,img_excluir_anexo;
    private TextView txt_anexo_cam,txt_receita,txt_entrega,txt_receita_info,txt_anexo,txt_obs;
    private TextView txt_rua_esc_casa,txt_bairro_esc_casa,txt_rua_esc_loja,txt_bairro_esc_loja;
    private EditText edt_obs,edt_titulo_orc;
    private RadioGroup rg_entrega;
    private RadioButton rb_casa,rb_loja;
    private Boolean confirm_anexo = false,confirm_obs = false;
    private String encodedString64;
    private ConverterImage converterImage = new ConverterImage();
    private String data_now;
    private ProgressDialog progressDoalog;


    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_novo_orcamento);
        data_now = String.valueOf(Calendar.YEAR)+"-"+String.valueOf(Calendar.MONTH)+"-"+String.valueOf(Calendar.DAY_OF_MONTH);
        Typeface R_light = Typeface.createFromAsset(getAssets(),"Roboto-Light.ttf");
        Typeface R_medium = Typeface.createFromAsset(getAssets(),"Roboto-Regular.ttf");
        progressDoalog= new ProgressDialog(this);

        rg_entrega = (RadioGroup) findViewById(R.id.rg_entrega);
        rb_casa = (RadioButton)findViewById(R.id.rb_casa);
        rb_loja = (RadioButton)findViewById(R.id.rb_loja);

        edt_obs = (EditText)findViewById(R.id.edt_obs);
        edt_titulo_orc = (EditText)findViewById(R.id.edt_titulo_orc);
        verificaCampos(edt_obs);

        txt_anexo_cam = (TextView) findViewById(R.id.txt_anexo_cam);
        txt_receita = (TextView) findViewById(R.id.txt_receita);
        txt_receita_info = (TextView) findViewById(R.id.txt_receita_info);
        txt_anexo = (TextView) findViewById(R.id.txt_anexo);
        txt_obs = (TextView) findViewById(R.id.txt_obs);
        txt_entrega = (TextView)findViewById(R.id.txt_entrega);
        txt_rua_esc_casa = (TextView)findViewById(R.id.txt_rua_esc_casa);
        txt_bairro_esc_casa = (TextView)findViewById(R.id.txt_bairro_esc_casa);
        txt_rua_esc_loja = (TextView)findViewById(R.id.txt_rua_esc_loja);
        txt_bairro_esc_loja = (TextView)findViewById(R.id.txt_bairro_esc_loja);


        img_face_triste = (ImageView)findViewById(R.id.img_face_triste);
        img_anexo_excolhido = (ImageView)findViewById(R.id.img_anexo_excolhido);
        img_excluir_anexo = (ImageView)findViewById(R.id.img_excluir_anexo);

        btn_ct_anexo = (Button)findViewById(R.id.btn_ct_anexo);
        btn_inserir_anexo = (Button)findViewById(R.id.btn_inserir_anexo);
        btn_ct_obs = (Button)findViewById(R.id.btn_ct_obs);
        btn_enviar_orc = (Button)findViewById(R.id.btn_enviar_orc);

        tb_novo_orc = (Toolbar)findViewById(R.id.tb_novo_orcamento);
        tb_novo_orc.setTitle("Novo Orçamento");
        setSupportActionBar(tb_novo_orc);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fab_anexo = (FloatingActionButton) findViewById(R.id.fab_anexo);
        fab_obs = (FloatingActionButton) findViewById(R.id.fab_obs);
        fab_entrega = (FloatingActionButton) findViewById(R.id.fab_entrega);

        ll_anexo = (LinearLayout) findViewById(R.id.ll_anexo);
        ll_obs = (LinearLayout) findViewById(R.id.ll_obs);
        ll_entrega = (LinearLayout) findViewById(R.id.ll_entrega);
        ll_todo_anexo =(LinearLayout) findViewById(R.id.ll_todo_anexo);
        ll_todo_obs = (LinearLayout) findViewById(R.id.ll_todo_obs);
        ll_todo_entrega = (LinearLayout) findViewById(R.id.ll_todo_entrega);

        linha_anexo = (View)findViewById(R.id.v_1);
        linha_obs = (View)findViewById(R.id.v_2);
        linha_entrega = (View)findViewById(R.id.v_3);

        txt_anexo.setTypeface(R_light);
        txt_obs.setTypeface(R_light);
        txt_obs.setTypeface(R_light);
        txt_anexo_cam.setTypeface(R_light);

        fab_anexo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                inserirAnexo();
            }
        });
        fab_obs.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                inserirObs();
            }

        });
        fab_entrega.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(confirm_anexo == false && confirm_obs == false){
                    alertaDialog("Atenção", "Insira um anexo ou uma observação sobre o pedido");
                    btn_enviar_orc.setVisibility(GONE);
                }else{
                    definirEntrega();
                    btn_enviar_orc.setVisibility(VISIBLE);
                }
            }
        });

        btn_ct_anexo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                inserirObs();
            }
        });
        btn_inserir_anexo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                btninserirAnexo();
            }
        });
        btn_ct_obs.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(confirm_anexo == false && confirm_obs == false){
                    alertaDialog("Atenção", "Insira um anexo ou uma observação sobre o pedido");
                    btn_enviar_orc.setVisibility(GONE);
                }
                else{
                    definirEntrega();
                    btn_enviar_orc.setVisibility(VISIBLE);
                }
            }
        });
        btn_enviar_orc.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(confirm_anexo == false && confirm_obs == false)
                {
                    alertaDialog("Atenção", "Insira um anexo ou uma observação sobre o pedido");
                    btn_enviar_orc.setVisibility(GONE);
                }
                else{
                    try {
                        EnviarOrcamento();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        img_excluir_anexo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                excluirAnexo();
            }
        });

        rg_entrega.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch(id) {
                    case R.id.rb_casa:
                        int casa = 1;
                        DefinirEntrega(casa);
                        break;
                    case R.id.rb_loja:
                        int loja = 2;
                        DefinirEntrega(loja);
                        break;
                }
            }
        });

        rg_entrega.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if (rb_casa.isChecked()) {
                        DefinirEntrega(1);
                    }if(rb_loja.isChecked()){
                        DefinirEntrega(2);
                    }
                    return false;
                }
                return false;
            }
        });
    }

    private void EnviarOrcamento() throws Exception {
        progressDoalog.setMessage("Aguarde......");
        progressDoalog.setTitle("Enviando Orçamento");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE + "Orcamento/PostOrcamento/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ClientesApi clienteResource = retrofit.create(ClientesApi.class);
        Orcamentos orcamento = new Orcamentos(
                2, //id cliente
                1, // id empresa
                edt_titulo_orc.getText().toString(), // titulo
                encodedString64, //anexo em base64
                edt_obs.getText().toString(), //observação
                1,//status orçamento (1 = enviado)
                data_now,
                0.0, //valor total
                EnderecoDef.ID, // status orçamento
                EnderecoDef.LOCAL_RETIRADA
        );
        Call <Orcamentos> call = clienteResource.postOrcamentoCliente(orcamento);
        call.enqueue(new Callback<Orcamentos>() {
            @Override
            public void onResponse(Call<Orcamentos> call, Response<Orcamentos> response) {
                if(response.isSuccessful())
                {
                    Log.d("response code",String.valueOf(response.code()));
                    Log.d("response raw",String.valueOf(response.raw()));
                    //Log.d("response body",String.valueOf(response.body().getCOD_ORCAMENTO()));
                    Log.d("response raw message",response.raw().message());
                    Log.d("response raw message",String.valueOf(response.body()));
                    progressDoalog.dismiss();
                    finish();
                }else{
                    Log.d("response code",String.valueOf(response.code()));
                    Log.d("response raw",String.valueOf(response.raw()));
                    //Log.d("response body",String.valueOf(response.body().getCOD_ORCAMENTO()));
                    Log.d("response raw message",response.raw().message());
                    Log.d("response raw message",String.valueOf(response.body()));
                    progressDoalog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Orcamentos> call, Throwable t) {
                alertaDialog("Ops","Não foi possível salvar seu endereço ):");
                progressDoalog.dismiss();
            }
        });

    }

    private void verificaEnderecoDefinido() {
        if(EnderecoDef.valida == 1)
        {
            txt_rua_esc_casa.setText(EnderecoDef.RUA+", "+EnderecoDef.NUMERO);
            txt_rua_esc_casa.setVisibility(VISIBLE);
            txt_bairro_esc_casa.setText(EnderecoDef.BAIRRO+" - "+EnderecoDef.ESTADO);
            txt_bairro_esc_casa.setVisibility(VISIBLE);
            txt_rua_esc_loja.setVisibility(GONE);
            txt_bairro_esc_loja.setVisibility(GONE);
        }if(EnderecoDef.valida == 2){
            txt_rua_esc_loja.setText(EnderecoDef.RUA+", "+EnderecoDef.NUMERO);
            txt_rua_esc_loja.setVisibility(VISIBLE);
            txt_bairro_esc_loja.setText(EnderecoDef.BAIRRO+" - "+EnderecoDef.ESTADO);
            txt_bairro_esc_loja.setVisibility(VISIBLE);
            txt_rua_esc_casa.setVisibility(GONE);
            txt_bairro_esc_casa.setVisibility(GONE);
        }
    }

    private void excluirAnexo() {
        img_anexo_excolhido.setVisibility(GONE);
        txt_receita.setVisibility(GONE);
        txt_receita_info.setVisibility(GONE);
        img_excluir_anexo.setVisibility(GONE);
        img_face_triste.setVisibility(VISIBLE);
        txt_anexo_cam.setVisibility(VISIBLE);
        linha_anexo.setBackgroundColor(getResources().getColor(R.color.txt_fab_orc));
        txt_anexo.setTextColor(getResources().getColor(R.color.txt_fab_orc));
        fab_anexo.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.txt_fab_orc)));
        confirm_anexo = false;
    }

    private void btninserirAnexo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alterar foto");
        builder.setItems(array, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    TirarFoto();
                } else if (item == 1) {
                    AbrirGaleria();
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void AbrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), request_galeria);
    }

    public void TirarFoto() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        this.startActivityForResult(intent, request_camera);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == request_camera) {
                if (data != null) {
                    //Bundle bundle = data.getExtras();
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        Bitmap img_camera = (Bitmap) bundle.get("data");
                        img_anexo_excolhido.setImageBitmap(img_camera);
                        vereficaLocalFoto("Imagem da Câmera");
                        this.encodedString64 = converterImage.bitMapToBase64(img_camera); // retorna string base 64
                        Log.d("bitmap to string 64", encodedString64);

                    }
                }
            } else if (requestCode == request_galeria) {
                Uri imagemSelecionada = data.getData();
                Bitmap img_galeria;
                progressDoalog.setMessage("Carregando Imagem...");
                progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                // show it
                progressDoalog.show();
                try {
                    img_galeria = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imagemSelecionada);
                    this.encodedString64 = converterImage.bitMapToBase64(img_galeria); // retorna string base 64
                    Log.d("bitmap to string 64", encodedString64);
                    progressDoalog.dismiss();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Glide.with(this).load(imagemSelecionada).into(img_anexo_excolhido);
                vereficaLocalFoto("Imagem da Galeria");

            }
        }
    }

    private void vereficaLocalFoto(String s) {
        txt_anexo_cam.setVisibility(GONE);
        img_face_triste.setVisibility(GONE);
        txt_receita.setVisibility(VISIBLE);
        txt_receita_info.setVisibility(VISIBLE);
        txt_receita_info.setText(s);
        img_anexo_excolhido.setVisibility(VISIBLE);
        img_excluir_anexo.setVisibility(VISIBLE);
        txt_anexo.setTextColor(corAnexo);
        linha_anexo.setBackgroundColor(getResources().getColor(R.color.fab_anexo));
        fab_anexo.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.fab_anexo)));
        confirm_anexo = true;
    }

    private int getPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

    private void definirEntrega() {
        ll_entrega.setVisibility(VISIBLE);
        ll_anexo.setVisibility(GONE);
        ll_obs.setVisibility(GONE);

        if(edt_obs.getText().length() > 0)
        {
            linha_obs.setBackgroundColor(getResources().getColor(R.color.fab_anexo));
            fab_obs.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.fab_anexo)));
            txt_obs.setTextColor(getResources().getColor(R.color.fab_anexo));

        }else
        {
            linha_obs.setBackgroundColor(getResources().getColor(R.color.txt_fab_orc));
            fab_obs.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.txt_fab_orc)));
            txt_obs.setTextColor(getResources().getColor(R.color.txt_fab_orc));
        }

        LinearLayout.LayoutParams paramsEntrega = (LinearLayout.LayoutParams) ll_todo_entrega.getLayoutParams();
        paramsEntrega.width = getPx(190);
        paramsEntrega.height = getPx(70);
        paramsEntrega.gravity = Gravity.CENTER_HORIZONTAL;
        ll_todo_entrega.setLayoutParams(paramsEntrega);

        LinearLayout.LayoutParams paramsAnexo = (LinearLayout.LayoutParams) ll_todo_anexo.getLayoutParams();
        paramsAnexo.width = getPx(190);
        paramsAnexo.height = getPx(110);
        paramsAnexo.gravity = Gravity.CENTER_HORIZONTAL;
        ll_todo_anexo.setLayoutParams(paramsAnexo);

        LinearLayout.LayoutParams paramsObs = (LinearLayout.LayoutParams) ll_todo_obs.getLayoutParams();
        paramsObs.width = getPx(190);
        paramsObs.height = getPx(110);
        paramsObs.gravity = Gravity.CENTER_HORIZONTAL;
        ll_todo_obs.setLayoutParams(paramsObs);
    }

    private void inserirObs() {
        ll_obs.setVisibility(VISIBLE);
        ll_anexo.setVisibility(GONE);
        ll_entrega.setVisibility(GONE);

        if(edt_obs.getText().length() > 0)
        {
            linha_obs.setBackgroundColor(getResources().getColor(R.color.fab_anexo));
            fab_obs.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.fab_anexo)));
            txt_obs.setTextColor(getResources().getColor(R.color.fab_anexo));
            confirm_obs = true;

        }else
        {
            linha_obs.setBackgroundColor(getResources().getColor(R.color.txt_fab_orc));
            fab_obs.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.txt_fab_orc)));
            txt_obs.setTextColor(getResources().getColor(R.color.txt_fab_orc));
            confirm_obs = false;
        }

        LinearLayout.LayoutParams paramsEntrega = (LinearLayout.LayoutParams) ll_todo_entrega.getLayoutParams();
        paramsEntrega.width = getPx(190);
        paramsEntrega.height = getPx(110);
        paramsEntrega.gravity = Gravity.CENTER_HORIZONTAL;
        ll_todo_entrega.setLayoutParams(paramsEntrega);

        LinearLayout.LayoutParams paramsAnexo = (LinearLayout.LayoutParams) ll_todo_anexo.getLayoutParams();
        paramsAnexo.width = getPx(190);
        paramsAnexo.height = getPx(110);
        paramsAnexo.gravity = Gravity.CENTER_HORIZONTAL;
        ll_todo_anexo.setLayoutParams(paramsAnexo);

        LinearLayout.LayoutParams paramsObs = (LinearLayout.LayoutParams) ll_todo_obs.getLayoutParams();
        paramsObs.width = getPx(190);
        paramsObs.height = getPx(90);
        paramsObs.gravity = Gravity.CENTER_HORIZONTAL;
        ll_todo_obs.setLayoutParams(paramsObs);
    }

    private void inserirAnexo() {
        ll_anexo.setVisibility(VISIBLE);
        ll_obs.setVisibility(GONE);
        ll_entrega.setVisibility(GONE);

        if(edt_obs.getText().length() > 0)
        {
            linha_obs.setBackgroundColor(getResources().getColor(R.color.fab_anexo));
            fab_obs.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.fab_anexo)));
            txt_obs.setTextColor(getResources().getColor(R.color.fab_anexo));
            confirm_obs = true;

        }else
        {
            linha_obs.setBackgroundColor(getResources().getColor(R.color.txt_fab_orc));
            fab_obs.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.txt_fab_orc)));
            txt_obs.setTextColor(getResources().getColor(R.color.txt_fab_orc));
            confirm_obs = false;
        }

        if(img_face_triste.getVisibility()==View.INVISIBLE)
        {
            fab_anexo.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.txt_fab_orc)));
            txt_anexo.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.txt_fab_orc)));
        }

        LinearLayout.LayoutParams paramsEntrega = (LinearLayout.LayoutParams) ll_todo_entrega.getLayoutParams();
        paramsEntrega.width = getPx(190);
        paramsEntrega.height = getPx(110);
        paramsEntrega.gravity = Gravity.CENTER_HORIZONTAL;
        ll_todo_entrega.setLayoutParams(paramsEntrega);

        LinearLayout.LayoutParams paramsAnexo = (LinearLayout.LayoutParams) ll_todo_anexo.getLayoutParams();
        paramsAnexo.width = getPx(190);
        paramsAnexo.height = getPx(90);
        paramsAnexo.gravity = Gravity.CENTER_HORIZONTAL;
        ll_todo_anexo.setLayoutParams(paramsAnexo);

        LinearLayout.LayoutParams paramsObs = (LinearLayout.LayoutParams) ll_todo_obs.getLayoutParams();
        paramsObs.width = getPx(190);
        paramsObs.height = getPx(110);
        paramsObs.gravity = Gravity.CENTER_HORIZONTAL;
        ll_todo_obs.setLayoutParams(paramsObs);
    }

    private void alertaDialog(String titulo, String mensagem) {
        AlertDialog alerta = new AlertDialog.Builder(this).create();
        alerta.setTitle(titulo);
        alerta.setMessage(mensagem);
        alerta.setIcon(R.mipmap.ic_atencao);
        alerta.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

    private void verificaCampos(final EditText edt_text){
        edt_text.addTextChangedListener(new TextWatcher() {
            // depois de terminar de escrever
            public void afterTextChanged(Editable s) {
            }

            // antes da ultima edição
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            //edição em tempo real
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edt_text.getText().length() > 1) {
                    confirm_obs = true;
                }else{
                    confirm_obs = false;
                }
            }
        });
    }

    private void DefinirEntrega(int id){
        if(id == 1)
        {
            rb_casa.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_home_orc_roxo, 0, 0, 0);
            rb_loja.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_loja_retirar, 0, 0, 0);
            linha_entrega.setBackgroundColor(getResources().getColor(R.color.fab_anexo));
            txt_entrega.setTextColor(getResources().getColor(R.color.fab_anexo));
            //rb_casa.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(Activity_novo_orc.this,R.color.fab_entrega)));
            fab_entrega.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(Activity_novo_orc.this,R.color.fab_anexo)));
            Intent it = new Intent(Activity_novo_orc.this,Activity_definir_end_entrega.class);
            it.putExtra("int_value",id);
            startActivity(it);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }else{
            linha_entrega.setBackgroundColor(getResources().getColor(R.color.fab_anexo));
            txt_entrega.setTextColor(getResources().getColor(R.color.fab_anexo));
            fab_entrega.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(Activity_novo_orc.this,R.color.fab_anexo)));
            rb_casa.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_receber_home, 0, 0, 0);
            rb_loja.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_maps_pin_roxo, 0, 0, 0);
            Intent i = new Intent(Activity_novo_orc.this,Activity_definir_end_entrega.class);
            i.putExtra("int_value",id);
            startActivity(i);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        verificaEnderecoDefinido();
    }
}
