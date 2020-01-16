package com.automatodev.gcadmin.firebase;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.automatodev.gcadmin.R;
import com.automatodev.gcadmin.activity.MainActivity;
import com.automatodev.gcadmin.provider.UserProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseHelper {
    private Activity context;


    private TextView txt_name_main;
    private TextView txt_email_main;
    private TextView txt_user_main;
    private TextView txt_phone_main;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private DocumentReference documentReference;

    public FirebaseHelper(Activity context) {

        this.context = context;
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void fireLogin(final String userName, final String userPassword) {
        firebaseAuth.signInWithEmailAndPassword(userName, userPassword)
                .addOnCompleteListener(context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i("logx", "SuccessoLogin: " + task.getResult());
                            nextActivityMain();
                        }
                    }
                }).addOnFailureListener((Activity) context, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("logx", "ExceptionLogin: " + e.getMessage(), e);
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Ops!");
                alert.setMessage("Verifique se o email e senha estão corretos\nDa uma olhadinha na sua internet tambem!");
                alert.setNegativeButton("Ok", null);
                alert.show();
            }
        });
    }

    public void fireLogout() {
        firebaseAuth.signOut();
        context.finish();
    }

    public void nextActivityMain() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null && !MainActivity.status) {
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);

        }
    }

    public void fireGetUser(String uid) {
        txt_email_main = context.findViewById(R.id.txt_email_main);
        txt_name_main = context.findViewById(R.id.txt_name_main);
        txt_user_main = context.findViewById(R.id.txt_user_main);
        txt_phone_main = context.findViewById(R.id.txt_phone_main);

        documentReference = firebaseFirestore.collection("userAdmin")
                .document(uid);
        documentReference.get()
                .addOnCompleteListener(context, new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot doc = task.getResult();
                        assert doc != null;
                        if (doc.exists()) {
                            UserProvider userProvider = doc.toObject(UserProvider.class);
                            assert userProvider != null;
                            txt_phone_main.setText(userProvider.getUserPhone());
                            txt_user_main.setText(userProvider.getUserUser());
                            txt_name_main.setText(userProvider.getUserName());
                            txt_email_main.setText(userProvider.getUserEmail());
                        }
                    }
                }).addOnFailureListener(context, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}

