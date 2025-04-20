package com.aula.trabalhoextensionista.ong;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aula.trabalhoextensionista.R;
import com.aula.trabalhoextensionista.databinding.FragmentListagemOngsBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.List;

public class ListagemOngsFragment extends Fragment {

    private FragmentListagemOngsBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentListagemOngsBinding.inflate(inflater, container, false);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Botao de cadastro de ONGs", Snackbar.LENGTH_LONG)
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
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewListagemOngs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<String> myList = Arrays.asList(
                "Instituto Ayrton Senna",
                "Fundação Abrinq",
                "GRAACC",
                "Médicos Sem Fronteiras Brasil",
                "Amigos do Bem",
                "Instituto Rodrigo Mendes",
                "Instituto Sou da Paz",
                "Instituto Ethos",
                "Associação Vaga Lume",
                "Projeto Tamar",
                "Instituto Alana",
                "Casa do Zezinho",
                "Instituto Terra",
                "Instituto Dara (ex-Saúde Criança)",
                "Instituto Ayrton Senna",
                "Fundação SOS Mata Atlântica",
                "CIPÓ Comunicação Interativa",
                "Instituto Reação",
                "Instituto Rukha",
                "Gerando Falcões"
        );
        //Cria o adapter, passando a lista
        OngAdapter adapter = new OngAdapter(myList);

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