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
import com.example.jammind.menuAluno.salas.ResponderTarefasActivity;
import com.example.jammind.model.Tarefa;

import java.util.List;


public class AdapterExibirTarefas extends RecyclerView.Adapter<AdapterExibirTarefas.MyAdapterTarefa> {

    private Context context;
    private List<Tarefa> tarefas;

    public AdapterExibirTarefas(Context context, List<Tarefa> tarefas){
        this.context = context;
        this.tarefas = tarefas;
    }

    @NonNull
    @Override
    public MyAdapterTarefa onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_de_tarefas,
                parent,false);
        return new MyAdapterTarefa(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterTarefa holder, int position) {
        final Tarefa tarefa = tarefas.get(position);
        holder.nomeTarefa.setText(tarefa.getNomeTarefa());
        holder.nomePergunta.setText(tarefa.getNomePergunta());

        if(tarefa.isConcluido()){
            holder.btnRespoder.setText("FEITO");
            holder.btnRespoder.setEnabled(false);
        }

        holder.btnRespoder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ResponderTarefasActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("pergunta",tarefa);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tarefas.size();
    }

    public class MyAdapterTarefa extends RecyclerView.ViewHolder{

        private TextView nomeTarefa, nomePergunta;
        private Button btnRespoder;

        public MyAdapterTarefa(@NonNull View itemView) {
            super(itemView);

            nomeTarefa = itemView.findViewById(R.id.nomeTarefaSala);
            nomePergunta = itemView.findViewById(R.id.nomePergunta);
            btnRespoder = itemView.findViewById(R.id.btnResposderTarefa);
        }
    }

}
