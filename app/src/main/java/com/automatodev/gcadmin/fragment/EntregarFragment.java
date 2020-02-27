package com.automatodev.gcadmin.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.automatodev.gcadmin.R;
import com.automatodev.gcadmin.adapter.OrdersAdapter;
import com.automatodev.gcadmin.provider.UserOrderProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EntregarFragment extends Fragment {

    private RecyclerView recycler_prepare;
    private List<UserOrderProvider> uList;
    private FirebaseFirestore firestore;
    private OrdersAdapter ordersAdapter;

    private String sit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entregar, container, false);

        firestore = FirebaseFirestore.getInstance();
        recycler_prepare = view.findViewById(R.id.recycler_delivered);

        fireGetOrdersEr();
        showData();
        sClick();
        sLongCick();
        return view;
    }
    private void fireGetOrdersEr() {
        uList = new ArrayList<>();
        firestore.collection("userAdmin").document("admin")
                .collection("pedidos")
                .addSnapshotListener( new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        uList.clear();
                        List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot doc : docs) {
                            UserOrderProvider userOrderProvider = doc.toObject(UserOrderProvider.class);
                            if (userOrderProvider.getStatus().equals("Para entrega"))
                                uList.add(userOrderProvider);
                        }
                        ordersAdapter.notifyDataSetChanged();
                    }


                });

               /* .get().addOnCompleteListener(getActivity(), new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
               uList.clear();
               for (DocumentSnapshot docs: task.getResult()){
                   if (docs.exists()){
                       UserOrderProvider userOrderProvider = docs.toObject(UserOrderProvider.class);
                       if (userOrderProvider.getStatus().equals("Preparação"))
                           uList.add(userOrderProvider);
                   }
               }
               ordersAdapter.notifyDataSetChanged();
            }
        });*/
    }

    private void showData() {
        recycler_prepare.hasFixedSize();
        recycler_prepare.setLayoutManager(new LinearLayoutManager(getActivity()));
        ordersAdapter = new OrdersAdapter(uList, getActivity());
        recycler_prepare.setAdapter(ordersAdapter);
    }

    private void sClick() {
        ordersAdapter.setOnItemClickListener(new OrdersAdapter.OnItemClickListener() {
            @Override
            public void setOnItemClickListener(int position) {
                ordersAdapter.notifyItemChanged(position);
            }
        });
    }

    private void sLongCick() {
        ordersAdapter.setOnItemLongClickListener(new OrdersAdapter.OnItemLongClickListener() {
            @Override
            public void setOnItemLongClickListener(int position) {
                ordersAdapter.notifyItemChanged(position);
                alterSituation(position);
            }

        });
    }

    private void alterSituation(int position) {
        final UserOrderProvider userOrderProvider = uList.get(position);

        final View vBottom = getLayoutInflater().inflate(R.layout.layout_update_item, null);
        final BottomSheetDialog bt = new BottomSheetDialog(getActivity(), R.style.BottomSheetDialogTheme);

        final RadioGroup radio_group = vBottom.findViewById(R.id.radio_group);
        final RadioButton rd_pendente = vBottom.findViewById(R.id.rd_pendente);
        final RadioButton rd_preparacao = vBottom.findViewById(R.id.rd_preparacao);
        final RadioButton rd_entregar = vBottom.findViewById(R.id.rd_entregar);
        final RadioButton rd_entregue = vBottom.findViewById(R.id.rd_entregue);
        final Button btn_send_status = vBottom.findViewById(R.id.btn_send_status);

        switch (userOrderProvider.getStatus()) {
            case "Pendente":
                rd_pendente.setChecked(true);
                rd_pendente.setEnabled(false);
                break;
            case "Preparação":
                rd_preparacao.setChecked(true);
                rd_preparacao.setEnabled(false);
                break;
            case "Para entrega":
                rd_entregar.setChecked(true);
                rd_entregar.setEnabled(false);
                break;
            case "Enntregue":
                rd_entregue.setChecked(true);
                rd_entregue.setEnabled(false);
        }

        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rd_pendente:
                        sit = rd_pendente.getText().toString();
                        break;
                    case R.id.rd_preparacao:
                        sit = rd_preparacao.getText().toString();
                        break;
                    case R.id.rd_entregar:
                        sit = rd_entregar.getText().toString();
                        break;
                    case R.id.rd_entregue:
                        sit = rd_entregue.getText().toString();
                        break;
                }
            }
        });

        btn_send_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSituation(sit, userOrderProvider.getUserUid());
                bt.dismiss();
            }
        });

        bt.setContentView(vBottom);
        bt.show();
    }

    private void updateSituation(String value, String userUid) {
        firestore.collection("userAdmin").document("admin")
                .collection("pedidos")
                .document(userUid)
                .update("status", value)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Sucesso", Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("logx", "Error: " + e.getMessage(), e);
                Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
