package com.example.jammind.menuProfessor.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jammind.ArmazenaNoApp;
import com.example.jammind.MainActivity;
import com.example.jammind.R;

public class SairFragment extends Fragment {

    public static SairFragment newInstance() {
        return new SairFragment();
    }

    private ArmazenaNoApp armazenaNoApp;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        armazenaNoApp = new ArmazenaNoApp(getActivity());
        Intent intent = new Intent(context, MainActivity.class);
        armazenaNoApp.salvarShared(0,"","", "");
        context.startActivity(intent);
        getActivity().finish();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sair_fragment, container, false);
        return view;
    }


}
