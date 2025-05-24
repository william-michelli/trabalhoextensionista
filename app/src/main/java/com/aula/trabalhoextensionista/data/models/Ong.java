package com.aula.trabalhoextensionista.data.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "ong")
public class Ong implements Serializable {//Implements Serializable -- PARA PASSAR DADOS ENTRE TELAS

    @PrimaryKey(autoGenerate = true)
    int id;
    @NonNull
    String nome;
    String descricao;
    String necessidade; //O que a ONG precisa que façam

    public Ong() { }

    public Ong(@NonNull int id, @NonNull String nome, String descricao, String necessidade){
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.necessidade = necessidade;
    }

    public Ong(@NonNull String nome, String descricao, String necessidade){
        this.nome = nome;
        this.descricao = descricao;
        this.necessidade = necessidade;
    }


    //region GETTERS, SETTERS, TOSTRING
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getNome() {
        return nome;
    }

    public void setNome(@NonNull String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNecessidade() {
        return necessidade;
    }

    public void setNecessidade(String necessidade) {
        this.necessidade = necessidade;
    }

    @Override
    public String toString() {
        return "Ong{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", necessidade='" + necessidade + '\'' +
                '}';
    }

    //endregion
}
