package com.pharmamobi.joao.rotas;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentConfiguracao extends Fragment{
    private GoogleApiClient mGoogleApiClient;
    public FragmentConfiguracao() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_configuracao, container, false);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build();
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity()).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        //Button btn_sair_google = (Button)v.findViewById(R.id.btn_google_sair);


        //Button btn_sair_facebook = (Button) v.findViewById(R.id.btn_facebook_sair);

        // Inflate the layout for this fragment
        return v;
    }
    @Override
    public void onStart(){
        super.onStart();
        mGoogleApiClient.connect();
    }
    private void sairFacebook() {
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(getActivity(),ActivityLogin.class);
        startActivity(intent);

    }
    private void sairGoogle(){
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        Intent intent = new Intent(getActivity() , ActivityLogin.class);
                        startActivity(intent);
                    }
                }
        );
    }

}
