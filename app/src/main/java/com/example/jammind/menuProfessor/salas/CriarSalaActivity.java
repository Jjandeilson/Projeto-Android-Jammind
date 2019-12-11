package com.example.jammind.menuProfessor.salas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.jammind.ArmazenaNoApp;
import com.example.jammind.R;
import com.example.jammind.api.ConverteJson;
import com.example.jammind.api.SalaInterface;
import com.example.jammind.model.Endereco;
import com.example.jammind.model.Sala;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CriarSalaActivity extends AppCompatActivity {

    private EditText titulo, serie, descricao;
    private Button criar, cancelar;
    private Sala sala;
    private ConstraintLayout telaCriarSala;
    private Endereco endereco = new Endereco();
    private Retrofit retrofit = new Retrofit.Builder().baseUrl(endereco.getUrl()+"salas/")
            .addConverterFactory(new ConverteJson())
            .addConverterFactory(GsonConverterFactory.create()).build();
    private ArmazenaNoApp armazenaNoApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_sala);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Criar nova sala");

        Bundle dado = getIntent().getExtras();
        armazenaNoApp = new ArmazenaNoApp(getApplicationContext());

        titulo = findViewById(R.id.tituloSala);
        serie = findViewById(R.id.serieSala);
        descricao = findViewById(R.id.descricaoSala);
        telaCriarSala = findViewById(R.id.telaCarregamentoCriarSala);
        criar = findViewById(R.id.btnCriarSalaProfessor);
        cancelar = findViewById(R.id.btnCancelarSalaProfessor);

        telaCriarSala.setVisibility(View.GONE);
        cancelar.setEnabled(true);

        if(dado != null){
            sala = (Sala) dado.getSerializable("sala");
            titulo.setText(sala.getTitulo());
            serie.setText(sala.getSerie());
            descricao.setText(sala.getDescricao());
            criar.setText("Alterar");
        }else {
            sala = new Sala();
        }

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menssagemCanselarSala();
            }
        });

        criar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                desabilitar();
                telaCriarSala.setVisibility(View.VISIBLE);

                if(sala.getIdSala() != null){
                    if(titulo.getText().toString().equals("") || serie.getText().toString().equals("") ||
                            descricao.getText().toString().equals("")){
                        menssagemErro("Preencha todos os campos");
                    }else{
                        sala.setTitulo(titulo.getText().toString());
                        sala.setSerie(serie.getText().toString());
                        sala.setDescricao(descricao.getText().toString());
                        editarSala(sala);
                    }
                }
                if(sala.getIdSala() == null) {
                    if (titulo.getText().toString().equals("") || serie.getText().toString().equals("") ||
                            descricao.getText().toString().equals("")) {
                        menssagemErro("Preencha todos os campos");
                    } else {
                        sala = new Sala(titulo.getText().toString(), serie.getText().toString(),
                                descricao.getText().toString(), armazenaNoApp.recuperarShared());
                        criarSala(sala);
                    }
                }
            }
        });

    }

    public void criarSala(Sala sala){
        SalaInterface salaInterface = retrofit.create(SalaInterface.class);

        Call<Sala> call = salaInterface.salvar(sala);

        call.enqueue(new Callback<Sala>() {
            @Override
            public void onResponse(Call<Sala> call, Response<Sala> response) {
                if(response.isSuccessful()){
                    menssagemDeSucesso("Sala criada com sucesso");
                }
            }

            @Override
            public void onFailure(Call<Sala> call, Throwable t) {
                menssagemErro("Erro ao tentar criar sala");
            }
        });
    }

    public void editarSala(Sala sala){
        SalaInterface salaInterface = retrofit.create(SalaInterface.class);

        Call<Void> call = salaInterface.editarSala(sala);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    menssagemDeSucesso("Alterdo com sucesso.");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public void menssagemErro(String erro){
        telaCriarSala.setVisibility(View.GONE);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle(erro);
        alert.setCancelable(false);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                habilitar();
            }
        });

        alert.create();
        alert.show();
    }

    public void menssagemDeSucesso(String msg){
        telaCriarSala.setVisibility(View.GONE);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle(msg);
        alert.setCancelable(false);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        alert.create();
        alert.show();
    }

    public void menssagemCanselarSala(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
         alert.setTitle("Deseja cancelar?");
         alert.setCancelable(false);

         alert.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialogInterface, int i) {
                 finish();
             }
         });

         alert.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialogInterface, int i) {

             }
         });

         alert.create();
         alert.show();
    }

    public void habilitar(){
        titulo.setEnabled(true);
        serie.setEnabled(true);
        descricao.setEnabled(true);
        criar.setEnabled(true);
        cancelar.setEnabled(true);
    }

    public void desabilitar(){
        titulo.setEnabled(false);
        serie.setEnabled(false);
        descricao.setEnabled(false);
        criar.setEnabled(false);
        cancelar.setEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       switch (item.getItemId()){
           case android.R.id.home:
               finish();
               return true;
               default:
                   return super.onOptionsItemSelected(item);
       }
    }
}
