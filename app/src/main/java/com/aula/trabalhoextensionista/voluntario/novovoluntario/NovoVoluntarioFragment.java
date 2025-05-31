package com.aula.trabalhoextensionista.voluntario.novovoluntario;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.aula.trabalhoextensionista.data.dao.VoluntarioDAO;
import com.aula.trabalhoextensionista.data.models.Voluntario;
import com.aula.trabalhoextensionista.databinding.FragmentNovoVoluntarioBinding;
import com.google.firebase.firestore.FirebaseFirestore;

public class NovoVoluntarioFragment extends Fragment {
    private View rootView;

    private Voluntario voluntario = null;
    private VoluntarioDAO voluntarioDao;
    private FragmentNovoVoluntarioBinding binding;

    private FirebaseFirestore firebaseDB = FirebaseFirestore.getInstance();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // Required to show menu in Fragment

        // Pega o voluntario que veio da listagem (Se foi clicado na listagem)
        if (getArguments() != null) {
            voluntario = (Voluntario) getArguments().getSerializable("voluntario");
        }
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentNovoVoluntarioBinding.inflate(inflater, container, false);
        rootView = binding.getRoot();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppDatabase db = AppDatabase.getDatabase(getContext());
        voluntarioDao = db.VoluntarioDAO();

        EditText txtId = view.findViewById(R.id.txtId);
        EditText txtDataNascimento = view.findViewById(R.id.txtDataNascimento);

        if (voluntario != null) {
            EditText txtNome = view.findViewById(R.id.txtNome);
            EditText txtInteresses = view.findViewById(R.id.txtInteresses);
            EditText txtEmail = view.findViewById(R.id.txtEmail);
            EditText txtSenha = view.findViewById(R.id.txtSenha);
            EditText txtLocalizacao = view.findViewById(R.id.txtLocalizacao);
            EditText txtTelefone = view.findViewById(R.id.txtTelefone);

            txtId.setText(voluntario.getId());
            txtNome.setText(voluntario.getNome());
            txtEmail.setText(voluntario.getEmail());
            txtSenha.setText(voluntario.getSenha());
            txtDataNascimento.setText(voluntario.getDataNascimento());
            txtInteresses.setText(voluntario.getInteresses());
            txtLocalizacao.setText(voluntario.getLocalizacao());
            txtTelefone.setText(voluntario.getTelefone());

            // Disable fields for details view
            txtNome.setEnabled(false);
            txtEmail.setEnabled(false);
            txtSenha.setEnabled(false);
            txtLocalizacao.setEnabled(false);
            txtDataNascimento.setEnabled(false);
            txtInteresses.setEnabled(false);
            txtTelefone.setEnabled(false);
            txtLocalizacao.setEnabled(false);
        } else {
            txtId.setText("0");
        }

        // Set screen title
        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            if (voluntario != null && voluntario.getId() != "") {
                activity.getSupportActionBar().setTitle("Detalhes Voluntário");
            } else {
                activity.getSupportActionBar().setTitle("Cadastrar Voluntário");
            }
        }

        //region MASCARA PRA DATA NASCIMENTO SER dd/mm/yyyy
        txtDataNascimento.addTextChangedListener(new TextWatcher() {
            private boolean isUpdating;
            private final String mask = "##/##/####";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (isUpdating) return;

                isUpdating = true;

                // Remove all non-digit characters
                String unmasked = s.toString().replaceAll("[^\\d]", "");
                StringBuilder builder = new StringBuilder();

                int i = 0;
                for (char m : mask.toCharArray()) {
                    if (m == '#') {
                        if (i < unmasked.length()) {
                            builder.append(unmasked.charAt(i));
                            i++;
                        } else {
                            break;
                        }
                    } else {
                        if (i < unmasked.length()) {
                            builder.append(m);
                        } else {
                            break;
                        }
                    }
                }

                txtDataNascimento.setText(builder.toString());
                txtDataNascimento.setSelection(builder.length());

                isUpdating = false;
            }
        });
        //endregion
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem saveItem = menu.findItem(R.id.action_save);

        // Hide save button if editing existing voluntario
        if (voluntario != null && voluntario.getId() != "") {
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
            EditText dataNascimentoEditText = rootView.findViewById(R.id.txtDataNascimento);
            EditText telefoneEditText = rootView.findViewById(R.id.txtTelefone);
            EditText localizacaoEditText = rootView.findViewById(R.id.txtLocalizacao);
            EditText interessesEditText = rootView.findViewById(R.id.txtInteresses);
            EditText emailEditText = rootView.findViewById(R.id.txtEmail);
            EditText senhaEditText = rootView.findViewById(R.id.txtSenha);

            String id = idEditText.getText().toString().trim();
            String nome = nomeEditText.getText().toString().trim();
            String dataNascimento = dataNascimentoEditText.getText().toString().trim();
            String telefone = telefoneEditText.getText().toString().trim();
            String localizacao = localizacaoEditText.getText().toString().trim();
            String interesses = interessesEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String senha = senhaEditText.getText().toString().trim();

            Voluntario novoVoluntario = new Voluntario(nome, email, senha, dataNascimento, interesses, localizacao, telefone);
            enviaVoluntarioFirebase(novoVoluntario);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void enviaVoluntarioFirebase(Voluntario voluntario) {
        new Thread(() -> {
            /** TODO -- Aqui vai enviar os dados para o firebase */
            firebaseDB.collection("voluntario").add(voluntario);

            // Insert into local DB
            //voluntarioDao.insert(voluntario);

            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "Voluntário cadastrado!", Toast.LENGTH_SHORT).show();
                    NavHostFragment.findNavController(NovoVoluntarioFragment.this).popBackStack();
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
