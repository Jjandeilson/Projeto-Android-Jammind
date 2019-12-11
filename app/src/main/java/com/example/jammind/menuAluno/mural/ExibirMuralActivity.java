package com.example.jammind.menuAluno.mural;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jammind.ArmazenaNoApp;
import com.example.jammind.R;
import com.example.jammind.api.AlunoMuralInterface;
import com.example.jammind.api.ConverteJson;
import com.example.jammind.api.MuralInterface;
import com.example.jammind.model.AlunoMural;
import com.example.jammind.model.Endereco;
import com.example.jammind.model.Mural;
import com.example.jammind.model.Tema;
import com.example.jammind.model.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExibirMuralActivity extends AppCompatActivity {

    private Usuario usuario = new Usuario();
    private Mural mural = new Mural();
    private TextView nome, questao;
    private EditText textoAluno;
    private ImageView imgTema;
    private Button btnSalvar;
    private Tema tema;
    private Endereco endereco = new Endereco();
    private Retrofit retrofit = new Retrofit.Builder().baseUrl(endereco.getUrl()+"texto/")
            .addConverterFactory(new ConverteJson())
            .addConverterFactory(GsonConverterFactory.create()).build();
    private Retrofit retrofitMural = new Retrofit.Builder().baseUrl(endereco.getUrl()+"murais/")
            .addConverterFactory(new ConverteJson())
            .addConverterFactory(GsonConverterFactory.create()).build();
    private ArmazenaNoApp armazenaNoApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibir_mural);

        armazenaNoApp = new ArmazenaNoApp(getApplicationContext());

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle dado = getIntent().getExtras();
        tema = (Tema) dado.getSerializable("tema");

        buscarMural(tema.getIdTema(), armazenaNoApp.recuperarShared());

        nome = findViewById(R.id.nomeTemaMural);
        questao = findViewById(R.id.qtsMural);
        imgTema = findViewById(R.id.imgTemaMural);

        textoAluno = findViewById(R.id.txtMural);
        btnSalvar = findViewById(R.id.btnSalvarMural);

        nome.setText(tema.getNomeTema());

        foto(tema.getIdTema());
        pergunta(tema.getIdTema());
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarTexto(armazenaNoApp.recuperarShared(), tema.getIdTema(), textoAluno.getText().toString());
            }
        });
    }

    public void buscarMural(Integer idTema, Integer idAluno){
        MuralInterface muralInterface = retrofitMural.create(MuralInterface.class);

        Call<Mural> call = muralInterface.buscarMural(idTema, idAluno);

        call.enqueue(new Callback<Mural>() {
            @Override
            public void onResponse(Call<Mural> call, Response<Mural> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        mural = response.body();
                        textoAluno.setText(mural.getTextoAluno());
                    }
                    if(textoAluno.getText().toString().equals("")){
                        textoAluno.setEnabled(true);
                    }else {
                        textoAluno.setEnabled(false);
                        btnSalvar.setEnabled(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<Mural> call, Throwable t) {

            }
        });
    }

    public void salvarTexto(Integer idAluno, Integer idMural, String texto){
        usuario.setIdUsuario(idAluno);
        mural.setIdMural(idMural);
        AlunoMuralInterface alunoMuralInterface = retrofit.create(AlunoMuralInterface.class);
        AlunoMural alunoMural = new AlunoMural(usuario,mural, texto);
        Call<Void> call = alunoMuralInterface.salvarTexto(alunoMural);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    mensagemSucesso("Salvo com sucesso.");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public void mensagemSucesso(String msg){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle(msg);
        alert.setCancelable(false);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                textoAluno.setEnabled(false);
            }
        });

        alert.create();
        alert.show();
    }

    public void foto(Integer id){
        if(id == 1 ){
            imgTema.setImageResource(R.drawable.tarefa_vida_selvagem);
        }else if(id == 2){
            imgTema.setImageResource(R.drawable.tarefa_arte);
        }else if(id == 3){
            imgTema.setImageResource(R.drawable.tarefa_social);
        }else if(id == 4){
            imgTema.setImageResource(R.drawable.tarefa_genero);
        }else {
            imgTema.setImageResource(R.drawable.tarefa_sustentabilidade);
        }
    }

    public void pergunta(Integer id){
        if(id == 1 ){
            questao.setText("Fale um pouco sobre os seus animais preferidos.");
        }else if(id == 2){
            questao.setText("Fale um pouco sobre as atividades de criação de pinturas e desenhos que você faz na escola.");
        }else if(id == 3){
            questao.setText("Fale um pouco sobre a situação de crianças carentes que vivem no seu bairro.");
        }else if(id == 4){
            questao.setText("Fale um pouco sobre as brincadeiras que você mais gosta de praticar com os seus colegas.");
        }else {
            questao.setText("Fale um pouco sobre como  é a coleta de lixo na sua casa.");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return  true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }
}
