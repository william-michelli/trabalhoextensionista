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
    String id = "";//Usa o ID do firebase
    @NonNull
    String nome;
    String email;

    @NonNull
    String detalhes;
    String senha;
    String necessidades; //O que a ONG precisa que façam
    String pais;
    String estado;
    String cidade;
    String telefone;
    private boolean destaque;

    public Ong() { }

    public Ong(@NonNull String id, @NonNull String nome, String email, String senha, String detalhes, String necessidades, String pais, String estado, String cidade, String telefone) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.detalhes = detalhes;
        this.necessidades = necessidades;
        this.pais = pais;
        this.estado = estado;
        this.cidade = cidade;
        this.telefone = telefone;
    }

    public Ong(@NonNull String nome, String email, String senha, String detalhes, String necessidades, String pais, String estado, String cidade, String telefone) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.detalhes = detalhes;
        this.necessidades = necessidades;
        this.pais = pais;
        this.estado = estado;
        this.cidade = cidade;
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

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    @NonNull
    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(@NonNull String detalhes) {
        this.detalhes = detalhes;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public boolean isDestaque() {
        return destaque;
    }

    public void setDestaque(boolean destaque) {
        this.destaque = destaque;
    }

    @Override
    public String toString() {
        return "Ong{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", necessidades='" + necessidades + '\'' +
                ", detalhes='" + detalhes + '\'' +
                ", pais='" + pais + '\'' +
                ", estado='" + estado + '\'' +
                ", cidade='" + cidade + '\'' +
                ", telefone='" + telefone + '\'' +
                '}';
    }

//endregion
}
