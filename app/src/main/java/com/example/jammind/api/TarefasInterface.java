package com.example.jammind.api;

import com.example.jammind.model.Tarefa;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TarefasInterface {

    @GET("{idTema}/tarefas/{idAluno}")
    Call<List<Tarefa>> listaDeTarefas(@Path("idTema") Integer idTema, @Path("idAluno") Integer idAluno);

    @POST("{idTarefa}/resposta")
    Call<Boolean> respostaDoALuno(@Path("idTarefa") Integer idTarefa, @Body String opcao);

    @GET("historico/{idAluno}")
    Call<List<Tarefa>> listarHistorica(@Path("idAluno") Integer idAluno);
}
