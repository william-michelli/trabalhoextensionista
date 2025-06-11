package com.aula.trabalhoextensionista.ong.novaong;

import static android.view.View.GONE;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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
import com.google.android.flexbox.FlexboxLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DetalhesOngFragment extends Fragment {
    Ong ong = null;
    private FragmentDetalheOngBinding binding;

    String interessesVoluntario = "";

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // ✅ Required!

        // Pega a ong que veio da listagem (Se foi clicado na listagem)
        if (getArguments() != null) {
            ong = (Ong) getArguments().getSerializable("ong");

            // Pega interesses do voluntario
            interessesVoluntario = (String)getArguments().getSerializable("voluntario_interesses");
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
        TextView txtEmail = view.findViewById(R.id.detalhesEmail);
        TextView txtLocalizacao = view.findViewById(R.id.detalhesLocalizacao);
        TextView txtTelefone = view.findViewById(R.id.detalhesTelefone);


        //region SETA AS TAGS

        String necessidades = ong.getNecessidades().toLowerCase();
        String[] tags = necessidades.split(";");
        String[] interesses = interessesVoluntario.toLowerCase().split(";");

        Set<String> interessesSet = new HashSet<>(Arrays.asList(interesses));

        FlexboxLayout tagContainer = view.findViewById(R.id.tagsNecessidades);

        for (String tag : tags) {
            TextView tagView = new TextView(getContext());
            tagView.setText(tag);
            tagView.setTextSize(14);
            tagView.setPadding(24, 12, 24, 12);

            // Checa se combina
            int backgroundColor = interessesSet.contains(tag)
                    ? Color.parseColor("#00ef69") // Verde
                    : Color.parseColor("#D2D2D2");

            int corLetra = interessesSet.contains(tag)
                    ? Color.parseColor("#0c640f") //Verde
                    : Color.parseColor("#000000");

            tagView.setTextColor(corLetra);

            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(backgroundColor);
            drawable.setCornerRadius(1000f); //Formato de pilula

            tagView.setBackground(drawable);


            // Margem
            FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                    FlexboxLayout.LayoutParams.WRAP_CONTENT,
                    FlexboxLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 8, 8, 8);
            tagView.setLayoutParams(params);

            tagContainer.addView(tagView);
        }

        //endregion

        txtNome.setText(ong.getNome());
        txtDetalhes.setText(ong.getDetalhes());
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