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
import android.widget.TextView;

import com.example.jammind.ArmazenaNoApp;
import com.example.jammind.R;
import com.example.jammind.api.ConverteJson;
import com.example.jammind.api.TarefasInterface;
import com.example.jammind.menuAluno.adapter.AdapterHistoricoDoAluno;
import com.example.jammind.model.Endereco;
import com.example.jammind.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListaHistoricoDoAlunoActivity extends AppCompatActivity {

    private TextView nome;
    private RecyclerView recycleHistoricoDoAluno;
    private ConstraintLayout telaCarregamento;
    private List<Tarefa> tarefas = new ArrayList<Tarefa>();
    private Endereco endereco = new Endereco();
    private Retrofit retrofit = new Retrofit.Builder().baseUrl(endereco.getUrl()+"tarefas/")
            .addConverterFactory(new ConverteJson())
            .addConverterFactory(GsonConverterFactory.create()).build();
    private ArmazenaNoApp armazenaNoApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_historico_do_aluno);

        getSupportActionBar().setTitle("Meu Histórico");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        armazenaNoApp = new ArmazenaNoApp(getApplicationContext());

        nome = findViewById(R.id.nomeDoAlunoHistorico);
        recycleHistoricoDoAluno = findViewById(R.id.recyclerHistoricoDoAluno);
        telaCarregamento = findViewById(R.id.telaCarregamentoHistorico);

        nome.setText(armazenaNoApp.recuperarNome());

        listarHistorico(armazenaNoApp.recuperarShared());
    }

    public void listarHistorico(Integer idAluno){
        TarefasInterface tarefasInterface = retrofit.create(TarefasInterface.class);

        Call<List<Tarefa>> call = tarefasInterface.listarHistorica(idAluno);

        call.enqueue(new Callback<List<Tarefa>>() {
            @Override
            public void onResponse(Call<List<Tarefa>> call, Response<List<Tarefa>> response) {
                if(response.isSuccessful()){
                    tarefas = response.body();
                    AdapterHistoricoDoAluno adapter = new AdapterHistoricoDoAluno(tarefas);
                    RecyclerView.LayoutManager layout = new LinearLayoutManager(getApplicationContext());
                    DividerItemDecoration divider = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
                    recycleHistoricoDoAluno.setHasFixedSize(true);
                    recycleHistoricoDoAluno.addItemDecoration(divider);
                    recycleHistoricoDoAluno.setLayoutManager(layout);
                    recycleHistoricoDoAluno.setAdapter(adapter);
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
