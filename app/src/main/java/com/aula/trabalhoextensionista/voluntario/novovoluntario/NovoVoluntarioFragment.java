package com.aula.trabalhoextensionista.voluntario.novovoluntario;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
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
    private FragmentNovoVoluntarioBinding binding;

    private FirebaseFirestore firebaseDB = FirebaseFirestore.getInstance();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);//Para mostrar o menu de salvar
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

        binding.txtEstado.setFilters(new InputFilter[] {
                new InputFilter.LengthFilter(2),
                new InputFilter.AllCaps()
        });

        EditText txtId = view.findViewById(R.id.txtId);
        EditText txtDataNascimento = view.findViewById(R.id.txtDataNascimento);

        txtId.setText("0");

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

                // Remove todos os caracteres nao digito
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
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            EditText idEditText = rootView.findViewById(R.id.txtId);
            EditText nomeEditText = rootView.findViewById(R.id.txtNome);
            EditText dataNascimentoEditText = rootView.findViewById(R.id.txtDataNascimento);
            EditText telefoneEditText = rootView.findViewById(R.id.txtTelefone);
            EditText txtPais = rootView.findViewById(R.id.txtPais);
            EditText txtEstado = rootView.findViewById(R.id.txtEstado);
            EditText txtCidade = rootView.findViewById(R.id.txtCidade);
            EditText interessesEditText = rootView.findViewById(R.id.txtInteresses);
            EditText emailEditText = rootView.findViewById(R.id.txtEmail);
            EditText senhaEditText = rootView.findViewById(R.id.txtSenha);

            String id = idEditText.getText().toString().trim();
            String nome = nomeEditText.getText().toString().trim();
            String dataNascimento = dataNascimentoEditText.getText().toString().trim();
            String telefone = telefoneEditText.getText().toString().trim();
            String pais = txtPais.getText().toString().trim();
            String estado = txtEstado.getText().toString().trim();
            String cidade = txtCidade.getText().toString().trim();
            String interesses = interessesEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim().toLowerCase();;
            String senha = senhaEditText.getText().toString().trim();

            Voluntario novoVoluntario = new Voluntario(nome, email, senha, dataNascimento, interesses, pais, estado, cidade, telefone);
            enviaVoluntarioFirebase(novoVoluntario);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void enviaVoluntarioFirebase(Voluntario voluntario) {
        new Thread(() -> {
            firebaseDB.collection("voluntario").add(voluntario);

            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "Voluntário cadastrado!", Toast.LENGTH_SHORT).show();

                    // Volta pra tela anterior
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
