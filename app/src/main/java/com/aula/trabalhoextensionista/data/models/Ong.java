package com.aula.trabalhoextensionista.data.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ong")
public class Ong {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    String nome;
    String descricao;
    String necessidade; //O que a ONG precisa que façam

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
