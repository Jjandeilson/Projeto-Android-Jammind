package com.example.jammind.menuAluno.salas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.jammind.ArmazenaNoApp;
import com.example.jammind.R;
import com.example.jammind.api.ConverteJson;
import com.example.jammind.api.HistoricoInterface;
import com.example.jammind.api.TarefasInterface;
import com.example.jammind.model.Endereco;
import com.example.jammind.model.Historico;
import com.example.jammind.model.Tarefa;
import com.example.jammind.model.Usuario;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResponderTarefasActivity extends AppCompatActivity {

    private Tarefa tarefa;
    private Usuario usuario = new Usuario();
    private ImageView imgTema;
    private Historico historico;
    private Character respostaAluno;
    private TextView pergunta,q1,q2,q3,q4, nomeQuestao;
    private RadioButton op1, op2, op3, op4;
    private Button confirma;
    private ArmazenaNoApp armazenaNoApp;
    private Endereco endereco = new Endereco();
    private Retrofit retrofit = new Retrofit.Builder().baseUrl(endereco.getUrl()+"tarefas/")
            .addConverterFactory(new ConverteJson())
            .addConverterFactory(GsonConverterFactory.create()).build();
    private Retrofit retrofitHistorico = new Retrofit.Builder().baseUrl(endereco.getUrl()+"historicos/")
            .addConverterFactory(new ConverteJson())
            .addConverterFactory(GsonConverterFactory.create()).build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responder_tarefas);

        armazenaNoApp = new ArmazenaNoApp(getApplicationContext());

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle dado = getIntent().getExtras();
        tarefa = (Tarefa) dado.getSerializable("pergunta");
        usuario.setIdUsuario(armazenaNoApp.recuperarShared());

        pergunta = findViewById(R.id.perguntaTarefa);
        nomeQuestao = findViewById(R.id.nomeTemaQst);
        imgTema = findViewById(R.id.imgReinoAnimal);

        imagemTarefa(tarefa.getIdTarefa());
        nomeQuestao.setText(tarefa.getNomeTarefa());

        q1 = findViewById(R.id.perguntaA);
        q2 = findViewById(R.id.perguntaB);
        q3 = findViewById(R.id.perguntaC);
        q4 = findViewById(R.id.perguntaD);

        op1 = findViewById(R.id.opA);
        op2 = findViewById(R.id.opB);
        op3 = findViewById(R.id.opC);
        op4 = findViewById(R.id.opD);

        confirma = findViewById(R.id.btnConfirmaResposta);

        pergunta.setText(tarefa.getNomePergunta());
        q1.setText(tarefa.getOpcaoA());
        q2.setText(tarefa.getOpcaoB());
        q3.setText(tarefa.getOpcaoC());
        q4.setText(tarefa.getOpcaoD());

        op1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                respostaAluno = 'A';
                desativarRadio(Arrays.asList(op2,op3,op4));
            }
        });

        op2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                respostaAluno = 'B';
                desativarRadio(Arrays.asList(op1, op3, op4));
            }
        });

        op3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                respostaAluno = 'C';
                desativarRadio(Arrays.asList(op1, op2, op4));
            }
        });

        op4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                respostaAluno = 'D';
                desativarRadio(Arrays.asList(op1,op2,op3));
            }
        });

        confirma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmaResposta(tarefa.getIdTarefa(), respostaAluno.toString());
            }
        });
    }

    public void confirmaResposta(Integer idTarefa, String opcao){
        TarefasInterface tarefasInterface = retrofit.create(TarefasInterface.class);

        Call<Boolean> call = tarefasInterface.respostaDoALuno(idTarefa, opcao);

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    if(response.body() == false){
                       respostaErrado();
                    } else{
                        historico = new Historico(usuario, tarefa, true);
                        salvarNoHistorico(historico);
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }

    public void salvarNoHistorico(Historico historico){
        HistoricoInterface historicoInterface = retrofitHistorico.create(HistoricoInterface.class);

        Call<Void> call = historicoInterface.salvarHistorico(historico);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    mensagemSucesso();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public void desativarRadio(List<RadioButton> radios){
        for(int i = 0;i < radios.size(); i++){
            RadioButton radio = radios.get(i);
            radio.setClickable(false);
        }
    }

    public void respostaErrado(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Resposta Errada");
        alert.setCancelable(false);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                limparSelecionado();
                ativarRadios();
            }
        });

        alert.create();
        alert.show();
    }

    public void mensagemSucesso(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Resposta correta.");
        alert.setCancelable(false);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        alert.create();
        alert.show();
    }

    public void limparSelecionado(){
        op1.setChecked(false);
        op2.setChecked(false);
        op3.setChecked(false);
        op4.setChecked(false);
    }

    public void ativarRadios(){
        op1.setClickable(true);
        op2.setClickable(true);
        op3.setClickable(true);
        op4.setClickable(true);
    }

    public void imagemTarefa(Integer idT){
        if(idT == 1 || idT <= 5 ){
            imgTema.setImageResource(R.drawable.tarefa_vida_selvagem);
        }else if(idT == 6 || idT <= 10){
            imgTema.setImageResource(R.drawable.tarefa_arte);
        }else if(idT == 11 || idT <= 12){
            imgTema.setImageResource(R.drawable.tarefa_social);
        }else if(idT == 3 || idT <= 14){
            imgTema.setImageResource(R.drawable.tarefa_genero);
        }else{
            imgTema.setImageResource(R.drawable.tarefa_sustentabilidade);
        }
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
