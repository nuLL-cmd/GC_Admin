package com.automatodev.gcadmin.activity;

import android.app.AlertDialog;
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
        AlertDialog insertItem = new AlertDialog.Builder(this).create();
        View itemView = getLayoutInflater().inflate(R.layout.layout_add_item,null);
        insertItem.setView(itemView);
        insertItem.show();
    }

}
