package com.example.jammind.api;

import com.example.jammind.model.AlunoMural;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AlunoMuralInterface {

    @POST("salvar")
    Call<Void> salvarTexto(@Body AlunoMural alunoMural);

}
