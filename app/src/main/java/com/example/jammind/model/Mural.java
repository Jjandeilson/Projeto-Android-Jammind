package com.example.jammind.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Objects;

public class Mural implements Serializable {

    private Integer idMural;
    private String nomeMural;
    private String tituloMural;
    private String textoAluno;

    public Mural() {
    }

    public Integer getIdMural() {
        return idMural;
    }

    public void setIdMural(Integer idMural) {
        this.idMural = idMural;
    }

    public String getNomeMural() {
        return nomeMural;
    }

    public void setNomeMural(String nomeMural) {
        this.nomeMural = nomeMural;
    }

    public String getTituloMural() {
        return tituloMural;
    }

    public void setTituloMural(String tituloMural) {
        this.tituloMural = tituloMural;
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
        Mural mural = (Mural) o;
        return idMural.equals(mural.idMural);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(idMural);
    }
}
