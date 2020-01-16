package com.automatodev.gcadmin.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.automatodev.gcadmin.R;
import com.automatodev.gcadmin.firebase.FirebaseHelper;

public class LoginActivity extends AppCompatActivity {
    private EditText edt_email_login;
    private EditText edt_password_login;
    private FirebaseHelper firebaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edt_email_login = findViewById(R.id.edt_email_login);
        edt_password_login = findViewById(R.id.edt_password_login);
        firebaseHelper = new FirebaseHelper(this);
    }

    public void loginMethodClick(View view) {
        String userEmail, userPassword;
        userEmail = edt_email_login.getText().toString();
        userPassword = edt_password_login.getText().toString();
        if (userEmail.trim().isEmpty() || userPassword.trim().isEmpty())
            Toast.makeText(this, "Campos nao podem ser vazios!", Toast.LENGTH_SHORT).show();
        else {
            firebaseHelper.fireLogin(userEmail, userPassword);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseHelper.nextActivityMain();

    }
}
