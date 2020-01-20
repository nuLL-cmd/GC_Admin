package com.automatodev.gcadmin.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.automatodev.gcadmin.R;
import com.automatodev.gcadmin.provider.FidelityProvider;

import java.util.List;

public class FidelityAdapter extends RecyclerView.Adapter<FidelityAdapter.DataHandler> {
    private List<FidelityProvider> fidelityProviders;

    public FidelityAdapter(List<FidelityProvider> fidelityProviders) {
        this.fidelityProviders = fidelityProviders;
    }

    @NonNull
    @Override
    public DataHandler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_celula_fidelity,parent, false);
        return new DataHandler(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull DataHandler holder, int position) {
        FidelityProvider fidelityProvider = fidelityProviders.get(position);
        holder.txt_totalOrders_celula.setText(String.valueOf(fidelityProvider.getTotalOrders()));
        holder.txt_nameFidel_celula.setText(fidelityProvider.getUserName());
    }

    @Override
    public int getItemCount() {

        return fidelityProviders.size();
    }

    public class DataHandler extends RecyclerView.ViewHolder {
        TextView txt_nameFidel_celula;
        TextView txt_totalOrders_celula;
        public DataHandler(@NonNull View itemView) {
            super(itemView);
            txt_nameFidel_celula = itemView.findViewById(R.id.txt_nameFidel_celula);
            txt_totalOrders_celula = itemView.findViewById(R.id.txt_totalOrders_celula);
        }
    }
}
