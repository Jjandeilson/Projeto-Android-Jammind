package com.example.jammind;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.FragmentActivity;

public class ArmazenaNoApp {

    private SharedPreferences shared;
    private Context context;
    private FragmentActivity fragment;
    private final String LOCAL_DE_ARMAZENAMENTO = "valor.armazenado";
    private final String CHAVE_VALOR = "id";
    private final String NOME_USUARIO = "nome";
    private final String EMAIL_USUARIO = "email";
    private final String SOBRENOME = "sobrenome";

    private SharedPreferences.Editor editor;

    public ArmazenaNoApp(Context context){
        this.context = context;
        shared = context.getSharedPreferences(LOCAL_DE_ARMAZENAMENTO, 0);
        editor = shared.edit();
    }

    public ArmazenaNoApp(FragmentActivity fragment){
        this.fragment = fragment;
        shared = fragment.getSharedPreferences(LOCAL_DE_ARMAZENAMENTO,0);
        editor = shared.edit();
    }

    public void salvarShared(Integer id, String nome, String email, String sobrenome){
        editor.putInt(CHAVE_VALOR, id);
        editor.putString(NOME_USUARIO, nome);
        editor.putString(EMAIL_USUARIO, email);
        editor.putString(SOBRENOME, sobrenome);
        editor.commit();
    }

    public Integer recuperarShared(){
        return shared.getInt(CHAVE_VALOR, 0);
    }

    public String recuperarNome(){
        return shared.getString(NOME_USUARIO, String.valueOf(0));
    }

    public String recuperarEmail(){
        return shared.getString(EMAIL_USUARIO, String.valueOf(1));
    }

    public String recuperarSobrenome(){
        return shared.getString(SOBRENOME, String.valueOf(2));
    }
}
