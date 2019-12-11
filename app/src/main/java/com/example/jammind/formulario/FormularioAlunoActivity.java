package com.example.jammind.formulario;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.jammind.ArmazenaNoApp;
import com.example.jammind.R;
import com.example.jammind.api.ConverteJson;
import com.example.jammind.api.UsuarioInterface;
import com.example.jammind.enuns.UsuarioEnum;
import com.example.jammind.menuAluno.MenuAlunoActivity;
import com.example.jammind.model.Endereco;
import com.example.jammind.model.Usuario;
import com.vicmikhailau.maskededittext.MaskedEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FormularioAlunoActivity extends AppCompatActivity {

    private Usuario usuario;
    private EditText nome, sobrenome, cidade, email, confirmaEmail, senha, confirmaSenha;
    private MaskedEditText data;
    private AutoCompleteTextView estado;
    private ImageView imgEstado;
    private Button criar, cancelar;
    private ConstraintLayout telaCarregamentoAluno;
    private ArmazenaNoApp armazenaNoApp;
    private Endereco endereco = new Endereco();
    private Retrofit retrofit = new Retrofit.Builder().baseUrl(endereco.getUrl()+"usuarios/")
            .addConverterFactory(new ConverteJson())
            .addConverterFactory(GsonConverterFactory.create()).build();
    private String[] estados = new String[]{"Acre", "Alagoas ", "Amapá", "Amazonas", "Bahia", "Ceará", "Distrito Federal", "Espírito Santo",
            "Goiás", "Maranhão", "Mato Grosso", "Mato Grosso do Sul", "Minas Gerais", "Pará", "Paraíba", "Paraná", "Pernambuco",
            "Piauí", "Rio de Janeiro", "Rio Grande do Norte", "Rio Grande do Sul", "Rondônia", "Roraima", "Santa Catarina",
            "São Paulo","Sergipe", "Tocantins"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aluno);

        getSupportActionBar().hide();

        nome = findViewById(R.id.nomeAluno);
        sobrenome = findViewById(R.id.sobrenomeAluno);
        cidade = findViewById(R.id.cidadeAluno);
        email = findViewById(R.id.emailAluno);
        confirmaEmail = findViewById(R.id.confirmaEmailAluno);
        senha = findViewById(R.id.senhaAluno);
        confirmaSenha = findViewById(R.id.confirmaSenhaAluno);
        telaCarregamentoAluno = findViewById(R.id.telaCarregamentoAluno);
        data = findViewById(R.id.dataAluno);
        estado = findViewById(R.id.estadoAluno);
        criar = findViewById(R.id.btnCriarAluno);
        cancelar = findViewById(R.id.btnCancelarAluno);

        telaCarregamentoAluno.setVisibility(View.GONE);
        armazenaNoApp = new ArmazenaNoApp(getApplicationContext());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,estados);
        estado.setAdapter(adapter);

        cancelar.setEnabled(false);

        nome.addTextChangedListener(verificarCampos);
        sobrenome.addTextChangedListener(verificarCampos);
        cidade.addTextChangedListener(verificarCampos);
        email.addTextChangedListener(verificarCampos);
        confirmaEmail.addTextChangedListener(verificarCampos);
        senha.addTextChangedListener(verificarCampos);
        confirmaSenha.addTextChangedListener(verificarCampos);
        data.addTextChangedListener(verificarCampos);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menssagemCancelar();
            }
        });

       criar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               desabilitar();
               telaCarregamentoAluno.setVisibility(View.VISIBLE);
               if(nome.getText().toString().equals("") || sobrenome.getText().toString().equals("") ||
                       data.getText().toString().equals("")|| email.getText().toString().equals("") ||
                       confirmaEmail.getText().toString().equals("") || senha.getText().toString().equals("") ||
                       confirmaSenha.getText().toString().equals("")){
                   menssagemDeErro("Preencha todos os campos");
               }else if(estado.getText().toString().equals("")){
                   menssagemDeErro("Selecione o estado");
               }else if(!email.getText().toString().equals(confirmaEmail.getText().toString())){
                   menssagemDeErro("Confirme o E-mail");
               }else if(!email.getText().toString().contains("@gmail")){
                   menssagemDeErro("E-mail inválido");
               }else if(!senha.getText().toString().equals(confirmaSenha.getText().toString())){
                   menssagemDeErro("Confirme a senha");
               }else{
                   usuario = new Usuario(nome.getText().toString(), sobrenome.getText().toString(),data.getText().toString(),
                           email.getText().toString(), cidade.getText().toString(), estado.getText().toString(),
                           senha.getText().toString(), UsuarioEnum.ALUNO.toString());
                   salvarAluno(usuario);
               }
           }
       });

    }

    public void salvarAluno(Usuario usuario){
        UsuarioInterface usuarioInterface = retrofit.create(UsuarioInterface.class);

        Call<Usuario> call = usuarioInterface.salvarUsuario(usuario);

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.isSuccessful()){
                    telaCarregamentoAluno.setVisibility(View.GONE);
                    cadastradoComSucesso();
                    armazenaNoApp.salvarShared(response.body().getIdUsuario(), response.body().getNome(), response.body().getEmail(),
                            response.body().getSobrenome());
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                menssagemDeErro("Erro ao tentar salvar");
            }
        });
    }


    public TextWatcher verificarCampos = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String nomeA = nome.getText().toString().trim();
            String sobrenomeA = sobrenome.getText().toString().trim();
            String cidadeA = cidade.getText().toString().trim();
            String emailA = email.getText().toString().trim();
            String confirmaEmailA = confirmaEmail.getText().toString().trim();
            String senhaA = senha.getText().toString().trim();
            String confirmaSenhaA = confirmaSenha.getText().toString().trim();
            String dataA = data.getText().toString().trim();

            if(!nomeA.isEmpty() && !sobrenomeA.isEmpty() && !cidadeA.isEmpty() && !emailA.isEmpty() &&
                !confirmaEmailA.isEmpty() && !senhaA.isEmpty() && !confirmaSenhaA.isEmpty() &&
                    !dataA.isEmpty()){
                cancelar.setEnabled(true);
            }else{
                cancelar.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    public void cadastradoComSucesso(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Cadastrado Com Sucesso!");
        alert.setCancelable(false);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(getApplicationContext(), MenuAlunoActivity.class);
                startActivity(intent);
                finish();
            }
        });

        alert.create();
        alert.show();
    }

    public void menssagemDeErro(String erro){
        telaCarregamentoAluno.setVisibility(View.GONE);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle(erro);
        alert.setCancelable(false);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                habilitar();
            }
        });

        alert.create();
        alert.show();
    }

    public void menssagemCancelar(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Deseja cancelar?");
        alert.setCancelable(false);

        alert.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        alert.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alert.create();
        alert.show();
    }

    public void habilitar(){
        nome.setEnabled(true);
        sobrenome.setEnabled(true);
        cidade.setEnabled(true);
        email.setEnabled(true);
        confirmaEmail.setEnabled(true);
        senha.setEnabled(true);
        confirmaSenha.setEnabled(true);
        data.setEnabled(true);
        estado.setEnabled(true);
        criar.setEnabled(true);
        cancelar.setEnabled(true);
    }

    public void desabilitar(){
        nome.setEnabled(false);
        sobrenome.setEnabled(false);
        cidade.setEnabled(false);
        email.setEnabled(false);
        confirmaEmail.setEnabled(false);
        senha.setEnabled(false);
        confirmaSenha.setEnabled(false);
        data.setEnabled(false);
        estado.setEnabled(false);
        criar.setEnabled(false);
        cancelar.setEnabled(false);
    }
}
