package com.automatodev.gcadmin.adapter;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.automatodev.gcadmin.R;
import com.automatodev.gcadmin.provider.UserOrderProvider;
import com.bumptech.glide.Glide;
import com.google.firebase.Timestamp;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.DataHandler> {
    private List<UserOrderProvider> uOrderList;
    private Activity context;
    private OnItemClickListener listener;
    private OnItemLongClickListener longListener;

    public interface OnItemClickListener{
        void setOnItemClickListener(int position);
    }
    public interface OnItemLongClickListener{
        void setOnItemLongClickListener(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener longListener){
        this.longListener = longListener;
    }
    public OrdersAdapter(List<UserOrderProvider> uOrderList, Activity context) {
        this.uOrderList = uOrderList;
        this.context = context;
    }

    @NonNull
    @Override
    public DataHandler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_celula_pedidos,parent, false);
       return new DataHandler(convertView, listener, longListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull DataHandler holder, int position) {
        UserOrderProvider uOrderProvider = uOrderList.get(position);

        holder.txt_nameOrder_celula.setText(uOrderProvider.getUserName());
        holder.txt_sitOrder_celula.setText(uOrderProvider.getStatus());
        holder.txt_itemOrder_celula.setText(String.valueOf(uOrderProvider.getQtdItens()));
        holder.txt_dataOrder_celula.setText(convertDateTime(uOrderProvider.getTimestamp(),0));
        holder.txt_horaOrder_celula.setText(convertDateTime(uOrderProvider.getTimestamp(),1));
        holder.txt_totalOrder_celula.setText(convertMoeda(uOrderProvider.getTotalPayment()));
        Glide.with(context).load(uOrderProvider.getUserUrlPhoto())
                .into(holder.img_userOrder_celula);

    }


    @Override
    public int getItemCount() {
        return uOrderList.size();


    }

    public class DataHandler extends RecyclerView.ViewHolder {
        private TextView txt_nameOrder_celula;
        private TextView txt_totalOrder_celula;
        private TextView txt_itemOrder_celula;
        private TextView txt_sitOrder_celula;
        private TextView txt_dataOrder_celula;
        private TextView txt_horaOrder_celula;
        private CircleImageView img_userOrder_celula;


        public DataHandler(@NonNull View itemView, final OnItemClickListener listener, final OnItemLongClickListener longListner) {
            super(itemView);
            txt_nameOrder_celula = itemView.findViewById(R.id.txt_nameOrder_celula);
            txt_totalOrder_celula = itemView.findViewById(R.id.txt_totalOrders_celula);
            txt_itemOrder_celula = itemView.findViewById(R.id.txt_itemOrder_celula);
            txt_sitOrder_celula = itemView.findViewById(R.id.txt_pending);
            txt_dataOrder_celula = itemView.findViewById(R.id.txt_dataOrder_celula);
            txt_horaOrder_celula = itemView.findViewById(R.id.txt_HoraOrder_celula);
            img_userOrder_celula = itemView.findViewById(R.id.img_userOrders_celula);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                            listener.setOnItemClickListener(position);                  }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(longListener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            longListner.setOnItemLongClickListener(position);
                        }
                    }

                    return true;
                }
            });
        }
    }

    public String convertDateTime(Timestamp timestamp, int value){
        Locale locale = new Locale("pt","BR");
        SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyy",locale);
        SimpleDateFormat hour = new SimpleDateFormat("HH:mm:ss",locale);

        if (value == 0)
            return date.format(timestamp.toDate());
        else
            return hour.format(timestamp.toDate());
    }
    public String convertMoeda(double value){
        Locale locale = new Locale("pt","BR");
        NumberFormat decimal = NumberFormat.getCurrencyInstance(locale);
        return decimal.format(value);
    }
}
