package com.pharmamobi.joao.rotas.Interfaces;

import com.pharmamobi.joao.rotas.activity.Activity_buscar_end_cep;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetEnderecoApi {
    @GET("{cep}")
    Call<Activity_buscar_end_cep.EnderecoCep>getCep(@Path("cep") String cep);
}
