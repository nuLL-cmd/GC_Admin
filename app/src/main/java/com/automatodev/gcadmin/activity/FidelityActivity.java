package com.automatodev.gcadmin.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.automatodev.gcadmin.R;
import com.automatodev.gcadmin.adapter.FidelityAdapter;
import com.automatodev.gcadmin.provider.FidelityProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FidelityActivity extends AppCompatActivity {
    public static boolean status;
    private List<FidelityProvider> fidelityProviders;
    private RecyclerView recycler_fidelity;
    private FidelityAdapter adapter;
    private FirebaseFirestore firestore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fidelity);

        recycler_fidelity = findViewById(R.id.recycler_fidelity);

        firestore = FirebaseFirestore.getInstance();

        showDataFidelity();
        fireGetFidelity();
    }
    private void showDataFidelity(){
        recycler_fidelity.hasFixedSize();
        recycler_fidelity.setLayoutManager(new LinearLayoutManager(this));
        fidelityProviders  = new ArrayList<>();
        adapter = new FidelityAdapter(fidelityProviders);
        recycler_fidelity.setAdapter(adapter);

    }
    private void fireGetFidelity(){
        firestore.collection("userAdmin").document("fidelity").collection("usersCard")
                .addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        fidelityProviders.clear();
                        List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot doc: docs){
                            fidelityProviders.add(doc.toObject(FidelityProvider.class));
                        }

                        adapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        status = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        status = true;
    }
}
