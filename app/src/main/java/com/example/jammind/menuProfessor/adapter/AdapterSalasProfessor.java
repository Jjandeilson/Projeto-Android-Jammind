package com.example.jammind.menuProfessor.adapter;

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
import com.example.jammind.menuProfessor.salas.CriarSalaActivity;
import com.example.jammind.menuProfessor.salas.SalaProfessorActivity;
import com.example.jammind.model.Sala;

import java.util.List;

public class AdapterSalasProfessor extends RecyclerView.Adapter<AdapterSalasProfessor.MyAdapterSalasProfessor> {

    private List<Sala> salas;
    private Context context;

    public AdapterSalasProfessor(List<Sala> salas, Context context){
        this.salas = salas;
        this.context = context;
    }

    @NonNull
    @Override
    public MyAdapterSalasProfessor onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.salas_do_professor,
                parent, false);
        return new MyAdapterSalasProfessor(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyAdapterSalasProfessor holder, int position) {
        final Sala sala = salas.get(position);
        holder.id.setText(String.valueOf(sala.getIdSala()));
        holder.id.setVisibility(View.GONE);

        holder.titulo.setText(sala.getTitulo());
        holder.serie.setText(sala.getSerie());
        holder.descricao.setText(sala.getDescricao());
        holder.codigo.setText(sala.getCodigoSala());

        holder.entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SalaProfessorActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("sala", sala);
                context.startActivity(intent);
            }
        });

        holder.editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CriarSalaActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("sala", sala);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return salas.size();
    }

    public class MyAdapterSalasProfessor extends  RecyclerView.ViewHolder{

        private TextView titulo, serie, descricao, codigo, id;
        private Button entrar, editar;

        public MyAdapterSalasProfessor(@NonNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.tituloSala);
            serie = itemView.findViewById(R.id.serieSala);
            descricao = itemView.findViewById(R.id.descricaoSala);
            codigo = itemView.findViewById(R.id.codigoSala);
            id = itemView.findViewById(R.id.idSala);
            entrar = itemView.findViewById(R.id.btnEntrarSala);
            editar = itemView.findViewById(R.id.btnEditarSala);
        }
    }
}
