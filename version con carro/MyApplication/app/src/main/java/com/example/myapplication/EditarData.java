package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EditarData extends AppCompatActivity implements View.OnClickListener {
     EditText enombre,edescrip,eprecio;
    Button actualizar,eliminar;
    ImageView eimagen;
    int seleccionarId;
    String seleccionarNombre,seleccionarDescrip;
    double seleccionarPrecio;
    daoUsuario a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_data);
        getSupportActionBar().hide();
        enombre=findViewById(R.id.editNombre);
        edescrip=findViewById(R.id.editDescrip);
        eprecio=findViewById(R.id.editPrecio);
        eimagen=findViewById(R.id.editImagen);

        actualizar=findViewById(R.id.btactualizar);
        eliminar=findViewById(R.id.bteliminar);
        a=new daoUsuario(this);
        actualizar.setOnClickListener(this);
        eliminar.setOnClickListener(this);


        Intent recibirVista =getIntent();
        seleccionarId=getIntent().getIntExtra("id",-1);
        seleccionarNombre=getIntent().getStringExtra("nom_producto");
        seleccionarDescrip=getIntent().getStringExtra("descrip_producto");
        seleccionarPrecio=getIntent().getDoubleExtra("precio",0);
        if(getIntent().hasExtra("byteArray"))
        {
            Bitmap bitmap= BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("byteArray"),0,getIntent().getByteArrayExtra("byteArray").length);
            eimagen.setImageBitmap(bitmap);

        }
        enombre.setText(seleccionarNombre);
        edescrip.setText(seleccionarDescrip);
        eprecio.setText(String.valueOf(seleccionarPrecio));

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btactualizar:

                    boolean isupdate=a.updatedata(enombre.getText().toString(),edescrip.getText().toString(),Double.parseDouble(eprecio.getText().toString()));

                 if(isupdate==true)
                 {
                     Toast.makeText(this, "Actualización exitosa", Toast.LENGTH_SHORT).show();
                 }
                 else{
                     Toast.makeText(this, "Actualización NO exitosa", Toast.LENGTH_SHORT).show();
                 }
                 break;
            case R.id.bteliminar:
                Integer borrarfila=a.borrardata(enombre.getText().toString());
                if(borrarfila>0)
                {
                    Toast.makeText(this, "se eliminó", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "No eliminado ", Toast.LENGTH_SHORT).show();
                }
        }
    }
}