package com.automatodev.gcadmin.activity;

import android.os.Bundle;

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
}
