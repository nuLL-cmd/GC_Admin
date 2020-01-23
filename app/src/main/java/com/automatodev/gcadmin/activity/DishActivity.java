package com.automatodev.gcadmin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.automatodev.gcadmin.R;
import com.automatodev.gcadmin.adapter.DishAdapter;
import com.automatodev.gcadmin.firebase.FirebaseHelper;
import com.automatodev.gcadmin.provider.DishProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DishActivity extends AppCompatActivity {

    public static boolean status;
    private RecyclerView recycler_dish;
    private DishAdapter dishAdapter;
    private FirebaseHelper firebaseOper;
    private List<DishProvider> dishProviderList;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);

        recycler_dish = findViewById(R.id.recycler_dish);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseOper = new FirebaseHelper(this);

        showDataDish();

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

    public void addIten(View view) {
        if (!EditActivity.status) {
            Intent intent = new Intent(DishActivity.this, EditActivity.class);
            startActivity(intent);
        }
    }

    public void showDataDish() {
        recycler_dish.hasFixedSize();
        recycler_dish.setLayoutManager(new LinearLayoutManager(this));
        dishProviderList = new ArrayList<>();
        dishAdapter = new DishAdapter(dishProviderList, this);
        recycler_dish.setAdapter(dishAdapter);

    }

    public void fireDishGet() {
        firebaseFirestore.collection("userAdmin").document("cardapio")
                .collection("pratos")
                .addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        dishProviderList.clear();
                        List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot doc : docs) {
                            dishProviderList.add(doc.toObject(DishProvider.class));
                        }
                        dishAdapter.notifyDataSetChanged();
                    }

                });

    }

    @Override
    protected void onResume() {
        super.onResume();
        fireDishGet();
    }
}
