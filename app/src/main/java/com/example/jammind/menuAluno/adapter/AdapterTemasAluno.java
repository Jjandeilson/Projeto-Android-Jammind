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
import com.example.jammind.menuAluno.salas.ExibirTarefasActivity;
import com.example.jammind.model.Tema;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterTemasAluno extends RecyclerView.Adapter<AdapterTemasAluno.MyAdapterTemasAluno> {

    private List<Tema> temas;
    private Context context;

    public AdapterTemasAluno(List<Tema> temas, Context context){
        this.temas = temas;
        this.context = context;
    }

    @NonNull
    @Override
    public MyAdapterTemasAluno onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.temas_alunos,
                parent,false);
        return new MyAdapterTemasAluno(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyAdapterTemasAluno holder, int position) {
        Tema tema = temas.get(position);
        holder.id.setText(String.valueOf(tema.getIdTema()));
        holder.id.setVisibility(View.GONE);
        holder.nome.setText(tema.getNomeTema());
        holder.descricao.setText(tema.getDescricaoTema());
        holder.entrar.setEnabled(tema.isAtivo());

        if(Integer.parseInt(holder.id.getText().toString()) == 1){
            holder.imgTemaAluno.setImageResource(R.drawable.reino_animal);
        }else if(Integer.parseInt(holder.id.getText().toString()) == 2){
            holder.imgTemaAluno.setImageResource(R.drawable.artes);
        }else if(Integer.parseInt(holder.id.getText().toString()) == 3){
            holder.imgTemaAluno.setImageResource(R.drawable.social);
        }else if(Integer.parseInt(holder.id.getText().toString()) == 4){
            holder.imgTemaAluno.setImageResource(R.drawable.genero);
        }else{
            holder.imgTemaAluno.setImageResource(R.drawable.sustentabilidade);
        }

        if(!tema.isAtivo()){
            holder.entrar.setText("Bloqueado");
        }

        holder.entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ExibirTarefasActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("idSala", Integer.parseInt(holder.id.getText().toString()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return temas.size();
    }

    public class MyAdapterTemasAluno extends RecyclerView.ViewHolder{

        private TextView nome, descricao, id;
        private Button entrar;
        private CircleImageView imgTemaAluno;

        public MyAdapterTemasAluno(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.nomeTemaAluno);
            descricao = itemView.findViewById(R.id.descricaoTemaAluno);
            id = itemView.findViewById(R.id.idTemaAluno);
            entrar = itemView.findViewById(R.id.btnEntrarTemaAluno);
            imgTemaAluno = itemView.findViewById(R.id.imgTemaAluno);
        }
    }
}
