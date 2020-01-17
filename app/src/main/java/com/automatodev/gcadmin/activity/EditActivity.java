package com.automatodev.gcadmin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widigets.EditText;
import com.automatodev.gcadmin.R;

public class EditActivity extends AppCompatActivity {
    private EditText edt_nameDish;
    private EditText edt_descOne_edit;
    private EditText edt_descTwo_edit;
    private EditText edt_descTree_edit;
    private EditText edt_descFour_edit;
    private EditText edt_descFive_edit;
    private EditText edt_price_edit;
    private CardapioProvider cardapioProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        
        edt_nameDish_edit = findViewById(R.id.edt_nome_edit);
        edt_descOne_edit = findViewById(R.id.edt_descOne_edit);
        edt_descTwo_edit = findVIewById(R.id.edt_descTwo_edit);
        edt_descTree_edit = findViewById(R.id.edt_descTree_edit);
        edt_descFour_edit = findViewById(R.id.edt_descFour_edit);
        edt_descFive_edt = fidViewById(R.id.edt_descFive_edit);
        edt_price_edit = findViewById(R.id.edt_price_edit);


    
    }

    public void validateEdits(){
        String[] fields = new String[7];
        int count = 0
        fields[0] = edt_nameDish.getText().toString();
        fields[1] = edt_descOne_edit.getText().toString(();
        fields[2] = edt_descTwo_edit.getText().toString();
        fields[3] = edt_descTree_edit.getText().toString();
        fields[4] = edt_descFour_edit.getText().toString();
        fields[5] = edt_descFive_edit.getText().toString();
        fields[6] = edt_price_edit.getText().toString();
        
        
        if(fields[6].tirm().isEmpty()){
            Toast.makeText(EditActivity.this, "Campo preço é obrigatório para o prato!",Tost.LENG_SHORT)
            .show();
            return;
            
        }
        for(String f: fields){
            if(f.trim().isEmpty()){
                count++;
            }
        }
        
        if(count != 0){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Campos vazios");
            alert.setMesssate("Ha campos a serem preenchidos, deseja continuar?");
            alert.setPositiveButton("Sim",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int witch){
                    cardapioProvider = new CardapioProvider(fields[0],
                    fields[1],fields[2], fields[3],fields[4],fields[5],fields[6],null);
                    //TODO IMPLEMENTR METODO PARA GRAVAÇÃO DOS DADOS NO BANCO
                }

            }).setNegativeButton("Não", null);
            alert.show();
        }else{
            ALertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Confirmação");
            alert.setMessage("Salvar os dados na tabela?");
            alert.setPositiveButton("Sim", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int witch){
                    cardapioProvider = new CardapioProvider(fields[0],
                    fields[1],fields[2], fields[3],fields[4],fields[5],fields[6],null);
                    //TODO IMPLEMENTAR METODO PARA GRAVAÇÃO DOS DADOS NO BANCO
                }
            })
        }
    }
}
