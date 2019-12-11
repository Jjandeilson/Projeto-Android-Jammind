package com.example.jammind.api;

import com.example.jammind.model.Sala;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SalaInterface {

    @POST("novo")
    Call<Sala> salvar(@Body Sala sala);

    @GET("buscar/{id}")
    Call<List<Sala>> buscarSalas(@Path("id") Integer id);

    @DELETE("{idAluno}/remover")
    Call<Void> removerAluno(@Path("idAluno") Integer idAluno);

    @PATCH("novo")
    Call<Void> editarSala(@Body Sala sala);
}
