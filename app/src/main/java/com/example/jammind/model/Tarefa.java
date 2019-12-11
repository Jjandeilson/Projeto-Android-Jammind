package com.example.jammind.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Objects;

public class Tarefa implements Serializable {

    private Integer idTarefa;
    private String nomeTarefa;
    private String nomePergunta;
    private String opcaoA;
    private String opcaoB;
    private String opcaoC;
    private String opcaoD;
    private Character resposta;
    private boolean concluido;

    public Tarefa() {
    }

    public Integer getIdTarefa() {
        return idTarefa;
    }

    public void setIdTarefa(Integer idTarefa) {
        this.idTarefa = idTarefa;
    }

    public String getNomeTarefa() {
        return nomeTarefa;
    }

    public void setNomeTarefa(String nomeTarefa) {
        this.nomeTarefa = nomeTarefa;
    }

    public String getNomePergunta() {
        return nomePergunta;
    }

    public void setNomePergunta(String nmePergunta) {
        this.nomePergunta = nmePergunta;
    }

    public String getOpcaoA() {
        return opcaoA;
    }

    public void setOpcaoA(String opcaoA) {
        this.opcaoA = opcaoA;
    }

    public String getOpcaoB() {
        return opcaoB;
    }

    public void setOpcaoB(String opcaoB) {
        this.opcaoB = opcaoB;
    }

    public String getOpcaoC() {
        return opcaoC;
    }

    public void setOpcaoC(String opcaoC) {
        this.opcaoC = opcaoC;
    }

    public String getOpcaoD() {
        return opcaoD;
    }

    public void setOpcaoD(String opcaoD) {
        this.opcaoD = opcaoD;
    }

    public Character getResposta() {
        return resposta;
    }

    public void setResposta(Character resposta) {
        this.resposta = resposta;
    }

    public boolean isConcluido() {
        return concluido;
    }

    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tarefa tarefa = (Tarefa) o;
        return idTarefa.equals(tarefa.idTarefa);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(idTarefa);
    }
}
