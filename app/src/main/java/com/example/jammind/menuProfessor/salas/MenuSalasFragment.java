package com.example.jammind.menuProfessor.salas;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jammind.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class MenuSalasFragment extends Fragment {

    public static MenuSalasFragment newInstance() {
        return new MenuSalasFragment();
    }

    private CircleImageView imgCriar, imgListar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_salas_fragment, container, false);

        imgCriar = view.findViewById(R.id.imgCriarSala);
        imgListar = view.findViewById(R.id.imgListarSalas);

        imgCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),CriarSalaActivity.class);
                startActivity(intent);
            }
        });

        imgListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ListarSalasProfessorActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
