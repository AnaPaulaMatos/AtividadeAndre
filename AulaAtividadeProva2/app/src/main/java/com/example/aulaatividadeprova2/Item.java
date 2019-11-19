package com.example.aulaatividadeprova2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.Serializable;

public class Item implements Serializable {

    int codigo;

    String nome, telefone;

    public String getNome() {

        return nome;
    }

    public void setNome(String nome) {

        this.nome = nome;
    }

    public String getTelefone() {

        return telefone;
    }

    public void setTelefone(String telefone) {

        this.telefone = telefone;
    }

    public int getCodigo() {

        return codigo;
    }

    public void setCodigo(int codigo) {

        this.codigo = codigo;
    }

}
