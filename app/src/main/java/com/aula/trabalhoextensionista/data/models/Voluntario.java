package com.aula.trabalhoextensionista.data.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Voluntario implements Serializable {//Implements Serializable -- PARA PASSAR DADOS ENTRE TELAS{
    @PrimaryKey
    @NonNull
    @Exclude//Nao manda pro fireabase com EXCLUDE
    String id = "";//Usa o ID do firebase

    @NonNull
    public String nome;
    String email;
    String senha;
    public String dataNascimento;
    public String interesses;
    String pais;
    String estado;
    String cidade;
    String telefone;

    private boolean destaque;

    public Voluntario() {

    }

    public Voluntario(@NonNull String id, @NonNull String nome, String email, String senha, String dataNascimento, String interesses, String pais, String estado, String cidade, String telefone) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dataNascimento = dataNascimento;
        this.interesses = interesses;
        this.pais = pais;
        this.estado = estado;
        this.cidade = cidade;
        this.telefone = telefone;
    }

    public Voluntario(@NonNull String nome, String email, String senha, String dataNascimento, String interesses, String pais, String estado, String cidade, String telefone) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dataNascimento = dataNascimento;
        this.interesses = interesses;
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

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getInteresses() {
        return interesses;
    }

    public void setInteresses(String interesses) {
        this.interesses = interesses;
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
        return "Voluntario{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", dataNascimento='" + dataNascimento + '\'' +
                ", interesses='" + interesses + '\'' +
                ", pais='" + pais + '\'' +
                ", estado='" + estado + '\'' +
                ", cidade='" + cidade + '\'' +
                ", telefone='" + telefone + '\'' +
                '}';
    }

//endregion
}
