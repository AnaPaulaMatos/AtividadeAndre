package com.example.aulaatividadeprova2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ItemRepositorio {
    private SQLiteDatabase conexao;

    public ItemRepositorio(SQLiteDatabase conexao){

        this.conexao = conexao;

    }

    public void inserir(Item item){

        ContentValues contentValues = new ContentValues();

        contentValues.put("NOME", item.nome);
        contentValues.put("TELEFONE", item.telefone);

        conexao.insertOrThrow("ITEM", null, contentValues);

    }

    public void excluir(int codigo){

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(codigo);

        conexao.delete("ITEM","CODIGO = ?", parametros);

    }

    public void alterar(Item item){

        ContentValues contentValues = new ContentValues();

        contentValues.put("NOME", item.nome);
        contentValues.put("TELEFONE", item.telefone);

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(item.codigo);

        conexao.update("ITEM", contentValues, "CODIGO = ?", parametros);

    }

    public ArrayList<Item> buscarTodos(){
        ArrayList<Item> itens = new ArrayList<Item>();
        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT CODIGO, NOME, TELEFONE ");
        sql.append(" FROM ITEM ORDER BY NOME");

        Cursor resultado = conexao.rawQuery(sql.toString(), null);

        Item item = new Item();

        if(resultado.getCount() > 0){
            resultado.moveToFirst();

            do{
                item = new Item();
                item.codigo = resultado.getInt( resultado.getColumnIndexOrThrow("CODIGO"));
                item.nome = resultado.getString( resultado.getColumnIndexOrThrow("NOME"));
                item.telefone = resultado.getString( resultado.getColumnIndexOrThrow("TELEFONE"));

                itens.add(item);
            }while(resultado.moveToNext());

            return itens;
        }

        return null;
    }

}
