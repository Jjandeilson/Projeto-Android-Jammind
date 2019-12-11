package com.example.jammind.menuAluno.salas;

import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.jammind.ArmazenaNoApp;
import com.example.jammind.R;
import com.example.jammind.api.ConverteJson;
import com.example.jammind.api.SalaInterface;
import com.example.jammind.api.UsuarioInterface;
import com.example.jammind.menuAluno.adapter.AdapterSalasAluno;
import com.example.jammind.model.Endereco;
import com.example.jammind.model.Sala;
import com.example.jammind.model.Usuario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SalaAlunoFragment extends Fragment {

    public static SalaAlunoFragment newInstance() {
        return new SalaAlunoFragment();
    }

    private RecyclerView recycleSalas;
    private FloatingActionButton fbaCadastro;
    private ArmazenaNoApp armazenaNoApp;
    private Usuario usuario = new Usuario();
    private List<Sala> salas = new ArrayList<Sala>();
    private ConstraintLayout telaCarregamentoSala;
    private Endereco endereco = new Endereco();
    private Retrofit retrofitUsuario = new Retrofit.Builder().baseUrl(endereco.getUrl()+"usuarios/")
            .addConverterFactory(new ConverteJson())
            .addConverterFactory(GsonConverterFactory.create()).build();
    private Retrofit retrofitSalas = new Retrofit.Builder().baseUrl(endereco.getUrl()+"salas/")
            .addConverterFactory(new ConverteJson())
            .addConverterFactory(GsonConverterFactory.create()).build();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sala_aluno_fragment, container, false);

        armazenaNoApp = new ArmazenaNoApp(getActivity());

        recycleSalas = view.findViewById(R.id.recyclerSalasAluno);
        fbaCadastro = view.findViewById(R.id.fbaCadastrar);
        telaCarregamentoSala = view.findViewById(R.id.telaCarregamentoSalasAluno);

        listarSala(armazenaNoApp.recuperarShared());

        fbaCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrarAluno();
            }
        });
        return view;
    }

    public void cadastrarNaSala(Integer idUsuario, Usuario usuario){
        UsuarioInterface usuarioInterface = retrofitUsuario.create(UsuarioInterface.class);

        Call<Usuario> call = usuarioInterface.cadastrarNaSala(idUsuario, usuario);

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.isSuccessful()){
                    telaCarregamentoSala.setVisibility(View.GONE);
                    mensagemSalaAluno("Cadastrado com sucesso.");
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {

            }
        });
    }

    public void listarSala(Integer idAluno){
        SalaInterface salaInterface = retrofitSalas.create(SalaInterface.class);

        Call<List<Sala>> call = salaInterface.buscarSalas(idAluno);

        call.enqueue(new Callback<List<Sala>>() {
            @Override
            public void onResponse(Call<List<Sala>> call, Response<List<Sala>> response) {
                if(response.isSuccessful()){
                    salas = response.body();
                    AdapterSalasAluno adapter = new AdapterSalasAluno(salas, getActivity());
                    DividerItemDecoration divider = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
                    RecyclerView.LayoutManager layout = new LinearLayoutManager(getActivity());
                    recycleSalas.setLayoutManager(layout);
                    recycleSalas.addItemDecoration(divider);
                    recycleSalas.setHasFixedSize(true);
                    recycleSalas.setAdapter(adapter);
                    telaCarregamentoSala.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Sala>> call, Throwable t) {

            }
        });
    }

    public void cadastrarAluno(){
        final EditText codigo = new EditText(getActivity());
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        alert.setTitle("Digite o código da sala:");
        alert.setCancelable(false);
        alert.setView(codigo);

        alert.setPositiveButton("Confirma", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                telaCarregamentoSala.setVisibility(View.VISIBLE);
                usuario.setCodigoSala(codigo.getText().toString());
                cadastrarNaSala(armazenaNoApp.recuperarShared(), usuario);
            }
        });

        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alert.create();
        alert.show();
    }

    public void mensagemSalaAluno(String msg){
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        alert.setTitle(msg);
        alert.setCancelable(false);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listarSala(armazenaNoApp.recuperarShared());
            }
        });

        alert.create();
        alert.show();
    }
}
