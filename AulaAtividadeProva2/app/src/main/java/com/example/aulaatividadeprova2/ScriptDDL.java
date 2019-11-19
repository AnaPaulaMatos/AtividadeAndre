package com.example.aulaatividadeprova2;

public class ScriptDDL {
    public static String getCreateTableContato(){

        StringBuilder sql = new StringBuilder();

        sql.append("CREATE TABLE IF NOT EXISTS ITEM (");
        sql.append("  CODIGO INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,");
        sql.append("  NOME VARCHAR(255) NOT NULL DEFAULT (''),");
        sql.append("  TELEFONE VARCHAR(255) NOT NULL DEFAULT (''))");

        return sql.toString();

    }

}
