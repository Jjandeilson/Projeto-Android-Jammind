package com.example.jammind.api;

import com.example.jammind.model.Historico;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface HistoricoInterface {

    @POST("salvar")
    Call<Void> salvarHistorico(@Body Historico historico);

    @GET("tarefas/{idAluno}")
    Call<List<Historico>> listarTarefasConcluidas(@Path("idAluno") Integer idAluno);
}
