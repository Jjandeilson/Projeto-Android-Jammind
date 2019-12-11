package com.example.jammind.menuAluno.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jammind.R;
import com.example.jammind.model.Tarefa;

import java.util.List;

public class AdapterHistoricoDoAluno extends RecyclerView.Adapter<AdapterHistoricoDoAluno.MyAdapterHistoricoDoAluno> {

    private List<Tarefa> tarefas;

    public AdapterHistoricoDoAluno(List<Tarefa> tarefas){
        this.tarefas = tarefas;
    }

    @NonNull
    @Override
    public MyAdapterHistoricoDoAluno onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.historico_do_aluno,
                parent, false);
        return new MyAdapterHistoricoDoAluno(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterHistoricoDoAluno holder, int position) {
        Tarefa tarefa = tarefas.get(position);
        holder.nome.setText(tarefa.getNomeTarefa());
        holder.pergunta.setText(tarefa.getNomePergunta());
        holder.feito.setEnabled(false);
    }

    @Override
    public int getItemCount() {
        return tarefas.size();
    }

    public class MyAdapterHistoricoDoAluno extends RecyclerView.ViewHolder{

        private TextView nome, pergunta;
        private Button feito;

        public MyAdapterHistoricoDoAluno(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.nomeTarefaHistorico);
            pergunta = itemView.findViewById(R.id.nomePerguntaHistorico);
            feito = itemView.findViewById(R.id.btnFeito);
        }
    }
}
