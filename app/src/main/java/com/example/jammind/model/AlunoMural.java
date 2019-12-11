package com.example.jammind.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Objects;

public class AlunoMural implements Serializable {

    private Integer idAlunoMural;
    private Usuario usuario;
    private Mural mural;
    private String textoAluno;

    public AlunoMural() {
    }

    public AlunoMural(Usuario usuario, Mural mural, String textoAluno) {
        this.usuario = usuario;
        this.mural = mural;
        this.textoAluno = textoAluno;
    }

    public Integer getIdAlunoMural() {
        return idAlunoMural;
    }

    public void setIdAlunoMural(Integer idAlunoMural) {
        this.idAlunoMural = idAlunoMural;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Mural getMural() {
        return mural;
    }

    public void setMural(Mural mural) {
        this.mural = mural;
    }

    public String getTextoAluno() {
        return textoAluno;
    }

    public void setTextoAluno(String textoAluno) {
        this.textoAluno = textoAluno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlunoMural that = (AlunoMural) o;
        return idAlunoMural.equals(that.idAlunoMural);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(idAlunoMural);
    }
}
