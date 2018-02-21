package com.pharmamobi.joao.rotas;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.pharmamobi.joao.rotas.Domain.WebApiUrl;
import com.pharmamobi.joao.rotas.Interfaces.AcessoLoginApi;
import com.pharmamobi.joao.rotas.activity.Activity_home_motoboy;
import com.pharmamobi.joao.rotas.model.AcessoLogin;
import com.pharmamobi.joao.rotas.model.Clientes;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.github.florent37.materialtextfield.MaterialTextField;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ActivityLogin extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {
    private WebApiUrl webApi = new WebApiUrl();
    private String URL_BASE = webApi.GetUrlBase();
    private ProgressDialog progressDialog;
    private CallbackManager callbackManager; //facebook
    private AccessTokenTracker acessoTokenTracker; // facebook
    private ProfileTracker perfilTracker; //facebook
    private EditText edt_usuario, edt_senha;
    private TextView txtCadastro, txt_info_user, txt_info_senha;
    private MaterialTextField mtf_usuario, mtf_senha;
    int red, green;


    //google
    private GoogleApiClient googleApiCliente;
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //transitions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Explode trans1 = new Explode();
            trans1.setDuration(1000);
            Slide trans2 = new Slide();
            trans2.setDuration(1000);

            getWindow().setExitTransition(trans1);
            getWindow().setReenterTransition(null);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        edt_usuario = (EditText) findViewById(R.id.edt_usuario);
        edt_senha = (EditText) findViewById(R.id.edt_senha);
        txt_info_user = (TextView) findViewById(R.id.txt_info_usuario);
        txt_info_senha = (TextView) findViewById(R.id.txt_info_senha);
        txtCadastro = (TextView) findViewById(R.id.btn_cadastro);
        mtf_usuario = (MaterialTextField) findViewById(R.id.edt_mtf_usuario);
        mtf_senha = (MaterialTextField) findViewById(R.id.edt_mtf_senha);

        txtCadastro.setPaintFlags(txtCadastro.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        red = Color.RED;
        green = Color.parseColor("#FF25AB11");

        edt_usuario.addTextChangedListener(new TextWatcher() {
            // depois de terminar de escrever
            public void afterTextChanged(Editable s) {
            }

            // antes da ultima edição
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            //edição em tempo real
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count < 1) {
                    txt_info_user.setText("Digite sua email cadastrado.");
                    txt_info_user.setTextColor(red);
                } else {
                    txt_info_user.setTextColor(green);
                }
            }
        });
        edt_senha.addTextChangedListener(new TextWatcher() {
            // depois de terminar de escrever
            public void afterTextChanged(Editable s) {
            }

            // antes da ultima edição
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            //edição em tempo real
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().length() < 1) {
                    txt_info_senha.setText("Digite sua senha cadastrada.");
                    txt_info_senha.setTextColor(red);
                } else {
                    txt_info_senha.setTextColor(green);
                }
            }
        });

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken novoAcessoToke = loginResult.getAccessToken();
                Profile novoPerfil = Profile.getCurrentProfile();
                LoginFacebookRealizado(novoPerfil);
                Toast.makeText(getApplicationContext(), "Entrando...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        /*----------------- rastreando token de acesso e perfil ------------------------*/
        acessoTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken antigoToken, AccessToken novoToken) {
            }
        };

        acessoTokenTracker.startTracking();

        perfilTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile antigoProfile, Profile novoProfileTraker) {
                LoginFacebookRealizado(novoProfileTraker);
            }
        };
        perfilTracker.startTracking();

        //LOGIN GOOGLE
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiCliente = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, null)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cadastro:
                Cadastro();
                break;
            case R.id.btn_login_padrao:
                if (!verificaEdtText(edt_usuario) && !verificaEdtText(edt_senha)) {
                    txt_info_user.setText("Digite seu email cadastrado.");
                    txt_info_senha.setText("Digite sua senha cadastrada.");
                    txt_info_user.setTextColor(red);
                    txt_info_senha.setTextColor(red);
                    alertaDialog("Atenção", "Por favor, preencha os campos USUARIO e SENHA. ");
                    mtf_senha.setHasFocus(true);
                    mtf_usuario.setHasFocus(true);
                    break;
                }
                if (!verificaEdtText(edt_usuario) && verificaEdtText(edt_senha)) {
                    txt_info_user.setText("Digite seu email cadastrado.");
                    txt_info_user.setTextColor(red);
                    alertaDialog("Atenção", "Por favor, preencha o campo USUARIO. ");
                    mtf_usuario.setHasFocus(true);
                    break;
                }
                if (verificaEdtText(edt_usuario) && !verificaEdtText(edt_senha)) {
                    txt_info_senha.setText("Digite sua senha cadastrada.");
                    txt_info_senha.setTextColor(red);
                    alertaDialog("Atenção", "Por favor, preencha o campo SENHA. ");
                    mtf_senha.setHasFocus(true);
                    break;
                }
                if (verificaEdtText(edt_usuario) && verificaEdtText(edt_senha)) {
                    LoginPadrao(edt_usuario.getText().toString(),edt_senha.getText().toString());
                }
                break;
            case R.id.btn_google:
                LoginGoogle();
                break;
            case R.id.btn_facebook:
                LoginFacebook();
                break;
        }
    }

    private void Cadastro() {

        Intent i = new Intent(this, ActivityCadastro.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, null);
            startActivity(i, options.toBundle());
        } else {
            startActivity(i);
        }
    }

    private void LoginPadrao(String user, String senha) {
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(this);
        progressDoalog.setMessage("Aguarde......");
        progressDoalog.setTitle("Verificando acesso");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE + "AcessoLogin/VerificaLogin/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AcessoLoginApi loginResource = retrofit.create(AcessoLoginApi.class);
        AcessoLogin login = new AcessoLogin(user,senha);

        Call<AcessoLogin> call = loginResource.postAcessoLogin(login);
        call.enqueue(new Callback<AcessoLogin>() {
            @Override
            public void onResponse(Call<AcessoLogin> call, Response<AcessoLogin> response) {
                if(response.isSuccessful())
                {
                    AcessoLogin login = response.body();
                    Log.d("TAG start login id boy",String.valueOf(login.getID()));
                    Log.d("response code",String.valueOf(response.code()));
                    Log.d("response raw",String.valueOf(response.raw()));
                    Log.d("response body",String.valueOf(response.body().getID()));
                    Log.d("response raw message",response.raw().message());
                    progressDoalog.dismiss();
                    Intent it = new Intent(ActivityLogin.this,Activity_home_motoboy.class);
                    it.putExtra("id_login",String.valueOf(login.getID()));
                    startActivity(it);
                }else{
                    alertaDialog("OK","Cadastro realizado com sucesso :)");
                    Log.d("response code",String.valueOf(response.code()));
                    Log.d("response raw",String.valueOf(response.raw()));
                    Log.d("response body",String.valueOf(response.body().getID()));
                    Log.d("response raw message",response.raw().message());
                    progressDoalog.dismiss();
                    alertaDialog("Ops","Login ou senha incorretos ):");
                }
            }

            @Override
            public void onFailure(Call<AcessoLogin> call, Throwable t) {
                alertaDialog("Falha","Alguma coisa deu errado. Tente novamente.");
                progressDoalog.dismiss();
            }
        });
    }

    private void LoginGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiCliente);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void LoginFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends", "email"));
    }

    @Override
    public void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiCliente);
        if (opr.isDone()) {
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
        LoginFacebookRealizado(profile);
    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    protected void onStop() {
        super.onStop();
        acessoTokenTracker.stopTracking();
        perfilTracker.stopTracking();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN && resultCode == RESULT_OK) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("Entrando em", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            LoginGoogleRealizado(acct);
        }
    }

    private void LoginGoogleRealizado(GoogleSignInAccount acct) {
        if (acct != null) {
            Intent intent = new Intent(this, MenuPrincipal.class);
            intent.putExtra("nome", acct.getGivenName());
            intent.putExtra("email", acct.getEmail());
            intent.putExtra("imageUrl", acct.getPhotoUrl());
            startActivity(intent);
        }
    }

    private void LoginFacebookRealizado(Profile profile) {
        if (profile != null) {
            String nome = profile.getFirstName() + " " + profile.getLastName();
            Intent intent = new Intent(this, MenuPrincipal.class);
            intent.putExtra("nome", nome);
            intent.putExtra("imageUrl", profile.getProfilePictureUri(200, 200));
            intent.putExtra("email", "joao_vitormt@hotmail.com");
            startActivity(intent);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        AlertDialog alertError = new AlertDialog.Builder(this).create();
        alertError.setTitle("Falha");
        alertError.setMessage(connectionResult.getErrorMessage());
        alertError.show();
    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Carregando");
            progressDialog.setIndeterminate(true);
        }
    }

    private void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }

    public boolean verificaEdtText(EditText edtText) {
        String txt = edtText.getText().toString().trim();
        if (txt.length() < 1) {
            return false;
        }
        return true;
    }

    private void alertaDialog(String titulo, String mensagem) {
        AlertDialog alerta = new AlertDialog.Builder(this).create();
        alerta.setTitle(titulo);
        alerta.setMessage(mensagem);
        alerta.setIcon(R.mipmap.ic_atencao);
        alerta.show();
    }

    public static void callAlert(String titulo, String menssagem, final Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.shpe_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));


        TextView tvTitle = (TextView) dialog.findViewById(R.id.txt_titulo);
        tvTitle.setText(titulo);

        TextView tvText = (TextView) dialog.findViewById(R.id.txt_mensagem);
        tvText.setText(menssagem);

        ImageButton buttonDialogYes = (ImageButton) dialog.findViewById(R.id.btn_ok);
        buttonDialogYes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}