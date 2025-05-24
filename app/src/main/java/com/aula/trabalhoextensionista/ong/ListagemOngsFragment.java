package com.aula.trabalhoextensionista.ong;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aula.trabalhoextensionista.R;
import com.aula.trabalhoextensionista.data.AppDatabase;
import com.aula.trabalhoextensionista.data.dao.OngDAO;
import com.aula.trabalhoextensionista.data.models.Ong;
import com.aula.trabalhoextensionista.databinding.FragmentListagemOngsBinding;

import java.util.ArrayList;
import java.util.List;

public class ListagemOngsFragment extends Fragment {

    private FragmentListagemOngsBinding binding;

    private OngDAO ongDao;

    private RecyclerView recyclerView;

    List<Ong> ongs = new ArrayList<>();

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentListagemOngsBinding.inflate(inflater, container, false);

        //Ao clicar botao + vai para tela de cadastro de ONG
        binding.fab.setOnClickListener(view -> NavHostFragment.findNavController(ListagemOngsFragment.this)
                .navigate(R.id.action_Listagem_to_NovaOng));

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Pega a recycler view e atribui
        recyclerView = view.findViewById(R.id.recyclerViewListagemOngs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onResume() {
        super.onResume();
        AppDatabase db = AppDatabase.getDatabase(getContext());
        ongDao = db.OngDAO();

        new Thread(() -> {
            //Busca ONGS no banco
            ongs = ongDao.getAllOngs();

            // TODO Valores padrao TIRAR DEPOIS******
            ongs.add(new Ong(1, "Teste", "teste descricao", "veterinario"));
            ongs.add(new Ong(2, "Teste 2", "teste descricao", "cuidadores;medicos"));

            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //region CRIA A LISTAGEM COM DADOS DO BANCO
                        //Cria o adapter, passando a lista e OnClick do item
                        OngAdapter adapter = new OngAdapter(ongs, ong -> {
                            Bundle dadosBundle = new Bundle();
                            dadosBundle.putSerializable("ong", ong);

                            NavHostFragment.findNavController(ListagemOngsFragment.this)
                                    .navigate(R.id.action_Listagem_to_NovaOng, dadosBundle);
                        });

                        //Seta o adapter no RecyclerView
                        recyclerView.setAdapter(adapter);


                        //Seta o adapter do recycler view
                        binding.recyclerViewListagemOngs.setAdapter(adapter);

                        //endregion
                    }
                });
            }
        }).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}