package com.example.jammind;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jammind.api.ConverteJson;
import com.example.jammind.api.UsuarioInterface;
import com.example.jammind.formulario.FormularioAlunoActivity;
import com.example.jammind.formulario.FormularioProfessorActivity;
import com.example.jammind.menuAluno.MenuAlunoActivity;
import com.example.jammind.menuProfessor.MenuProfessorActivity;
import com.example.jammind.model.Endereco;
import com.example.jammind.model.Usuario;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText login, senha;
    private Button logar;
    private TextView sobre;
    private CircleImageView professor, aluno;
    private ConstraintLayout telaPrincipal;
    private Usuario usuario = new Usuario();
    private Usuario usuarioBanco = new Usuario();
    private Endereco endereco = new Endereco();
    private Retrofit retrofit = new Retrofit.Builder().baseUrl(endereco.getUrl()+"/usuarios/")
            .addConverterFactory(new ConverteJson())
            .addConverterFactory(GsonConverterFactory.create()).build();
    private ArmazenaNoApp armazenaNoApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        armazenaNoApp = new ArmazenaNoApp(getApplicationContext());

        getSupportActionBar().hide();

        login = findViewById(R.id.loginUsuario);
        senha = findViewById(R.id.senhaUsuario);
        logar = findViewById(R.id.btnLogar);
        professor = findViewById(R.id.imgProfessor);
        aluno = findViewById(R.id.imgAluno);
        telaPrincipal = findViewById(R.id.telaCarregamentoPrincipal);
        sobre = findViewById(R.id.idSobre);

        telaPrincipal.setVisibility(View.GONE);

        logar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                desabilitarCampos();
                telaPrincipal.setVisibility(View.VISIBLE);
                if(login.getText().toString().equals("") || senha.getText().toString().equals("")){
                    menssagemDeErro("Preencha todos os campos");
                }else if(!login.getText().toString().contains("@gmail")){
                    menssagemDeErro("Preencha o campo E-mail corretamente");
                }else {
                    usuario.setEmail(login.getText().toString());
                    usuario.setSenha(senha.getText().toString());
                    buscarUsuario(usuario);
                }
            }
        });

       professor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FormularioProfessorActivity.class);
                startActivity(intent);
            }
        });

        aluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FormularioAlunoActivity.class);
                startActivity(intent);
            }
        });

        sobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SobreActivity.class);
                startActivity(intent);
            }
        });
    }

    public void buscarUsuario(final Usuario usuario){
        UsuarioInterface usuarioInterface = retrofit.create(UsuarioInterface.class);

        Call<Usuario> call = usuarioInterface.buscarUsuario(usuario);

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.isSuccessful()){
                    if(response.body() == null){
                        menssagemDeErro("Usuario não existe");
                        habilitarCampos();
                    }else{
                        usuarioBanco = response.body();
                        if(usuarioBanco.getTipoUsuario().equalsIgnoreCase("PROFESSOR")){
                                Intent intent = new Intent(getApplicationContext(), MenuProfessorActivity.class);
                                armazenaNoApp.salvarShared(usuarioBanco.getIdUsuario(), usuarioBanco.getNome(), usuarioBanco.getEmail(),
                                        usuarioBanco.getSobrenome());
                                startActivity(intent);
                                finish();
                        }else{
                            Intent intent = new Intent(getApplicationContext(), MenuAlunoActivity.class);
                            armazenaNoApp.salvarShared(usuarioBanco.getIdUsuario(), usuarioBanco.getNome(), usuarioBanco.getEmail(),
                                    usuarioBanco.getSobrenome());
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                menssagemDeErro("Erro de conexão");
            }
        });

    }
    public void menssagemDeErro(String erro){
        telaPrincipal.setVisibility(View.GONE);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle(erro);
        alert.setCancelable(false);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                habilitarCampos();
            }
        });

        alert.create();
        alert.show();
    }

    public void habilitarCampos(){
        login.setEnabled(true);
        senha.setEnabled(true);
        professor.setEnabled(true);
        aluno.setEnabled(true);
        logar.setEnabled(true);
    }

    public void desabilitarCampos(){
        login.setEnabled(false);
        senha.setEnabled(false);
        professor.setEnabled(false);
        aluno.setEnabled(false);
        logar.setEnabled(false);
    }
}
