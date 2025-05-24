package com.aula.trabalhoextensionista.data.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Voluntario implements Serializable {//Implements Serializable -- PARA PASSAR DADOS ENTRE TELAS{
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    public String nome;
    public String dataNascimento;
    public String descricao;

    public String interesses;

    public Voluntario() {

    }

    public Voluntario(@NonNull String nome, String dataNascimento, String descricao, String interesses) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.descricao = descricao;
        this.interesses = interesses;
    }

    public Voluntario(int id, @NonNull String nome, String dataNascimento, String descricao, String interesses) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.descricao = descricao;
        this.interesses = interesses;
    }

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

    public String getDataNascimento() {return dataNascimento;}

    public void setDataNascimento(String dataNascimento) {this.dataNascimento = dataNascimento;}

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getInteresses() { return interesses;}

    public void setInteresses(String interesses) { this.interesses = interesses;}

    @Override
    public String toString() {
        return "Voluntario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", data de nascimento=" + dataNascimento +
                ", descricao='" + descricao + '\'' +
                ", interesses='" + interesses + '\'' +
                '}';
    }

    //endregion
}
