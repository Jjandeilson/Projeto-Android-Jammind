package com.example.jammind.api;

import com.example.jammind.model.Tema;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TemaInterface {

    @GET("listar")
    Call<List<Tema>> listarTemas();

    @GET("{id}/temas")
    Call<List<Tema>> listar(@Path("id") Integer idSala);

    @PUT("{idSala}/{idTema}/ativa")
    Call<Tema> ativarTema(@Path("idSala") Integer idSala, @Path("idTema") Integer idTema);

    @PUT("{idSala}/{idTema}/ativa")
    Call<Tema> desativarTema(@Path("idSala") Integer idSala, @Path("idTema") Integer idTema);
}
