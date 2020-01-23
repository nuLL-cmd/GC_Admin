package com.automatodev.gcadmin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.automatodev.gcadmin.R;
import com.automatodev.gcadmin.firebase.FirebaseHelper;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseHelper firebaseHelper;

    public static boolean status;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseHelper = new FirebaseHelper(this);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finishAffinity();

    }

    @Override
    protected void onStart() {
        super.onStart();
        status = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseHelper.fireGetUser(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    @Override
    protected void onStop() {
        super.onStop();
        status = false;
    }
    public void logoutMethod(View view){
      firebaseHelper.fireLogout();
    }

    public void nextActivityUsers(View view){
        if (!UsersActivity.status){
            Intent intent = new Intent(this, UsersActivity.class);
            startActivity(intent);
        }
    }
    public void nextActivityCardapio(View view){
        if (!DishActivity.status){
            Intent intent = new Intent(this, DishActivity.class);
            startActivity(intent);
        }
    }

    public void nextActivityFidelity(View view0){
        if (!FidelityActivity.status){
            Intent intent = new Intent(this, FidelityActivity.class);
            startActivity(intent);
        }
    }
    public void nextActivityOrders(View view){
        if (!OrdersActivity.status){
            Intent intent = new Intent(this, OrdersActivity.class);
            startActivity(intent);
        }
    }

}

