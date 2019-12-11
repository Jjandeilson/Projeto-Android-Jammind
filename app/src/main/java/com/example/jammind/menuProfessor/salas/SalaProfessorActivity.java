package com.example.jammind.menuProfessor.salas;

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

public class SalaProfessorActivity extends AppCompatActivity {

    private Sala sala;
    private CircleImageView imgAluno, imgTarefa;
    private TextView serie, titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala_professor);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Gerencia da sala");

        Bundle dado = getIntent().getExtras();
        sala = (Sala) dado.getSerializable("sala");

        imgAluno = findViewById(R.id.imgAlunoSala);
        imgTarefa = findViewById(R.id.imgTarefaSala);
        serie = findViewById(R.id.serieSalaProfessor);
        titulo = findViewById(R.id.tituloSalaProfessor);

        serie.setText(sala.getSerie());
        titulo.setText(sala.getTitulo());


        imgAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ListaAlunosSalasActivity.class);
                intent.putExtra("codigo", sala.getIdSala());
                startActivity(intent);
            }
        });

        imgTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LiberarTarefaSalaActivity.class);
                intent.putExtra("codigo", sala.getIdSala());
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
