package com.pharmamobi.joao.rotas.Interfaces;

import com.pharmamobi.joao.rotas.FragmentMinhasEntregas;
import com.pharmamobi.joao.rotas.activity.Activity_detalhes_Orcamento;
import com.pharmamobi.joao.rotas.model.AvaliacaoEntrega;
import com.pharmamobi.joao.rotas.model.Clientes;
import com.pharmamobi.joao.rotas.model.Endereco;
import com.pharmamobi.joao.rotas.model.MEntregas;
import com.pharmamobi.joao.rotas.model.OrcamentoAux;
import com.pharmamobi.joao.rotas.model.Orcamentos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by joao on 21/09/2017.
 */

public interface ClientesApi {
    @POST("{post}")
    Call<AvaliacaoEntrega>postAvaliacao(@Body AvaliacaoEntrega avaliacao);

    @POST("{post}")
    Call<MEntregas>ReagendarEntrega(@Body MEntregas entrega);

    @POST("{post}")
    Call<Activity_detalhes_Orcamento.Confir_Orc>putOrcamento(@Body Activity_detalhes_Orcamento.Confir_Orc confirm_orcamento);

    @POST("{post}")
    Call<Clientes>postCliente(@Body Clientes cliente); // salvar cliente

    @POST("{post}")
    Call<Endereco>postEnderecoCliente(@Body Endereco endereco); //salvar endereço cliente

    @POST("{post}")
    Call<Orcamentos>postOrcamentoCliente(@Body Orcamentos orcamento); // salvar orçamento cliente

    @GET("{id}")
    Call<Clientes>getCliente(@Path("id") String id); // buscar cliente

    @GET("{id}")
    Call<List<Endereco>>getEnderecosCliente(@Path("id") String id); // buscar endereços cliente

    @GET("{id}")
    Call<List<OrcamentoAux>>getOrcamentosCliente(@Path("id") String id); //buscar orçamentos cliente

    @GET("{id}")
    Call<List<Orcamentos>>getItensOrcamentosCliente(@Path("id") String id); // buscar itens de um orçamento

    @GET("{id}")
    Call<OrcamentoAux>getOrcamento(@Path("id") String id); // buscar itens de um orçamento

    @GET("{id}")
    Call<List<MEntregas>>getEntregaIdCliente(@Path("id")String id);

    @GET("{id}")
    Call<MEntregas>getEntrega(@Path("id")String id);

    @POST("{post}")
    Call<MEntregas>getEntregaCV(@Body FragmentMinhasEntregas.CliEntrega cliEntrega); // buscar entrega pelo CV e salvando ela para o cliente

    @GET("{id}")
    Call<AvaliacaoEntrega>getAvaliacao(@Path("id") String id); // buscar Avaliação Entrega

    @GET("{id}")
    Call<MEntregas>getEntregaCV_S(@Path("id")String id);

    @GET("{id}")
    Call<String>GetTentativasEntrega(@Path("id")String id);


}
