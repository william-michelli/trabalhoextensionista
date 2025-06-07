package com.aula.trabalhoextensionista.ong;

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
import com.aula.trabalhoextensionista.data.dao.OngDAO;
import com.aula.trabalhoextensionista.data.models.Ong;
import com.aula.trabalhoextensionista.databinding.FragmentListagemOngsBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ListagemOngsFragment extends Fragment {

    private FragmentListagemOngsBinding binding;

    private OngDAO ongDao;

    private RecyclerView recyclerView;
    private FirebaseFirestore firebaseDB = FirebaseFirestore.getInstance();
    List<Ong> ongs = new ArrayList<>();

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentListagemOngsBinding.inflate(inflater, container, false);
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
            //Busca dados no FIREBASE
            firebaseDB.collection("ong")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    ongs.clear();

                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        Ong ong = document.toObject(Ong.class);
                        ong.setId(document.getId());
                        ongs.add(ong);
                    }
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
                                            .navigate(R.id.action_Listagem_to_DetalhesOng, dadosBundle);
                                });

                                //Seta o adapter no RecyclerView
                                recyclerView.setAdapter(adapter);

                                //Seta o adapter do recycler view
                                binding.recyclerViewListagemOngs.setAdapter(adapter);

                                //endregion
                            }
                        });
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(getContext(), R.string.erro_ao_buscar_ongs, Toast.LENGTH_SHORT).show());
        }).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}