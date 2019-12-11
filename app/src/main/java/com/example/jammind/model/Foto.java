package com.example.jammind.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Foto extends RealmObject {

    @PrimaryKey
    private int idFoto;
    private byte[] foto;

    public int getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(int idFoto) {
        this.idFoto = idFoto;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
}
