package com.pharmamobi.joao.rotas.Interfaces;

import com.pharmamobi.joao.rotas.model.AcessoLogin;
import com.pharmamobi.joao.rotas.model.Clientes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by joao on 21/09/2017.
 */

public interface AcessoLoginApi {
    @POST("{post}")
    Call<AcessoLogin>postAcessoLogin(@Body AcessoLogin acessoLogin);

}
