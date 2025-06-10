package com.aula.trabalhoextensionista.login;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aula.trabalhoextensionista.R;
import com.aula.trabalhoextensionista.databinding.FragmentLoginBinding;
import com.aula.trabalhoextensionista.voluntario.ListagemVoluntariosFragment;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnEntrar.setOnClickListener(v -> {
            String email = binding.editTextEmail.getText().toString().trim().toLowerCase();;
            String senha = binding.editTextSenha.getText().toString().trim();

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(requireContext(), "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
                return;
            }

            FirebaseFirestore firebaseDB = FirebaseFirestore.getInstance();

            // Search Voluntario collection
            firebaseDB.collection("voluntario")
                    .whereEqualTo("email", email)
                    .get()
                    .addOnSuccessListener(voluntarioQuery -> {
                        if (!voluntarioQuery.isEmpty()) {
                            for (DocumentSnapshot doc : voluntarioQuery) {
                                String nome = doc.getString("nome");
                                String senhaNoBanco = doc.getString("senha");
                                String interesses = doc.getString("interesses");

                                if(Objects.equals(senhaNoBanco, senha)){
                                    Toast.makeText(requireContext(), "Bem Vindo Voluntário " + nome, Toast.LENGTH_SHORT).show();

                                    Bundle dadosBundle = new Bundle();
                                    dadosBundle.putSerializable("voluntario_interesses", interesses);

                                    // Navega para listagem de ONGS (Voluntario ve ONGS)
                                    NavHostFragment.findNavController(LoginFragment.this)
                                            .navigate(R.id.action_Login_to_ListagemOngs, dadosBundle);
                                } else {
                                    Toast.makeText(requireContext(), "Senha incorreta", Toast.LENGTH_SHORT).show();
                                }
                                return;
                            }
                        } else {
                            // Not found in voluntario, search ong
                            firebaseDB.collection("ong")
                                    .whereEqualTo("email", email)
                                    .get()
                                    .addOnSuccessListener(ongQuery -> {
                                        if (!ongQuery.isEmpty()) {
                                            for (DocumentSnapshot doc : ongQuery) {
                                                String nome = doc.getString("nome");
                                                String senhaNoBanco = doc.getString("senha");
                                                String necessidades = doc.getString("necessidades");

                                                if(Objects.equals(senhaNoBanco, senha)){
                                                    Toast.makeText(requireContext(), "Bem Vindo ONG " + nome, Toast.LENGTH_SHORT).show();

                                                    Bundle dadosBundle = new Bundle();
                                                    dadosBundle.putSerializable("ong_necessidades", necessidades);

                                                    // Navega para listagem de Voluntarios (ONG ve Voluntarios)
                                                    NavHostFragment.findNavController(LoginFragment.this)
                                                            .navigate(R.id.action_Login_to_ListagemVoluntarios, dadosBundle);
                                                } else {
                                                    Toast.makeText(requireContext(), "Senha incorreta", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        } else {
                                            Toast.makeText(requireContext(), "Nenhum voluntário ou ONG encontrado com esse email.", Toast.LENGTH_LONG).show();
                                        }
                                    })
                                    .addOnFailureListener(e ->
                                            Toast.makeText(requireContext(), "Erro ao buscar ONG - detalhes:" + e, Toast.LENGTH_LONG).show());
                        }
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(requireContext(), "Erro ao buscar Voluntário - detalhes:" + e, Toast.LENGTH_LONG).show());
        });


        binding.btnCadastrarOng.setOnClickListener(v -> {
            //Ao clicar botao vai para tela de cadastro de ONG
            NavHostFragment.findNavController(LoginFragment.this)
                    .navigate(R.id.action_Login_to_NovaOng);
        });

        binding.btnCadastrarVoluntario.setOnClickListener(v -> {
            //Ao clicar botao vai para tela de cadastro de Voluntario
            NavHostFragment.findNavController(LoginFragment.this)
                    .navigate(R.id.action_Login_to_NovoVoluntario);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;  // Avoid memory leaks
    }
}