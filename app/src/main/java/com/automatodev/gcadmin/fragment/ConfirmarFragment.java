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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ConfirmarFragment extends Fragment {

    private List<UserOrderProvider> uList;
    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;
    private OrdersAdapter ordersAdapter;

    private RecyclerView recycler_confirm;
    private String sit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirmar, container, false);
        recycler_confirm = view.findViewById(R.id.recycler_confirm);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        fireGetOrdersC();
        showData();
        sClick();
        sLongClick();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void showData() {
        recycler_confirm.hasFixedSize();
        recycler_confirm.setLayoutManager(new LinearLayoutManager(getContext()));
        ordersAdapter = new OrdersAdapter(uList, getActivity());
        recycler_confirm.setAdapter(ordersAdapter);
        ;

    }

    private void fireGetOrdersC() {
        uList = new ArrayList<>();
        firestore.collection("userAdmin").document("admin").collection("pedidos").orderBy("timestamp")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        uList.clear();
                        List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot doc : docs) {
                            UserOrderProvider uProvider = doc.toObject(UserOrderProvider.class);
                            if (uProvider.getStatus().equals("Pendente"))
                                uList.add(uProvider);
                        }
                        ordersAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void sClick() {
        ordersAdapter.setOnItemClickListener(new OrdersAdapter.OnItemClickListener() {
            @Override
            public void setOnItemClickListener(int position) {
                ordersAdapter.notifyItemChanged(position);
                alterSituation(0);
            }
        });
    }

    private void sLongClick() {
        ordersAdapter.setOnItemLongClickListener(new OrdersAdapter.OnItemLongClickListener() {
            @Override
            public void setOnItemLongClickListener(int position) {
                ordersAdapter.notifyItemChanged(position);
                alterSituation(0);
            }
        });
    }

    private void alterSituation(int position) {
        final UserOrderProvider uProvider = uList.get(position);
        final BottomSheetDialog bt = new BottomSheetDialog(getContext(), R.style.BottomSheetDialogTheme);
        final View vBottom = getLayoutInflater().inflate(R.layout.layout_update_item, null);

        final RadioButton rd_pendente = vBottom.findViewById(R.id.rd_pendente);
        final RadioButton rd_preparacao = vBottom.findViewById(R.id.rd_preparacao);
        final RadioButton rd_entregar = vBottom.findViewById(R.id.rd_entregar);
        final RadioButton rd_entregue = vBottom.findViewById(R.id.rd_entregue);
        final Button btn_send_status = vBottom.findViewById(R.id.btn_send_status);
        RadioGroup radio_group = vBottom.findViewById(R.id.radio_group);

        switch (uProvider.getStatus()) {
            case "Pendente":
                rd_pendente.setChecked(true);
                rd_pendente.setEnabled(false);
                break;
            case "Preparação":
                rd_preparacao.setChecked(true);
                rd_preparacao.setEnabled(false);
                break;
            case "Entregar":
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
            public void onClick(View v) {
                updateSituation(sit, uProvider.getUserUid());
                bt.dismiss();
            }
        });

        bt.setContentView(vBottom);
        bt.show();
    }

    private void updateSituation(String sit,String uid){
        firestore.collection("userAdmin").document("admin")
                .collection("pedidos").document(uid).update("status",sit)
        .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getContext(),"Sucesso",Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("logx","Error: "+e.getMessage(),e);
                Toast.makeText(getActivity(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
