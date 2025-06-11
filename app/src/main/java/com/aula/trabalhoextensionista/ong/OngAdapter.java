package com.aula.trabalhoextensionista.ong;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.aula.trabalhoextensionista.R;
import com.aula.trabalhoextensionista.data.models.Ong;

import java.util.List;

public class OngAdapter extends RecyclerView.Adapter<OngAdapter.ViewHolder> {

    private final List<Ong> ongs;

    private final OnItemClickListener clickOngListener;

    public OngAdapter(List<Ong> ongs, OnItemClickListener listener) {
        this.ongs = ongs;
        this.clickOngListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.ongItem);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ong_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ong ong = ongs.get(position);

        holder.textView.setText(ong.getNome());

        // Seta o onClick do item na listagem
        holder.itemView.setOnClickListener(v -> {
            clickOngListener.onItemClick(ong);
        });

        // Destaque se houver match
        if (ong.isDestaque()) {
            holder.textView.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.verde_destaque));
        } else {
            holder.textView.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.black));
        }
    }


    public interface OnItemClickListener {
        void onItemClick(Ong ong);
    }

    @Override
    public int getItemCount() {
        return ongs.size();
    }
}
