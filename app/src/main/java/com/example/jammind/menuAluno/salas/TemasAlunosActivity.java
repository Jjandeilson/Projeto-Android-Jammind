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

import com.example.jammind.R;
import com.example.jammind.api.ConverteJson;
import com.example.jammind.api.TemaInterface;
import com.example.jammind.menuAluno.adapter.AdapterTemasAluno;
import com.example.jammind.model.Endereco;
import com.example.jammind.model.Tema;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TemasAlunosActivity extends AppCompatActivity {

    private RecyclerView recyclerTemas;
    private List<Tema> temas = new ArrayList<Tema>();
    private Integer codigo;
    private ConstraintLayout temaTemasAluno;
    private Endereco endereco = new Endereco();
    private Retrofit retrofit = new Retrofit.Builder().baseUrl(endereco.getUrl()+"temas/")
            .addConverterFactory(new ConverteJson())
            .addConverterFactory(GsonConverterFactory.create()).build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temas_alunos);

        getSupportActionBar().setTitle("Meus temas");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle dado = getIntent().getExtras();
        codigo = dado.getInt("codigo");

        recyclerTemas = findViewById(R.id.recyclerTemasAluno);
        temaTemasAluno = findViewById(R.id.telaCarregamentoTemas);
        listaDeTemas(codigo);
    }

    public void listaDeTemas(Integer idSala){
        TemaInterface temaInterface = retrofit.create(TemaInterface.class);

        Call<List<Tema>> call = temaInterface.listar(idSala);

        call.enqueue(new Callback<List<Tema>>() {
            @Override
            public void onResponse(Call<List<Tema>> call, Response<List<Tema>> response) {
                if(response.isSuccessful()){
                    temas = response.body();
                    AdapterTemasAluno adapter = new AdapterTemasAluno(temas, getApplicationContext());
                    DividerItemDecoration divider = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
                    RecyclerView.LayoutManager layout = new LinearLayoutManager(getApplicationContext());
                    recyclerTemas.setHasFixedSize(true);
                    recyclerTemas.addItemDecoration(divider);
                    recyclerTemas.setLayoutManager(layout);
                    recyclerTemas.setAdapter(adapter);
                    temaTemasAluno.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Tema>> call, Throwable t) {

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
