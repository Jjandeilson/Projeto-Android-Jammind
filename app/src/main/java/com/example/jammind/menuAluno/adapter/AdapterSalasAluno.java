package com.example.jammind.menuAluno.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jammind.R;
import com.example.jammind.menuAluno.salas.ExibirSalaActivity;
import com.example.jammind.model.Sala;

import java.util.List;

public class AdapterSalasAluno extends RecyclerView.Adapter<AdapterSalasAluno.MyAdapterSalasAluno> {

    private List<Sala> salas;
    private Context context;

    public AdapterSalasAluno(List<Sala> salas, Context context){
        this.salas = salas;
        this.context = context;
    }

    @NonNull
    @Override
    public MyAdapterSalasAluno onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_sala_alunos,
                parent,false);
        return new MyAdapterSalasAluno(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyAdapterSalasAluno holder, int position) {
        final Sala sala = salas.get(position);
        holder.id.setText(String.valueOf(sala.getIdSala()));
        holder.id.setVisibility(View.GONE);
        holder.serie.setText(sala.getSerie());
        holder.titulo.setText(sala.getTitulo());
        holder.descricao.setText(sala.getDescricao());
        holder.codigo.setText(sala.getCodigoSala());

        holder.entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ExibirSalaActivity.class);
                System.out.println(sala);
                intent.putExtra("sala", sala);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return salas.size();
    }

    public class MyAdapterSalasAluno extends RecyclerView.ViewHolder{

        private TextView titulo, serie, descricao, codigo, id;
        private Button entrar;

        public MyAdapterSalasAluno(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.tituloSalaAluno);
            serie = itemView.findViewById(R.id.serieSalaAluno);
            descricao = itemView.findViewById(R.id.descricaoSalaAluno);
            codigo = itemView.findViewById(R.id.codigoSalaAluno);
            id = itemView.findViewById(R.id.idSalaAluno);
            entrar = itemView.findViewById(R.id.btnEntrarAluno);
        }
    }
}
