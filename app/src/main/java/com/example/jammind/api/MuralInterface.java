package com.example.jammind.api;

import com.example.jammind.model.Mural;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MuralInterface {

    @GET("buscar/{idTema}/{idAluno}")
    Call<Mural> buscarMural(@Path("idTema") Integer idTema, @Path("idAluno") Integer idAluno);
}
