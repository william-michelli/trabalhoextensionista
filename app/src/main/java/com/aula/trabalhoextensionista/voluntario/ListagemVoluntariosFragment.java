package com.aula.trabalhoextensionista.voluntario;

import android.os.Bundle;
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
import com.aula.trabalhoextensionista.data.dao.VoluntarioDAO;
import com.aula.trabalhoextensionista.data.models.Ong;
import com.aula.trabalhoextensionista.data.models.Voluntario;
import com.aula.trabalhoextensionista.databinding.FragmentListagemVoluntariosBinding;
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

    String necessidadesOng = "";

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentListagemVoluntariosBinding.inflate(inflater, container, false);

        // Pega necessidades da ong
        if (getArguments() != null) {
            necessidadesOng = (String)getArguments().getSerializable("ong_necessidades");
        }

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
            //Busca dados no FIREBASE
            firebaseDB.collection("voluntario")
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        voluntarios.clear();

                        //region SETA OS VOLUNTARIOS que serao destacadas

                        String[] neccessidadesOngLogada = necessidadesOng.toLowerCase().split(";");

                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                            Voluntario voluntario = document.toObject(Voluntario.class);
                            voluntario.setId(document.getId());

                            String interessesStr = voluntario.getInteresses(); // ex: "animais;doações"
                            List<String> interesses = Arrays.asList(interessesStr.toLowerCase().split(";"));

                            // Verifica se alguma necessidade da Ong está nos interesses do Voluntario
                            for (String necessidade : neccessidadesOngLogada) {
                                if (interesses.contains(necessidade)) {
                                    voluntario.setDestaque(true);
                                    break; // já achou um match, não precisa continuar
                                }
                            }

                            voluntario.setId(document.getId());
                            voluntarios.add(voluntario);
                        }
                        //endregion

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
                                                .navigate(R.id.action_Listagem_to_DetalhesVoluntario, dadosBundle);
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
                    .addOnFailureListener(e -> Toast.makeText(getContext(), "Erro ao buscar voluntarios", Toast.LENGTH_SHORT).show());
        }).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}