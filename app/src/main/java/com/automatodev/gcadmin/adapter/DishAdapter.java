package com.automatodev.gcadmin.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.automatodev.gcadmin.R;
import com.automatodev.gcadmin.provider.DishProvider;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.List;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.DataHandler> {
    private List<DishProvider> dishProviderList;
    private Activity context;

    public DishAdapter(List<DishProvider> dishProviderList, Activity context) {
        this.dishProviderList = dishProviderList;
        this.context = context;
    }

    @NonNull
    @Override
    public DishAdapter.DataHandler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_celula_cardapio,parent,false);
        return new DataHandler(convertView);

    }

    @Override
    public void onBindViewHolder(@NonNull DishAdapter.DataHandler holder, int position) { ;
        DishProvider dishProvider = dishProviderList.get(position);
        holder.txt_ingrediente_layout_celula.setText(dishProvider.getDishDescOne());
        holder.txt_item_title_layout_celula.setText(dishProvider.getDishName());
        holder.txt_price_layout.setText(String.valueOf(dishProvider.getDishValue()));
        Glide.with(context).load(dishProvider.getDishUrlPhoto()).centerCrop().transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.img_dish_layout_celula);
    }

    @Override
    public int getItemCount() {
        return dishProviderList.size();

    }

    public class DataHandler extends RecyclerView.ViewHolder {
        private TextView txt_item_title_layout_celula;
        private TextView txt_ingrediente_layout_celula;
        private TextView txt_price_layout;
        private ImageView img_dish_layout_celula;

        public DataHandler(@NonNull View itemView) {
            super(itemView);
            txt_item_title_layout_celula = itemView.findViewById(R.id.txt_item_title_layout_celula);
            txt_ingrediente_layout_celula = itemView.findViewById(R.id.txt_ingrediente);
            txt_price_layout = itemView.findViewById(R.id.txt_price_layout_celula);
            img_dish_layout_celula = itemView.findViewById(R.id.img_dish_layout_celula);
        }
    }
}
