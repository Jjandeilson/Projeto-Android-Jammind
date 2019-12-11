package com.example.jammind.menuProfessor.salas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.jammind.R;
import com.example.jammind.api.ConverteJson;
import com.example.jammind.api.TemaInterface;
import com.example.jammind.menuProfessor.adapter.AdapterLiberarTarefas;
import com.example.jammind.model.Endereco;
import com.example.jammind.model.Tema;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LiberarTarefaSalaActivity extends AppCompatActivity {

    private Integer codigo;
    private List<Tema> temas = new ArrayList<Tema>();
    private RecyclerView recycleTarefas;
    private Endereco endereco = new Endereco();
    private Retrofit retrofit = new Retrofit.Builder().baseUrl(endereco.getUrl()+"temas/")
            .addConverterFactory(new ConverteJson())
            .addConverterFactory(GsonConverterFactory.create()).build();
    private ConstraintLayout telaCarregamentoTaremaProfessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liberar_tarefa_sala);

        getSupportActionBar().setTitle("Gerenciar Tarefas");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle dado = getIntent().getExtras();
        codigo = dado.getInt("codigo");

        recycleTarefas = findViewById(R.id.recyclerLiberarTarefas);
        telaCarregamentoTaremaProfessor = findViewById(R.id.telaCarregamentoTarefa);

        listarTemas(codigo);
    }

    public void listarTemas(Integer idSala){
        TemaInterface temaInterface = retrofit.create(TemaInterface.class);

        Call<List<Tema>> call = temaInterface.listar(idSala);
        call.enqueue(new Callback<List<Tema>>() {
            @Override
            public void onResponse(Call<List<Tema>> call, Response<List<Tema>> response) {
                if(response.isSuccessful()){
                    temas = response.body();
                    AdapterLiberarTarefas adapter = new AdapterLiberarTarefas(temas, getContext(), codigo);
                    DividerItemDecoration divider = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
                    RecyclerView.LayoutManager layout = new LinearLayoutManager(getApplicationContext());
                    recycleTarefas.setHasFixedSize(true);
                    recycleTarefas.setLayoutManager(layout);
                    recycleTarefas.addItemDecoration(divider);
                    recycleTarefas.setAdapter(adapter);
                    telaCarregamentoTaremaProfessor.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Tema>> call, Throwable t) {

            }
        });
    }

    public Context getContext(){
        return this;
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
