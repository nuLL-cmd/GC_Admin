package com.automatodev.gcadmin.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.automatodev.gcadmin.R;
import com.automatodev.gcadmin.firebase.FirebaseHelper;
import com.automatodev.gcadmin.provider.DishProvider;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class EditActivity extends AppCompatActivity {

    public static boolean status;
    private LinearLayout linearLayout;
    private EditText edt_dish_edit;
    private EditText edt_dishOne_edit;
    private EditText edt_dishTwo_edit;
    private EditText edt_dishTree_edit;
    private EditText edt_dishFour_edit;
    private EditText edt_dishFive_edit;
    private EditText edt_dishPrice_edit;
    private FirebaseHelper firebaeOper;
    private ImageView img_dish_preview;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        edt_dish_edit = findViewById(R.id.edt_dish_edit);
        edt_dishOne_edit = findViewById(R.id.edt_dishOne_edit);
        edt_dishTwo_edit = findViewById(R.id.edt_dishTwo_edit);
        edt_dishTree_edit = findViewById(R.id.edt_dishTree_edit);
        edt_dishFour_edit = findViewById(R.id.edt_dishFour_edit);
        edt_dishFive_edit = findViewById(R.id.edt_dishFive_edit);
        edt_dishPrice_edit = findViewById(R.id.edt_dishPrice_edit);
        img_dish_preview =findViewById(R.id.img_dish_preview);

        firebaeOper = new FirebaseHelper(this);

    }

    public void validateEdits(){
        String[] field = new String[7];
        DishProvider dishProvider;
        field[0] = edt_dish_edit.getText().toString();
        field[1] = edt_dishOne_edit.getText().toString();
        field[2] = edt_dishTwo_edit.getText().toString();
        field[3] = edt_dishTree_edit.getText().toString();
        field[4] = edt_dishFour_edit.getText().toString();
        field[5] = edt_dishFive_edit.getText().toString();
        field[6] = edt_dishPrice_edit.getText().toString();
        int count = 0;

        if (field[6].trim().isEmpty() || uri == null){
            Toast.makeText(this, "O produto precisa de um preço e uma imagem!!", Toast.LENGTH_SHORT).show();
            return;
        }
        for (String f: field){
            if (f.trim().isEmpty()){
                count++;
            }
        }
        if (count != 0){
            dishProvider = new DishProvider(UUID.randomUUID().toString(),field[0],field[1],field[2],
                    field[3],field[4],field[5],
                    Double.parseDouble(field[6]),null);
            alertValidate("Ha campos a serem preenchidos!\nDeseja salvar os dados?",dishProvider);
            count = 0;
        }else{
            dishProvider = new DishProvider(UUID.randomUUID().toString(),field[0],field[1],field[2],
                    field[3],field[4],field[5],
                    Double.parseDouble(field[6]),null);
            alertValidate("Deseja salvar os dados na tabela?",dishProvider);
            count = 0;
        }
    }
    public void alertValidate(String message, final DishProvider dishProvider){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Confirmação...");
        alert.setMessage(message);
        alert.setPositiveButton("Sim",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int witch){
                firebaeOper.fireSaveDish(dishProvider,uri);
            }
        }).setNegativeButton("Cancelar",null).show();

    }
    public void saveDish(View view){validateEdits();}

    public void getPick(View view){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == Activity.RESULT_OK){
            assert data != null;
            uri = data.getData();
            Picasso.get().load(uri).into(img_dish_preview);

        }
    }
}
