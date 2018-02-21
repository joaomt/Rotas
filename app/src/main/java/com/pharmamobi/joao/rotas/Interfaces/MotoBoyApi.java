package com.pharmamobi.joao.rotas.Interfaces;

import com.pharmamobi.joao.rotas.activity.Activity_detalhes_Orcamento;
import com.pharmamobi.joao.rotas.model.AvaliacaoEntrega;
import com.pharmamobi.joao.rotas.model.Clientes;
import com.pharmamobi.joao.rotas.model.Endereco;
import com.pharmamobi.joao.rotas.model.LocationLatLng;
import com.pharmamobi.joao.rotas.model.MEntregas;
import com.pharmamobi.joao.rotas.model.OrcamentoAux;
import com.pharmamobi.joao.rotas.model.Orcamentos;
import com.pharmamobi.joao.rotas.model.RelacaoEntregas;
import com.pharmamobi.joao.rotas.model.RelacaoEntregasInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by joao on 21/09/2017.
 */

public interface MotoBoyApi {
    @POST("{post}")
    Call<AvaliacaoEntrega>postAvaliacao(@Body AvaliacaoEntrega avaliacao);

    @GET("{id}")
    Call<List<RelacaoEntregas>>getRelacaoEntrega(@Path("id") String id); // buscar Relações

    @GET("{id}")
    Call<List<RelacaoEntregasInfo>>getEntregasRelacao(@Path("id") String id); // buscar Entregas da relação

    @POST("{post}")
    Call<LocationLatLng>postLocation(@Body LocationLatLng locationLatLng);// enviar localização do motoboy

}
