package com.automatodev.gcadmin.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.automatodev.gcadmin.R;
import com.automatodev.gcadmin.adapter.UsersAdapter;
import com.automatodev.gcadmin.firebase.FirebaseHelper;
import com.automatodev.gcadmin.provider.UsersProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity {
    public static boolean status;
    private List<UsersProvider> usersProviderList;
    private UsersAdapter usersAdapter;
    private FirebaseHelper firebaseHelper;
    private RecyclerView recycler_usrs;
    private FirebaseFirestore firebaseFirestore;
    private TextView txt_userTotal_users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        txt_userTotal_users = findViewById(R.id.txt_userTotal_users);
        recycler_usrs = findViewById(R.id.recycler_users);
        firebaseFirestore = FirebaseFirestore.getInstance();
        getDataUsers();
        fireGetUsers();


    }

    @Override
    protected void onStart() {
        super.onStart();
        status = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        status = false;
    }
    public void getDataUsers(){
        recycler_usrs.hasFixedSize();
        recycler_usrs.setLayoutManager(new LinearLayoutManager(this));
        usersProviderList = new ArrayList<>();
        usersAdapter = new UsersAdapter(usersProviderList);
        recycler_usrs.setAdapter(usersAdapter);
    }

    public void fireGetUsers(){
        firebaseFirestore.collection("users").addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                usersProviderList.clear();
                usersAdapter.notifyDataSetChanged();
                List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot doc: docs){
                    usersProviderList.add(doc.toObject(UsersProvider.class));
                }
                if (usersProviderList.size() != 0)
                    txt_userTotal_users.setText(String.valueOf(usersProviderList.size()));
            }
        });

    }
}
