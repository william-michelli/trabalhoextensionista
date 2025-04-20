package com.aula.trabalhoextensionista.voluntario;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aula.trabalhoextensionista.R;
import com.aula.trabalhoextensionista.databinding.FragmentListagemVoluntariosBinding;
import com.aula.trabalhoextensionista.ong.OngAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.List;

public class ListagemVoluntariosFragment extends Fragment {

    private FragmentListagemVoluntariosBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentListagemVoluntariosBinding.inflate(inflater, container, false);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Botao de cadastro de Voluntários", Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
            }
        });

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //region CRIA A LISTAGEM

        //Pega a recycler view
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewListagemVoluntarios);

        if (recyclerView == null) {
            Log.e("ListagemVoluntariosFragment", "RecyclerView não encontrado!");
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<String> myList = Arrays.asList(
                "João Silva",
                "Maria Oliveira",
                "Pedro Santos",
                "Ana Costa",
                "Lucas Almeida",
                "Fernanda Pereira",
                "Carla Souza",
                "Paulo Lima",
                "Raquel Martins",
                "Gabriel Rocha",
                "Juliana Torres",
                "Eduardo Almeida",
                "Tatiane Ferreira",
                "Carlos Henrique",
                "Camila Alves",
                "Ricardo Lima",
                "Bruna Ribeiro",
                "Marcelo Silva",
                "Isabela Gomes",
                "Thiago Oliveira"
        );
        //Cria o adapter, passando a lista
        VoluntarioAdapter adapter = new VoluntarioAdapter(myList);

        //Seta o adapter no RecyclerView
        recyclerView.setAdapter(adapter);

        //endregion
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}