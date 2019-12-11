package com.example.jammind.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Objects;

public class Tema implements Serializable {

    private Integer idTema;
    private String nomeTema;
    private String descricaoTema;
    private boolean ativo;

    public Tema() {
    }

    public Integer getIdTema() {
        return idTema;
    }

    public void setIdTema(Integer idTema) {
        this.idTema = idTema;
    }

    public String getNomeTema() {
        return nomeTema;
    }

    public void setNomeTema(String nomeTema) {
        this.nomeTema = nomeTema;
    }

    public String getDescricaoTema() {
        return descricaoTema;
    }

    public void setDescricaoTema(String descricaoTema) {
        this.descricaoTema = descricaoTema;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tema tema = (Tema) o;
        return idTema.equals(tema.idTema);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(idTema);
    }
}
