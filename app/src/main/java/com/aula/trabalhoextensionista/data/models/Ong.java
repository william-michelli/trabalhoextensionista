package com.aula.trabalhoextensionista.data.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

@Entity(tableName = "ong")
public class Ong implements Serializable {//Implements Serializable -- PARA PASSAR DADOS ENTRE TELAS

    @PrimaryKey
    @NonNull
    @Exclude//Nao manda pro fireabase com EXCLUDE
    String id = "";//Usa o ID do firebase
    @NonNull
    String nome;
    String email;
    String senha;
    String necessidades; //O que a ONG precisa que façam
    String localizacao;
    String telefone;

    public Ong() { }

    public Ong(@NonNull String id, @NonNull String nome, String necessidade,
               String email, String localizacao, String senha, String telefone){
        this.id = id;
        this.nome = nome;
        this.necessidades = necessidade;
        this.email = email;
        this.localizacao = localizacao;
        this.senha = senha;
        this.telefone = telefone;
    }

    public Ong(@NonNull String nome, String necessidades, String email,
               String localizacao, String senha, String telefone){
        this.nome = nome;
        this.necessidades = necessidades;
        this.email = email;
        this.localizacao = localizacao;
        this.senha = senha;
        this.telefone = telefone;
    }

    //region GETTERS, SETTERS, TOSTRING

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getNome() {
        return nome;
    }

    public void setNome(@NonNull String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNecessidades() {
        return necessidades;
    }

    public void setNecessidades(String necessidades) {
        this.necessidades = necessidades;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "Ong{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", necessidades='" + necessidades + '\'' +
                ", localizacao='" + localizacao + '\'' +
                ", telefone='" + telefone + '\'' +
                '}';
    }

//endregion
}
