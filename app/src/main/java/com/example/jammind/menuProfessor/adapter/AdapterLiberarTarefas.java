package com.example.jammind.menuProfessor.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jammind.R;
import com.example.jammind.api.ConverteJson;
import com.example.jammind.api.TemaInterface;
import com.example.jammind.model.Endereco;
import com.example.jammind.model.Tema;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdapterLiberarTarefas extends RecyclerView.Adapter<AdapterLiberarTarefas.MyAdapterLiberarTarefas> {

    private Integer codigo;
    private List<Tema> temas;
    private Context context;
    private Endereco endereco = new Endereco();
    private Retrofit retrofit = new Retrofit.Builder().baseUrl(endereco.getUrl()+"/temas/")
            .addConverterFactory(new ConverteJson())
            .addConverterFactory(GsonConverterFactory.create()).build();

    public AdapterLiberarTarefas(List<Tema> temas, Context context, Integer codigo){
        this.temas = temas;
        this.context = context;
        this.codigo = codigo;
    }

    @NonNull
    @Override
    public MyAdapterLiberarTarefas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.liberar_tarefas_sala,
                parent,false);
        return new MyAdapterLiberarTarefas(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyAdapterLiberarTarefas holder, int position) {
        Tema tema = temas.get(position);
        holder.id.setText(String.valueOf(tema.getIdTema()));
        holder.id.setVisibility(View.GONE);
        holder.nome.setText(tema.getNomeTema());
        holder.descricao.setText(tema.getDescricaoTema());
        holder.ativa.setChecked(tema.isAtivo());

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

        holder.ativa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ativo) {
                if(ativo){
                    liberarTema(codigo, Integer.parseInt(holder.id.getText().toString()));
                }else {
                    desativarTema(codigo, Integer.parseInt(holder.id.getText().toString()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return temas.size();
    }

    public class MyAdapterLiberarTarefas extends RecyclerView.ViewHolder{

        private TextView nome, descricao, id;
        private Switch ativa;
        private CircleImageView imgTema;

        public MyAdapterLiberarTarefas(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.nomeTemaSala);
            descricao = itemView.findViewById(R.id.descricaoTemaSala);
            id = itemView.findViewById(R.id.idTemaSala);
            ativa = itemView.findViewById(R.id.liberarTemaSala);
            imgTema = itemView.findViewById(R.id.imgTema);
        }
    }

    public void liberarTema(Integer idSala, Integer idTema){
        TemaInterface temaInterface = retrofit.create(TemaInterface.class);

        Call<Tema> call = temaInterface.ativarTema(idSala,idTema);

        call.enqueue(new Callback<Tema>() {
            @Override
            public void onResponse(Call<Tema> call, Response<Tema> response) {
                if(response.isSuccessful()){
                    menssagemTarefa("Tarefa liberada");
                }
            }

            @Override
            public void onFailure(Call<Tema> call, Throwable t) {

            }
        });
    }

    public  void desativarTema(Integer idSala, Integer idTema){
        TemaInterface temaInterface = retrofit.create(TemaInterface.class);

        Call<Tema> call = temaInterface.desativarTema(idSala, idTema);

        call.enqueue(new Callback<Tema>() {
            @Override
            public void onResponse(Call<Tema> call, Response<Tema> response) {
                if(response.isSuccessful()){
                    menssagemTarefa("Tarefa desativada");
                }
            }

            @Override
            public void onFailure(Call<Tema> call, Throwable t) {

            }
        });
    }

    public void menssagemTarefa(String msg){
        AlertDialog.Builder alert = new AlertDialog.Builder(context);

        alert.setTitle(msg);
        alert.setCancelable(false);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alert.create();
        alert.show();
    }
}
