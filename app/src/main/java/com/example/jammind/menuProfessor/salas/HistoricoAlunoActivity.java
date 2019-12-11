package com.example.jammind.menuProfessor.salas;

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

import com.example.jammind.R;
import com.example.jammind.api.ConverteJson;
import com.example.jammind.api.TarefasInterface;
import com.example.jammind.menuProfessor.adapter.AdapterHistoricoAluno;
import com.example.jammind.model.Endereco;
import com.example.jammind.model.Tarefa;
import com.example.jammind.model.Usuario;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HistoricoAlunoActivity extends AppCompatActivity {

    private TextView nome;
    private RecyclerView recycleAlunoHistorico;
    private ConstraintLayout telaCarregamento;
    private Usuario usuario;
    private List<Tarefa> tarefas = new ArrayList<Tarefa>();
    private Endereco endereco = new Endereco();
    private Retrofit retrofit = new Retrofit.Builder().baseUrl(endereco.getUrl()+"tarefas/")
            .addConverterFactory(new ConverteJson())
            .addConverterFactory(GsonConverterFactory.create()).build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_aluno);

        getSupportActionBar().setTitle("Histórico do aluno");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle dado = getIntent().getExtras();
        usuario = (Usuario) dado.getSerializable("usuario");

        nome = findViewById(R.id.nomeAlunoHistorico);
        recycleAlunoHistorico = findViewById(R.id.recyclerHistoricoAluno);
        telaCarregamento = findViewById(R.id.telaCarregamentoHistoricoAluno);

        nome.setText(usuario.getNome() + " " + usuario.getSobrenome());
        listarHistorica(usuario.getIdUsuario());
    }

    public void listarHistorica(Integer idAluno){
        TarefasInterface tarefasInterface = retrofit.create(TarefasInterface.class);

        Call<List<Tarefa>> call = tarefasInterface.listarHistorica(idAluno);

        call.enqueue(new Callback<List<Tarefa>>() {
            @Override
            public void onResponse(Call<List<Tarefa>> call, Response<List<Tarefa>> response) {
                if(response.isSuccessful()){
                    tarefas = response.body();
                    AdapterHistoricoAluno adapter = new AdapterHistoricoAluno(tarefas);
                    DividerItemDecoration divider = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
                    RecyclerView.LayoutManager layout = new LinearLayoutManager(getApplication());
                    recycleAlunoHistorico.setHasFixedSize(true);
                    recycleAlunoHistorico.setLayoutManager(layout);
                    recycleAlunoHistorico.addItemDecoration(divider);
                    recycleAlunoHistorico.setAdapter(adapter);
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
