package com.aula.trabalhoextensionista.voluntario;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aula.trabalhoextensionista.R;
import com.aula.trabalhoextensionista.data.models.Ong;
import com.aula.trabalhoextensionista.data.models.Voluntario;
import com.aula.trabalhoextensionista.ong.OngAdapter;

import java.util.List;

public class VoluntarioAdapter extends RecyclerView.Adapter<VoluntarioAdapter.ViewHolder>  {

    private final List<Voluntario> voluntarios;

    private final VoluntarioAdapter.OnItemClickListener clickVoluntarioListener;


    public VoluntarioAdapter(List<Voluntario> voluntarios, VoluntarioAdapter.OnItemClickListener listener) {
        this.voluntarios = voluntarios;
        this.clickVoluntarioListener = listener;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.voluntarioItem);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.voluntario_item, parent, false);
        return new VoluntarioAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Voluntario voluntario = voluntarios.get(position);

        holder.textView.setText(voluntario.getNome());

        // Seta o onClick do item na listagem
        holder.itemView.setOnClickListener(v -> {
            clickVoluntarioListener.onItemClick(voluntario);
        });
    }

    public interface OnItemClickListener {
        void onItemClick(Voluntario voluntario);
    }

    @Override
    public int getItemCount() {
        return voluntarios.size();
    }
}
