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

import com.example.jammind.ArmazenaNoApp;
import com.example.jammind.R;
import com.example.jammind.api.ConverteJson;
import com.example.jammind.api.SalaInterface;
import com.example.jammind.menuProfessor.adapter.AdapterSalasProfessor;
import com.example.jammind.model.Endereco;
import com.example.jammind.model.Sala;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListarSalasProfessorActivity extends AppCompatActivity {

    private List<Sala> salas = new ArrayList<Sala>();
    private RecyclerView recyclerSalasProfessor;
    private ArmazenaNoApp armazenaNoApp;
    private ConstraintLayout telaCarregamentoSalas;
    private Endereco endereco = new Endereco();
    private Retrofit retrofit = new Retrofit.Builder().baseUrl(endereco.getUrl()+"salas/")
            .addConverterFactory(new ConverteJson())
            .addConverterFactory(GsonConverterFactory.create()).build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_salas_professor);

        getSupportActionBar().setTitle("Salas");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        armazenaNoApp = new ArmazenaNoApp(getApplicationContext());

        telaCarregamentoSalas = findViewById(R.id.telaCarregamentoSalasProfessor);
        recyclerSalasProfessor = findViewById(R.id.recyclerSalasProfessor);

        telaCarregamentoSalas.setVisibility(View.GONE);
        buscarSalas(armazenaNoApp.recuperarShared());
    }

    public void buscarSalas(Integer idProfessor){
        telaCarregamentoSalas.setVisibility(View.VISIBLE);
        SalaInterface salaInterface = retrofit.create(SalaInterface.class);

        Call<List<Sala>> call = salaInterface.buscarSalas(idProfessor);

        call.enqueue(new Callback<List<Sala>>() {
            @Override
            public void onResponse(Call<List<Sala>> call, Response<List<Sala>> response) {
                if(response.isSuccessful()){
                    salas = response.body();
                    AdapterSalasProfessor adapter = new AdapterSalasProfessor(salas,getApplicationContext());
                    DividerItemDecoration divider = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
                    RecyclerView.LayoutManager layout = new LinearLayoutManager(getApplicationContext());
                    recyclerSalasProfessor.setHasFixedSize(true);
                    recyclerSalasProfessor.addItemDecoration(divider);
                    recyclerSalasProfessor.setLayoutManager(layout);
                    recyclerSalasProfessor.setAdapter(adapter);
                    telaCarregamentoSalas.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Sala>> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onRestart() {
        buscarSalas(armazenaNoApp.recuperarShared());
        super.onRestart();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                    finish();
                    return  true;
                    default:
                        return super.onOptionsItemSelected(item);
        }

    }
}
