package com.automatodev.gcadmin.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.automatodev.gcadmin.R;
import com.automatodev.gcadmin.provider.UsersProvider;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.DataHandler>{
    List<UsersProvider> usersProviders;

    public UsersAdapter(List<UsersProvider> usersProviders) {
        this.usersProviders = usersProviders;
    }

    @NonNull
    @Override

    public DataHandler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_celula_users,parent, false);
        return new DataHandler(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull DataHandler holder, int position) {
        holder.txt_name_celula.setText(usersProviders.get(position).getUserName());
        holder.txt_email_celula.setText(usersProviders.get(position).getUserEmail());
    }

    @Override
    public int getItemCount() {
        return usersProviders.size();
    }

    public class DataHandler extends RecyclerView.ViewHolder {
        private TextView txt_name_celula;
        private TextView txt_email_celula;
        public DataHandler(@NonNull View itemView) {
            super(itemView);

            txt_email_celula = itemView.findViewById(R.id.txt_email_celula);
            txt_name_celula = itemView.findViewById(R.id.txt_name_celula);
        }
    }
}
