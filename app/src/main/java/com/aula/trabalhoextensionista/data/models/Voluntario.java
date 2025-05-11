package com.aula.trabalhoextensionista.data.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Voluntario {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    public String nome;
    public int idade;
    public String descricao;

    //region GETTERS, SETTERS, TOSTRING
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getNome() {
        return nome;
    }

    public void setNome(@NonNull String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Voluntario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", idade=" + idade +
                ", descricao='" + descricao + '\'' +
                '}';
    }

    //endregion
}
