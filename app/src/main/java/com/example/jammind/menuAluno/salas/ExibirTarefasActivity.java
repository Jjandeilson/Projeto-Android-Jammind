package com.example.jammind.menuAluno.salas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.jammind.ArmazenaNoApp;
import com.example.jammind.R;
import com.example.jammind.api.ConverteJson;
import com.example.jammind.api.TarefasInterface;
import com.example.jammind.menuAluno.adapter.AdapterExibirTarefas;
import com.example.jammind.model.Endereco;
import com.example.jammind.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExibirTarefasActivity extends AppCompatActivity {

    private ArmazenaNoApp armazenaNoApp;
    private RecyclerView recycleTarefa;
    private ConstraintLayout telaCarregamento;
    private List<Tarefa> tarefas = new ArrayList<Tarefa>();
    private Endereco endereco = new Endereco();
    private Retrofit retrofit = new Retrofit.Builder().baseUrl(endereco.getUrl()+"tarefas/")
            .addConverterFactory(new ConverteJson())
            .addConverterFactory(GsonConverterFactory.create()).build();
    private Integer idTema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibir_tarefas);

        armazenaNoApp = new ArmazenaNoApp(getApplicationContext());

        getSupportActionBar().setTitle("Minhas tarefas");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle dado = getIntent().getExtras();
        idTema = dado.getInt("idSala");

        recycleTarefa = findViewById(R.id.recyclerExibirTarefas);
        telaCarregamento = findViewById(R.id.telaCarregamentoTarefas);

        listarTarefas(idTema, armazenaNoApp.recuperarShared());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        listarTarefas(idTema, armazenaNoApp.recuperarShared());
    }

    public void listarTarefas(Integer idSala, Integer idAluno){
        TarefasInterface tarefasInterface = retrofit.create(TarefasInterface.class);

        Call<List<Tarefa>> call = tarefasInterface.listaDeTarefas(idSala, idAluno);

        call.enqueue(new Callback<List<Tarefa>>() {
            @Override
            public void onResponse(Call<List<Tarefa>> call, Response<List<Tarefa>> response) {
               if(response.isSuccessful()){
                   tarefas = response.body();
                   AdapterExibirTarefas adapter = new AdapterExibirTarefas(getApplicationContext(),tarefas);
                   DividerItemDecoration divider = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
                   RecyclerView.LayoutManager layout = new LinearLayoutManager(getApplicationContext());
                   recycleTarefa.setHasFixedSize(true);
                   recycleTarefa.addItemDecoration(divider);
                   recycleTarefa.setLayoutManager(layout);
                   recycleTarefa.setAdapter(adapter);
                   telaCarregamento.setVisibility(View.GONE);
               }
            }

            @Override
            public void onFailure(Call<List<Tarefa>> call, Throwable t) {

            }
        });
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
