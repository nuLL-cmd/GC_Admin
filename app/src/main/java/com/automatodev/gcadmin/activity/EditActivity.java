package com.automatodev.gcadmin.activity;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.automatodev.gcadmin.R;

public class EditActivity extends AppCompatActivity {

    public static boolean status;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

    }
}
