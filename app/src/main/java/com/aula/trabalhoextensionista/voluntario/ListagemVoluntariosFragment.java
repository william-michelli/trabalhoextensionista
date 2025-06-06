package com.aula.trabalhoextensionista.voluntario;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aula.trabalhoextensionista.R;
import com.aula.trabalhoextensionista.data.AppDatabase;
import com.aula.trabalhoextensionista.data.dao.OngDAO;
import com.aula.trabalhoextensionista.data.dao.VoluntarioDAO;
import com.aula.trabalhoextensionista.data.models.Ong;
import com.aula.trabalhoextensionista.data.models.Voluntario;
import com.aula.trabalhoextensionista.databinding.FragmentListagemOngsBinding;
import com.aula.trabalhoextensionista.databinding.FragmentListagemVoluntariosBinding;
import com.aula.trabalhoextensionista.ong.ListagemOngsFragment;
import com.aula.trabalhoextensionista.ong.OngAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListagemVoluntariosFragment extends Fragment {

    private FragmentListagemVoluntariosBinding binding;

    private VoluntarioDAO voluntarioDao;

    private RecyclerView recyclerView;

    private FirebaseFirestore firebaseDB = FirebaseFirestore.getInstance();
    List<Voluntario> voluntarios = new ArrayList<>();

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentListagemVoluntariosBinding.inflate(inflater, container, false);

        //Ao clicar botao + vai para tela de cadastro de ONG
        binding.fab.setOnClickListener(view -> NavHostFragment.findNavController(ListagemVoluntariosFragment.this)
                .navigate(R.id.action_Listagem_to_NovoVoluntario));

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Pega a recycler view e atribui
        recyclerView = view.findViewById(R.id.recyclerViewListagemVoluntarios);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    @Override
    public void onResume() {
        super.onResume();
        AppDatabase db = AppDatabase.getDatabase(getContext());
        voluntarioDao = db.VoluntarioDAO();

        new Thread(() -> {
            //Busca ONGS no banco
            //voluntarios = voluntarioDao.getAllVoluntarios();

            new Thread(() -> {
                //Busca ONGS no banco
                //ongs = ongDao.getAllOngs();

                //Busca dados no FIREBASE
                firebaseDB.collection("voluntario")
                        .get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            voluntarios.clear();

                            for (DocumentSnapshot document : queryDocumentSnapshots) {
                                Voluntario voluntario = document.toObject(Voluntario.class);
                                voluntario.setId(document.getId());
                                voluntarios.add(voluntario);
                            }

                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //region CRIA A LISTAGEM COM DADOS DO BANCO
                                        //Cria o adapter, passando a lista e OnClick do item
                                        VoluntarioAdapter adapter = new VoluntarioAdapter(voluntarios, voluntario -> {
                                            Bundle dadosBundle = new Bundle();
                                            dadosBundle.putSerializable("voluntario", voluntario);

                                            NavHostFragment.findNavController(ListagemVoluntariosFragment.this)
                                                    .navigate(R.id.action_Listagem_to_NovoVoluntario, dadosBundle);
                                        });

                                        //Seta o adapter no RecyclerView
                                        recyclerView.setAdapter(adapter);

                                        //Seta o adapter do recycler view
                                        binding.recyclerViewListagemVoluntarios.setAdapter(adapter);

                                        //endregion
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(e -> Toast.makeText(getContext(), R.string.erro_ao_buscar_ongs, Toast.LENGTH_SHORT).show());
            }).start();

            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //region CRIA A LISTAGEM COM DADOS DO BANCO
                        //Cria o adapter, passando a lista e OnClick do item
                        VoluntarioAdapter adapter = new VoluntarioAdapter(voluntarios, voluntario -> {
                            Bundle dadosBundle = new Bundle();
                            dadosBundle.putSerializable("voluntario", voluntario);

                            NavHostFragment.findNavController(ListagemVoluntariosFragment.this)
                                    .navigate(R.id.action_Listagem_to_NovoVoluntario, dadosBundle);
                        });

                        //Seta o adapter no RecyclerView
                        recyclerView.setAdapter(adapter);

                        //Seta o adapter do recycler view
                        binding.recyclerViewListagemVoluntarios.setAdapter(adapter);

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