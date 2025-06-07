package com.aula.trabalhoextensionista.ong.novaong;

import android.os.Bundle;
import android.text.InputFilter;
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
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.aula.trabalhoextensionista.R;
import com.aula.trabalhoextensionista.data.models.Ong;
import com.aula.trabalhoextensionista.databinding.FragmentNovaOngBinding;
import com.google.firebase.firestore.FirebaseFirestore;

public class NovaOngFragment extends Fragment {
    private View rootView;

    Ong ong = null;

    private FragmentNovaOngBinding binding;
    private FirebaseFirestore firebaseDB = FirebaseFirestore.getInstance();

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);//Para mostrar o menu de salvar
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

        binding.txtEstado.setFilters(new InputFilter[] {
                new InputFilter.LengthFilter(2),
                new InputFilter.AllCaps()
        });

        EditText txtId = view.findViewById(R.id.txtId);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            EditText idEditText = rootView.findViewById(R.id.txtId);
            EditText nomeEditText = rootView.findViewById(R.id.txtNome);
            EditText detalhesEditText = rootView.findViewById(R.id.txtDetalhes);
            EditText necessidadesEditText = rootView.findViewById(R.id.txtNecessidades);
            EditText emailEditText = rootView.findViewById(R.id.txtEmail);
            EditText txtPais = rootView.findViewById(R.id.txtPais);
            EditText txtEstado = rootView.findViewById(R.id.txtEstado);
            EditText txtCidade = rootView.findViewById(R.id.txtCidade);
            EditText senhaEditText = rootView.findViewById(R.id.txtSenha);
            EditText telefoneEditText = rootView.findViewById(R.id.txtTelefone);


            //Pega valores
            String id = idEditText.getText().toString().trim();
            String nome = nomeEditText.getText().toString().trim();
            String detalhes = detalhesEditText.getText().toString().trim();
            String necessidades = necessidadesEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim().toLowerCase();;
            String pais = txtPais.getText().toString().trim();
            String estado = txtEstado.getText().toString().trim();
            String cidade = txtCidade.getText().toString().trim();
            String senha = senhaEditText.getText().toString().trim();
            String telefone = telefoneEditText.getText().toString().trim();

            Ong ong = new Ong(nome, email, senha, detalhes, necessidades, pais, estado, cidade, telefone);
            enviaOngFirebase(ong);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void enviaOngFirebase(Ong ong) {
        new Thread(() -> {
            firebaseDB.collection("ong").add(ong);

            getActivity().runOnUiThread(() -> {
                Toast.makeText(getContext(), "ONG cadastrada!", Toast.LENGTH_SHORT).show();

                // Volta pra tela anterior
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