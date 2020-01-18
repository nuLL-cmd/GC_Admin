package com.automatodev.gcadmin.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.automatodev.gcadmin.R;
import com.automatodev.gcadmin.provider.CardaptioProvider;

import java.util.List;

public class CardaptioAdapter extends RecyclerView.Adapter<CardaptioAdapter.DataHandler> {
    List<CardaptioProvider> cardaptioProviderList;

    public CardaptioAdapter(List<CardaptioProvider> cardaptioProviderList) {
        this.cardaptioProviderList = cardaptioProviderList;
    }

    @NonNull
    @Override
    public CardaptioAdapter.DataHandler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_celula_cardapio,parent,false);
        return new DataHandler(convertView);

    }

    @Override
    public void onBindViewHolder(@NonNull CardaptioAdapter.DataHandler holder, int position) {
        CardaptioProvider cardaptioProvider = cardaptioProviderList.get(position);
        holder.txt_ingrediente_layout_celula.setText(cardaptioProvider.getDishDescOne());
        holder.txt_item_title_layout_celula.setText(cardaptioProvider.getDishName());
        holder.txt_price_layout.setText(String.valueOf(cardaptioProvider.getDishValue()));
    }

    @Override
    public int getItemCount() {
        return cardaptioProviderList.size();

    }

    public class DataHandler extends RecyclerView.ViewHolder {
        private TextView txt_item_title_layout_celula;
        private TextView txt_ingrediente_layout_celula;
        private TextView txt_price_layout;

        public DataHandler(@NonNull View itemView) {
            super(itemView);
            txt_item_title_layout_celula = itemView.findViewById(R.id.txt_item_title_layout_celula);
            txt_ingrediente_layout_celula = itemView.findViewById(R.id.txt_ingrediente);
            txt_price_layout = itemView.findViewById(R.id.txt_price_layout_celula);
        }
    }
}
