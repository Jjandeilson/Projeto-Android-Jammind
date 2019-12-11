package com.example.jammind.api;

import com.example.jammind.model.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UsuarioInterface {

    @POST("novo")
    Call<Usuario> salvarUsuario(@Body Usuario usuario);

    @POST("buscar")
    Call<Usuario> buscarUsuario(@Body Usuario usuario);

    @PATCH("{id}/sala")
    Call<Usuario> cadastrarNaSala(@Path("id") Integer idUsuario, @Body Usuario usuario);

    @GET("{idSala}/alunos")
    Call<List<Usuario>> listaDeAlunos(@Path("idSala") Integer idSala);
}
