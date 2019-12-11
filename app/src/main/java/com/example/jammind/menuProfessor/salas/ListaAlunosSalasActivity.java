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
import com.example.jammind.api.UsuarioInterface;
import com.example.jammind.menuProfessor.adapter.AdapterListaDeAlunos;
import com.example.jammind.model.Endereco;
import com.example.jammind.model.Usuario;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListaAlunosSalasActivity extends AppCompatActivity {

    private Integer codigoSala;
    private List<Usuario> usuarios = new ArrayList<Usuario>();
    private Endereco endereco = new Endereco();
    private Retrofit retrofitAlunos = new Retrofit.Builder().baseUrl(endereco.getUrl()+"usuarios/")
            .addConverterFactory(new ConverteJson())
            .addConverterFactory(GsonConverterFactory.create()).build();
    private RecyclerView recycleAlunos;
    private ConstraintLayout telaAlunosProfessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos_salas);

        getSupportActionBar().setTitle("Lista de alunos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle dado = getIntent().getExtras();
        codigoSala = dado.getInt("codigo");
        recycleAlunos = findViewById(R.id.recyclerListaAlunos);
        telaAlunosProfessor = findViewById(R.id.telaCarregamentoAlunosProf);

        listaDeAlunos(codigoSala);
    }

    public void listaDeAlunos(Integer idSala){
        UsuarioInterface usuarioInterface = retrofitAlunos.create(UsuarioInterface.class);

        Call<List<Usuario>> call = usuarioInterface.listaDeAlunos(idSala);

        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if(response.isSuccessful()){
                    usuarios = response.body();
                    AdapterListaDeAlunos adapter = new AdapterListaDeAlunos(usuarios,getContext(), codigoSala);
                    DividerItemDecoration divider = new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL);
                    RecyclerView.LayoutManager layout = new LinearLayoutManager(getApplicationContext());
                    recycleAlunos.setHasFixedSize(true);
                    recycleAlunos.addItemDecoration(divider);
                    recycleAlunos.setLayoutManager(layout);
                    recycleAlunos.setAdapter(adapter);

                    telaAlunosProfessor.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {

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
