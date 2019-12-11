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
import com.example.jammind.menuAluno.mural.ExibirMuralActivity;
import com.example.jammind.menuAluno.salas.ExibirTarefasActivity;
import com.example.jammind.model.Tema;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterTemaMural extends RecyclerView.Adapter<AdapterTemaMural.MyAdapterTemaMural> {

    private Context context;
    private List<Tema> temas;

    public AdapterTemaMural(Context context, List<Tema> temas){
        this.context = context;
        this.temas = temas;
    }

    @NonNull
    @Override
    public MyAdapterTemaMural onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.temas_alunos,
                parent,false);
        return new MyAdapterTemaMural(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterTemaMural holder, int position) {
        final Tema tema = temas.get(position);
        holder.id.setText(String.valueOf(tema.getIdTema()));
        holder.id.setVisibility(View.GONE);
        holder.nome.setText(tema.getNomeTema());
        holder.descricao.setText(tema.getDescricaoTema());

        if(Integer.parseInt(holder.id.getText().toString()) == 1){
            holder.imgTema.setImageResource(R.drawable.reino_animal);
        }else if(Integer.parseInt(holder.id.getText().toString()) == 2){
            holder.imgTema.setImageResource(R.drawable.artes);
        }else if(Integer.parseInt(holder.id.getText().toString()) == 3){
            holder.imgTema.setImageResource(R.drawable.social);
        }else if(Integer.parseInt(holder.id.getText().toString()) == 4){
            holder.imgTema.setImageResource(R.drawable.genero);
        }else{
            holder.imgTema.setImageResource(R.drawable.sustentabilidade);
        }

        holder.entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ExibirMuralActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("tema", tema);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return temas.size();
    }

    public class MyAdapterTemaMural extends  RecyclerView.ViewHolder{

        private TextView nome, descricao, id;
        private CircleImageView imgTema;
        private Button entrar;

        public MyAdapterTemaMural(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.nomeTemaAluno);
            descricao = itemView.findViewById(R.id.descricaoTemaAluno);
            id = itemView.findViewById(R.id.idTemaAluno);
            imgTema = itemView.findViewById(R.id.imgTemaAluno);
            entrar = itemView.findViewById(R.id.btnEntrarTemaAluno);
        }
    }
}
