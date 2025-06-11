package com.aula.trabalhoextensionista.voluntario.novovoluntario;

import static com.aula.trabalhoextensionista.ong.novaong.DetalhesOngFragment.formatarTelefone;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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
import com.google.android.flexbox.FlexboxLayout;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DetalhesVoluntarioFragment extends Fragment {
    Voluntario voluntario = null;
    private FragmentDetalheVoluntarioBinding binding;

    String necessidadesOng = "";

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // ✅ Required!

        // Pega o voluntario que veio da listagem (Se foi clicado na listagem)
        if (getArguments() != null) {
            voluntario = (Voluntario) getArguments().getSerializable("voluntario");

            // Pega necessidades da ong
            necessidadesOng = (String)getArguments().getSerializable("ong_necessidades");
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
        TextView txtEmail = view.findViewById(R.id.detalhesEmail);
        TextView txtLocalizacao = view.findViewById(R.id.detalhesLocalizacao);
        TextView txtTelefone = view.findViewById(R.id.detalhesTelefone);


        //region SETA AS TAGS

        String interesses = voluntario.getInteresses().toLowerCase();
        String[] tags = interesses.split(";");
        String[] necessidades = necessidadesOng.toLowerCase().split(";");

        Set<String> necessidadeSet = new HashSet<>(Arrays.asList(necessidades));

        FlexboxLayout tagContainer = view.findViewById(R.id.tagsInteresses);

        for (String tag : tags) {
            TextView tagView = new TextView(getContext());
            tagView.setText(tag);
            tagView.setTextSize(14);
            tagView.setPadding(24, 12, 24, 12);

            // Checa se combina
            int backgroundColor = necessidadeSet.contains(tag)
                    ? Color.parseColor("#00ef69") // Verde
                    : Color.parseColor("#D2D2D2");

            int corLetra = necessidadeSet.contains(tag)
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

        txtNome.setText(voluntario.getNome());
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