package com.example.jammind.menuAluno.salas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.jammind.R;
import com.example.jammind.model.Sala;

import de.hdodenhof.circleimageview.CircleImageView;

public class ExibirSalaActivity extends AppCompatActivity {

    private TextView serie, titulo;
    private CircleImageView imgTarefa, imgDesempenho;
    Sala sala = new Sala();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibir_sala);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle dado = getIntent().getExtras();
        sala = (Sala) dado.getSerializable("sala");

        serie = findViewById(R.id.serieSalaAluno);
        titulo = findViewById(R.id.tituloSerieAluno);

        serie.setText(sala.getSerie());
        titulo.setText(sala.getDescricao());

        imgTarefa = findViewById(R.id.imgTarefasAluno);
        imgDesempenho = findViewById(R.id.imgHistoricoAluno);

        imgTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TemasAlunosActivity.class);
                intent.putExtra("codigo", sala.getIdSala());
                startActivity(intent);
            }
        });

        imgDesempenho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListaHistoricoDoAlunoActivity.class);
                startActivity(intent);
            }
        });
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
