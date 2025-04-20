package com.aula.trabalhoextensionista;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.aula.trabalhoextensionista.databinding.FragmentTelaInicialSelecaoBinding;

public class TelaInicialSelecaoFragment extends Fragment {

    private FragmentTelaInicialSelecaoBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentTelaInicialSelecaoBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //region Botao que leva para tela de listagem de ONG
        binding.buttonListagemOngs.setOnClickListener(v ->
                NavHostFragment.findNavController(TelaInicialSelecaoFragment.this)
                        .navigate(R.id.action_Selecao_to_ListagemOngs)
        );
        //endregion

        //region Botao que leva para tela de listagem de VOLUNTARIO
        binding.buttonListagemVoluntarios.setOnClickListener(v ->
                NavHostFragment.findNavController(TelaInicialSelecaoFragment.this)
                        .navigate(R.id.action_Selecao_to_ListagemVoluntarios)
        );
        //endregion
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}