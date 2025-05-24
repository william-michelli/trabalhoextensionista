package com.aula.trabalhoextensionista.ong.novaong;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.aula.trabalhoextensionista.R;
import com.aula.trabalhoextensionista.data.AppDatabase;
import com.aula.trabalhoextensionista.data.dao.OngDAO;
import com.aula.trabalhoextensionista.data.models.Ong;
import com.aula.trabalhoextensionista.databinding.FragmentNovaOngBinding;

public class NovaOngFragment extends Fragment {
    private View rootView;

    Ong ong = null;


    private OngDAO ongDao;
    private FragmentNovaOngBinding binding;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // ✅ Required!

        // Pega a ong que veio da listagem (Se foi clicado na listagem)
        if (getArguments() != null) {
            ong = (Ong) getArguments().getSerializable("ong");
        }
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentNovaOngBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);
        rootView = binding.getRoot();
        return binding.getRoot();
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppDatabase db = AppDatabase.getDatabase(getContext());
        ongDao = db.OngDAO();

        //region Se foi clicado na listagem mostra detalhes daquela ONG
        EditText txtId = view.findViewById(R.id.txtId);

        if (ong != null) {
            EditText txtNome = view.findViewById(R.id.txtNome);
            EditText txtDescricao = view.findViewById(R.id.txtDescricao);
            EditText txtNecessidades = view.findViewById(R.id.txtNecessidades);

            txtId.setText(String.valueOf(ong.getId()));
            txtNome.setText(ong.getNome());
            txtDescricao.setText(ong.getDescricao());
            txtNecessidades.setText(ong.getNecessidade());

            //Coloca READ-ONLY porque esta lendo detalhes
            txtNome.setEnabled(false);
            txtDescricao.setEnabled(false);
            txtNecessidades.setEnabled(false);
        } else {
            txtId.setText("0");
        }

        //Seta titulo tela de cadastro
        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            if (ong != null && ong.getId() != 0) {
                activity.getSupportActionBar().setTitle("Detalhes ONG");
            } else {
                activity.getSupportActionBar().setTitle("Cadastrar ONG");
            }
        }
        //endregion
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem saveItem = menu.findItem(R.id.action_save);

        // Check if ONG exists and has an ID, then hide the button
        if (ong != null && ong.getId() != 0) {
            saveItem.setVisible(false);
        } else {
            saveItem.setVisible(true);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            EditText idEditText = rootView.findViewById(R.id.txtId);
            EditText nomeEditText = rootView.findViewById(R.id.txtNome);
            EditText descricaoEditText = rootView.findViewById(R.id.txtDescricao);
            EditText necessidadesEditText = rootView.findViewById(R.id.txtNecessidades);


            //Pega valores
            int id = Integer.parseInt(idEditText.getText().toString().trim());
            String nome = nomeEditText.getText().toString().trim();
            String descricao = descricaoEditText.getText().toString().trim();
            String necessidades = necessidadesEditText.getText().toString().trim();

            Ong ong = new Ong(nome, descricao, necessidades);
            enviaOngFirebase(ong);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void enviaOngFirebase(Ong ong) {
        new Thread(() -> {

            /** TODO -- Aqui vai enviar os dados para o firebase */

            /** TODO (Tirar depois)- Insere no banco OBS: Só deixei aqui pra poder preencher com dados mais facil */
            ongDao.insert(ong);

            getActivity().runOnUiThread(() -> {
                Toast.makeText(getContext(), "ONG cadastrada!", Toast.LENGTH_SHORT).show();

                // Pop backstack safely on main thread
                NavHostFragment.findNavController(this).popBackStack();
            });
        }).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}