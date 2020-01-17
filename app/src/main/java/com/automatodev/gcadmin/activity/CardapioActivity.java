package com.automatodev.gcadmin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.automatodev.gcadmin.R;

public class CardapioActivity extends AppCompatActivity {

    public static boolean status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardaptio);
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
    public void addIten(View view){
        if (!EditActivity.status){
            Intent intent = new Intent(CardapioActivity.this, EditActivity.class);
            startActivity(intent);
        }
    }

}
