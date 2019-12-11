package com.example.jammind.menuAluno.mural;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jammind.R;
import com.example.jammind.api.ConverteJson;
import com.example.jammind.api.TemaInterface;
import com.example.jammind.menuAluno.adapter.AdapterTemaMural;
import com.example.jammind.model.Endereco;
import com.example.jammind.model.Tema;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MuralFragment extends Fragment {

    public static MuralFragment newInstance() {
        return new MuralFragment();
    }

    private RecyclerView recycleMural;
    private ConstraintLayout telaCarregamento;
    private List<Tema> temas = new ArrayList<Tema>();
    private Endereco endereco = new Endereco();
    private Retrofit retrofit = new Retrofit.Builder().baseUrl(endereco.getUrl()+"temas/")
            .addConverterFactory(new ConverteJson())
            .addConverterFactory(GsonConverterFactory.create()).build();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mural_fragment, container, false);

        recycleMural = view.findViewById(R.id.recyclerMural);
        telaCarregamento = view.findViewById(R.id.telaCarregamentoMural);

        listarTemas();
        return view;
    }

    public void listarTemas(){
        TemaInterface temaInterface = retrofit.create(TemaInterface.class);

        Call<List<Tema>> call = temaInterface.listarTemas();

        call.enqueue(new Callback<List<Tema>>() {
            @Override
            public void onResponse(Call<List<Tema>> call, Response<List<Tema>> response) {
                if(response.isSuccessful()){
                    temas = response.body();
                    AdapterTemaMural adapter = new AdapterTemaMural(getActivity(), temas);
                    RecyclerView.LayoutManager layout = new LinearLayoutManager(getActivity());
                    DividerItemDecoration divider = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
                    recycleMural.setHasFixedSize(true);
                    recycleMural.setLayoutManager(layout);
                    recycleMural.addItemDecoration(divider);
                    recycleMural.setAdapter(adapter);
                    telaCarregamento.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Tema>> call, Throwable t) {

            }
        });
    }
}
