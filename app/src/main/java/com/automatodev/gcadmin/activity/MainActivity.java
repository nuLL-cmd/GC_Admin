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

        firebaseHelper.fireGetUser(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        status = true;
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finishAffinity();

    }

    public void nextActivityCardapio(View view){
        if (!DishActivity.status){
            Intent intent = new Intent(this, DishActivity.class);
            startActivity(intent);
        }
    }

}

