package com.example.jammind.menuProfessor.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jammind.R;

public class PrincipalProfessorFragment extends Fragment {

    public static PrincipalProfessorFragment newInstance() {
        return new PrincipalProfessorFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.principal_professor_fragment, container, false);
        return view;
    }


}
