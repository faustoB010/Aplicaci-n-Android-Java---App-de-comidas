package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Cliente_Ver_Agregar extends AppCompatActivity {
    private TextView enombre,edescrip,eprecio, cantidadTxt;
    ImageView eimagen;
    Button btnSum, btnRes, btnVistaProd;
    int seleccionarId, cantidad, idUsu = MainActivity.usuId;
    String seleccionarNombre,seleccionarDescrip;
    double seleccionarPrecio;
    daoUsuProd daoUP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_ver_agregar);
        getSupportActionBar().hide();
        enombre=findViewById(R.id.editNombre);
        edescrip=findViewById(R.id.editDescrip);
        eprecio=findViewById(R.id.editPrecio);
        eimagen=findViewById(R.id.editImagen);
        cantidadTxt = findViewById(R.id.textView11);

        btnSum = findViewById(R.id.button5);
        btnRes = findViewById(R.id.button4);
        btnVistaProd = findViewById(R.id.button3);

        daoUP= new daoUsuProd(this);
        //Cursor dataUP = daoUP.getDatos();

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



        btnSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cantidad++;
                desplegarCantidad();
            }
        });

        btnRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cantidad == 1)
                    Toast.makeText(Cliente_Ver_Agregar.this, "No se puede escoger menos de 1 unidad", Toast.LENGTH_SHORT).show();
                else{
                    cantidad--;
                    desplegarCantidad();
                }

            }
        });

        btnVistaProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2=new Intent(Cliente_Ver_Agregar.this, VistaProdCliente.class);
                startActivity(i2);

                Toast.makeText(Cliente_Ver_Agregar.this,"idp:"+ seleccionarId +" id: "+ MainActivity.usuId+ "unidades: "+ Integer.parseInt(cantidadTxt.getText().toString()), Toast.LENGTH_LONG).show();
                daoUP.insertarProdUsu(seleccionarId, idUsu, Integer.parseInt(cantidadTxt.getText().toString()));


            }
        });



    }



    private void desplegarCantidad(){
        cantidadTxt.setText(String.valueOf(cantidad));
    }

}