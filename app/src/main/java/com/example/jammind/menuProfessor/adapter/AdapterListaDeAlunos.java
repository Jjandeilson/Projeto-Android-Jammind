package com.example.jammind.menuProfessor.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jammind.R;
import com.example.jammind.api.ConverteJson;
import com.example.jammind.api.SalaInterface;
import com.example.jammind.api.UsuarioInterface;
import com.example.jammind.menuProfessor.salas.HistoricoAlunoActivity;
import com.example.jammind.model.Endereco;
import com.example.jammind.model.Usuario;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdapterListaDeAlunos extends RecyclerView.Adapter<AdapterListaDeAlunos.MyAdapterListaAlunos> {

    private Integer codigo;
    private List<Usuario> usuarios;
    private Context context;
    private Endereco endereco = new Endereco();
    private Retrofit retrofitRemover = new Retrofit.Builder().baseUrl(endereco.getUrl()+"salas/")
            .addConverterFactory(new ConverteJson())
            .addConverterFactory(GsonConverterFactory.create()).build();

    public AdapterListaDeAlunos(List<Usuario> usuarios, Context context, Integer codigo){
        this.usuarios = usuarios;
        this.context = context;
        this.codigo = codigo;
    }

    @NonNull
    @Override
    public MyAdapterListaAlunos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_de_alunos,
                parent, false);
        return new MyAdapterListaAlunos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyAdapterListaAlunos holder, int position) {
        final int posicao = position;
        final Usuario usuario = usuarios.get(position);
        holder.id.setText(String.valueOf(usuario.getIdUsuario()));
        holder.id.setVisibility(View.GONE);
        holder.nome.setText(usuario.getNome() + " " + usuario.getSobrenome());

        holder.acompanhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, HistoricoAlunoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("usuario", usuario);
                context.startActivity(intent);
            }
        });

        holder.remover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removerDaSala(Integer.parseInt(holder.id.getText().toString()), posicao);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public class MyAdapterListaAlunos extends RecyclerView.ViewHolder{

        private TextView nome, id;
        private Button remover, acompanhar;

        public MyAdapterListaAlunos(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.nomeAlunoSala);
            id = itemView.findViewById(R.id.idAlunoSala);
            remover = itemView.findViewById(R.id.btnRemoverAluno);
            acompanhar = itemView.findViewById(R.id.btnAcompanharAluno);
        }
    }

    public void removerAluno(final Integer idAluno, int posicao){
        final int p = posicao;
        SalaInterface salaInterface = retrofitRemover.create(SalaInterface.class);

        Call<Void> call = salaInterface.removerAluno(idAluno);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    mensagem("Aluno removido da sala", p);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public void removerDaSala(Integer idAluno, int posicao){
        final int p = posicao;
        final Integer id = idAluno;
        AlertDialog.Builder alert = new AlertDialog.Builder(context);

        alert.setTitle("Remover aluno?");
        alert.setCancelable(false);

        alert.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                removerAluno(id, p);
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

    public void mensagem(String msg, int posicao){
        final int p = posicao;
        AlertDialog.Builder alert = new AlertDialog.Builder(context);

        alert.setTitle(msg);
        alert.setCancelable(false);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AdapterListaDeAlunos.this.notifyItemRemoved(p);
                usuarios.remove(p);
                AdapterListaDeAlunos.this.notifyItemRangeChanged(p, usuarios.size());
            }
        });

        alert.create();
        alert.show();
    }
}
