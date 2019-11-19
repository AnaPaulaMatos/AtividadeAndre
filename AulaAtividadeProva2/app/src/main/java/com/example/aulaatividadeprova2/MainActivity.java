package com.example.aulaatividadeprova2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Item> listaDados;
    RecyclerView recycle;

    private SQLiteDatabase conexao;
    private DadosOpenHelper dadosOpenHelper;
    private EditText idNome;
    private EditText idTelefone;
    private FloatingActionButton fabAdicionar, fabEditar, fabExcluir;
    private ItemRepositorio itemRepositorio;
    private Integer codigo;


    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idNome = findViewById(R.id.idNome);
        idTelefone = findViewById(R.id.idTelefone);

        recycle = findViewById(R.id.RecyclerAtividade);
        recycle.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));

        fabAdicionar = findViewById(R.id.fabAdicionar);
        fabEditar = findViewById(R.id.fabEditar);
        fabExcluir = findViewById(R.id.fabExcluir);

        fabAdicionar.show();
        fabEditar.hide();
        fabExcluir.hide();

        listaDados = new ArrayList<Item>();

        criarConexao();

        listaDados = itemRepositorio.buscarTodos();

        if (listaDados != null) {
            AdapterDados adapter = new AdapterDados(listaDados);
            recycle.setAdapter(adapter);

        }

        Bundle bundle = getIntent().getExtras();

        Item item = new Item();

        if (bundle != null && bundle.containsKey("DADOS")) {

            fabAdicionar.show();
            fabEditar.hide();
            fabExcluir.hide();

            item = (Item) bundle.getSerializable("DADOS");
            codigo = item.getCodigo();
            idNome.setText(item.getNome());
            idTelefone.setText(item.getTelefone());

        }

        fabAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                criarConexao();

                Item item = new Item();

                item.setNome(idNome.getText().toString());
                item.setTelefone(idTelefone.getText().toString());

                if ((idNome.getText().toString().isEmpty()) || (idTelefone.getText().toString().isEmpty())) {

                    Toast.makeText(getApplicationContext(), "Nome e telefone obrigatórios!", Toast.LENGTH_LONG).show();

                } else {

                    itemRepositorio.inserir(item);

                    Toast.makeText(getApplicationContext(), "Inclusão efetuada com sucesso!", Toast.LENGTH_LONG).show();

                }

                Intent it = new Intent(MainActivity.this, MainActivity.class);//como está destroindo a Activity precisa chamar uma nova Activity dentro do botão incluir

                listaDados = itemRepositorio.buscarTodos();

                if (listaDados != null) {
                    AdapterDados adapter = new AdapterDados(listaDados);
                    recycle.setAdapter(adapter);

                }

                startActivity(it);
                finish();
            }
        });


        fabEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                criarConexao();

                Item item = new Item();

                item.setCodigo(codigo);
                item.setNome(idNome.getText().toString());
                item.setTelefone(idTelefone.getText().toString());

                if((idNome.getText().toString().isEmpty()) || (idTelefone.getText().toString().isEmpty())){

                    Toast.makeText(getApplicationContext(), "Nome e telefone obrigatórios!", Toast.LENGTH_LONG).show();

                }else{

                    itemRepositorio.alterar(item);

                    Toast.makeText(getApplicationContext(), "Alteração efetuada com sucesso!", Toast.LENGTH_LONG).show();

                }

                Intent it = new Intent(MainActivity.this, MainActivity.class);//como está destroindo a Activity precisa chamar uma nova Activity dentro do botão incluir

                listaDados = itemRepositorio.buscarTodos();

                if (listaDados != null){
                    AdapterDados adapter = new AdapterDados(listaDados);
                    recycle.setAdapter(adapter);

                }

                startActivity(it);
                finish();

            }
        });


        fabExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                criarConexao();

                itemRepositorio.excluir(codigo);

                Toast.makeText(getApplicationContext(), "Exclusão efetuada com sucesso!", Toast.LENGTH_SHORT).show();

                Intent it = new Intent(MainActivity.this, MainActivity.class);

                listaDados = itemRepositorio.buscarTodos();

                if (listaDados != null) {
                    AdapterDados adapter = new AdapterDados(listaDados);
                    recycle.setAdapter(adapter);

                }

                startActivity(it);
                finish();

            }
        });

    }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {

            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu, menu);

            return super.onCreateOptionsMenu(menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

            int id = item.getItemId();

            switch (id) {
                case R.id.limpar:
                    this.idNome.setText("");
                    this.idTelefone.setText("");
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }

        private void criarConexao() {

            try {
                dadosOpenHelper = new DadosOpenHelper(this);
                conexao = dadosOpenHelper.getWritableDatabase();
                itemRepositorio = new ItemRepositorio(conexao);
            } catch (SQLException e) {
                Toast.makeText(getApplicationContext(), "Problema ao conectar!", Toast.LENGTH_SHORT).show();
            }

        }
}
