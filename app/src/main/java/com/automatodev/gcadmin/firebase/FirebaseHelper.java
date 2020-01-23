package com.automatodev.gcadmin.firebase;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.automatodev.gcadmin.R;
import com.automatodev.gcadmin.activity.MainActivity;
import com.automatodev.gcadmin.provider.DishProvider;
import com.automatodev.gcadmin.provider.UserProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class FirebaseHelper {
    private Activity context;


    private TextView txt_name_main;
    private TextView txt_email_main;
    private TextView txt_user_main;
    private TextView txt_phone_main;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private DocumentReference documentReference;
    private FirebaseStorage firebaseStorage;

    public FirebaseHelper(Activity context) {

        this.context = context;
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
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
                            txt_phone_main.setText(formatPhone(userProvider.getUserPhone()));
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

    public List<DishProvider> fireDishGet() {
        final List<DishProvider> dishProviderList = new ArrayList<>();
        firebaseFirestore.collection("userAdmin").document("cardapio")
                .collection("pratos").get()
                .addOnCompleteListener(context, new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        dishProviderList.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                dishProviderList.add(doc.toObject(DishProvider.class));
                            }
                        }
                        Toast.makeText(context, "teste: " + dishProviderList.get(0).getDishName(), Toast.LENGTH_SHORT).show();
                    }
                });

        return dishProviderList;
    }

    public void fireSaveDish(final DishProvider dishProvider, Uri uri) {
/*        firebaseFirestore.collection("userAdmin").document("cardapio")
                .collection("pratos").document(dishProvider.getDishUid()).set(dishProvider)
                .addOnCompleteListener(context, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.i("logx","SaveDish: "+task.getResult());
                        Toast.makeText(context, "Sucesso!", Toast.LENGTH_SHORT).show();
                        context.finish();

                    }
                }).addOnFailureListener(context, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("logx","SaveDishException: "+e.getMessage(),e);
                Toast.makeText(context, "Algo deu errado, contate o desenvolvedor!", Toast.LENGTH_SHORT).show();

            }
        });*/
        final StorageReference storageReference = firebaseStorage.getReference("/useAdmin/dish/images/" + dishProvider.getDishUid());
        storageReference.putFile(uri).addOnSuccessListener(context, new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(context, new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        dishProvider.setDishUrlPhoto(uri.toString());
                        firebaseFirestore.collection("userAdmin").document("cardapio")
                                .collection("pratos").document(dishProvider.getDishUid()).set(dishProvider)
                                .addOnCompleteListener(context, new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Log.i("logx", "SaveDish: " + task.getResult());
                                        Toast.makeText(context, "Sucesso!", Toast.LENGTH_SHORT).show();
                                        context.finish();

                                    }
                                }).addOnFailureListener(context, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("logx", "SaveDishException: " + e.getMessage(), e);
                                Toast.makeText(context, "Algo deu errado, contate o desenvolvedor!", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                });
            }
        });

    }

    private String formatPhone(String phone) {
        phone = phone.substring(0, 5) + "-" + phone.substring(5, 9);
        return phone;
    }

}

