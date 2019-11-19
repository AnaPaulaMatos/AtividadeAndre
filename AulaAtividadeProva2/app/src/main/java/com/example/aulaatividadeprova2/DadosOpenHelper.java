package com.example.aulaatividadeprova2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

public class DadosOpenHelper extends SQLiteOpenHelper {

    public DadosOpenHelper(Context context) {

        super(context, "DADOS", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(ScriptDDL.getCreateTableContato());

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS ITEM");
        onCreate(sqLiteDatabase);

    }
}
