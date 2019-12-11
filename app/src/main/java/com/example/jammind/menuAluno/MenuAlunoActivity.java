package com.example.jammind.menuAluno;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.jammind.ArmazenaNoApp;
import com.example.jammind.MainActivity;
import com.example.jammind.R;

import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;

public class MenuAlunoActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView nome, email, sobrenome;
    private ArmazenaNoApp armazenaNoApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_aluno);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        armazenaNoApp = new ArmazenaNoApp(getApplicationContext());

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        View view = navigationView.getHeaderView(0);
        nome = view.findViewById(R.id.nomeEmailAluno);
        email = view.findViewById(R.id.emailUsuario);
        sobrenome = view.findViewById(R.id.sobrenomeEmailAluno);

        nome.setText(armazenaNoApp.recuperarNome());
        email.setText(armazenaNoApp.recuperarEmail());
        sobrenome.setText(armazenaNoApp.recuperarSobrenome());

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_salas_aluno, R.id.nav_tools_aluno)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_aluno_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_aluno, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_aluno_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            mensagemDeSair();
        }
    }

    public void mensagemDeSair(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Sair do aplicativo");
        alert.setCancelable(false);

        alert.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
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

}
