package com.aula.trabalhoextensionista.ong.novaong;

import static android.view.View.GONE;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
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
import com.aula.trabalhoextensionista.databinding.FragmentDetalheOngBinding;
import com.aula.trabalhoextensionista.databinding.FragmentNovaOngBinding;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetalhesOngFragment extends Fragment {
    Ong ong = null;
    private FragmentDetalheOngBinding binding;

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
        binding = FragmentDetalheOngBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Mostra detalhes daquela ONG
        TextView txtNome = view.findViewById(R.id.detalhesNome);
        TextView txtDetalhes = view.findViewById(R.id.detalhesDetalhes);
        TextView txtNecessidades = view.findViewById(R.id.detalhesNecessidades);
        TextView txtEmail = view.findViewById(R.id.detalhesEmail);
        TextView txtLocalizacao = view.findViewById(R.id.detalhesLocalizacao);
        TextView txtTelefone = view.findViewById(R.id.detalhesTelefone);

        txtNome.setText(ong.getNome());
        txtDetalhes.setText(ong.getDetalhes());
        
        String necessidades = ong.getNecessidades();
        txtNecessidades.setText(necessidades != null ? necessidades.toLowerCase() : "");

        txtEmail.setText(ong.getEmail());

        var local = ong.getPais() + ", " + ong.getEstado() + " - " + ong.getCidade();
        txtLocalizacao.setText(local);

        var numeroTel = ong.getTelefone();
        String numeroFormatado = formatarTelefone(numeroTel);
        txtTelefone.setText(numeroFormatado);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static String formatarTelefone(String numero) {
        // Remove tudo que não for número
        numero = numero.replaceAll("\\D", "");

        if (numero.length() == 11) {
            // Exemplo: 11999991234 → (11) 99999-1234
            return String.format("(%s) %s-%s",
                    numero.substring(0, 2),
                    numero.substring(2, 7),
                    numero.substring(7));
        } else if (numero.length() == 10) {
            // Exemplo: 1132121234 → (11) 3212-1234
            return String.format("(%s) %s-%s",
                    numero.substring(0, 2),
                    numero.substring(2, 6),
                    numero.substring(6));
        } else {
            return "";
        }
    }

}