package com.aula.trabalhoextensionista.voluntario.novovoluntario;

import static com.aula.trabalhoextensionista.ong.novaong.DetalhesOngFragment.formatarTelefone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.aula.trabalhoextensionista.R;
import com.aula.trabalhoextensionista.data.models.Ong;
import com.aula.trabalhoextensionista.data.models.Voluntario;
import com.aula.trabalhoextensionista.databinding.FragmentDetalheOngBinding;
import com.aula.trabalhoextensionista.databinding.FragmentDetalheVoluntarioBinding;

public class DetalhesVoluntarioFragment extends Fragment {
    Voluntario voluntario = null;
    private FragmentDetalheVoluntarioBinding binding;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // ✅ Required!

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
        binding = FragmentDetalheVoluntarioBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Mostra detalhes daquela ONG
        TextView txtNome = view.findViewById(R.id.detalhesNome);
        TextView txtInteresses = view.findViewById(R.id.detalhesInteresses);
        TextView txtEmail = view.findViewById(R.id.detalhesEmail);
        TextView txtLocalizacao = view.findViewById(R.id.detalhesLocalizacao);
        TextView txtTelefone = view.findViewById(R.id.detalhesTelefone);

        txtNome.setText(voluntario.getNome());

        String interesses = voluntario.getInteresses();
        txtInteresses.setText(interesses != null ? interesses.toLowerCase() : "");

        txtEmail.setText(voluntario.getEmail());

        var local = voluntario.getPais() + ", " + voluntario.getEstado() + " - " + voluntario.getCidade();
        txtLocalizacao.setText(local);

        var numeroTel = voluntario.getTelefone();
        String numeroFormatado = formatarTelefone(numeroTel);
        txtTelefone.setText(numeroFormatado);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}