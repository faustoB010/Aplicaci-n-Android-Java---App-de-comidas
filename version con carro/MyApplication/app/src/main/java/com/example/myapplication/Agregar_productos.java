package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

public class Agregar_productos extends AppCompatActivity implements View.OnClickListener {
    daoUsuario dao;
   Button btguardar,btescoger;
    EditText nombre,precio,descripcion;
    ImageView miImagen;
   String[] permissions={Manifest.permission.READ_EXTERNAL_STORAGE};
    int REQUEST_CODE_GALLERY=1234;
    boolean isPermissionGranted=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_productos);
        Objects.requireNonNull(getSupportActionBar()).hide();
       btguardar=findViewById(R.id.guardarProducto);
        btescoger=findViewById(R.id.escoger1);

        nombre=findViewById(R.id.nombreProducto);
        descripcion=findViewById(R.id.descripcionProducto);
        precio=findViewById(R.id.precio);
        miImagen=findViewById(R.id.imagenEscoger);

        btguardar.setOnClickListener(this);
        btescoger.setOnClickListener(this);
       dao=new daoUsuario(this);



    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.escoger1:
                    validarPermiso();
                break;
            case R.id.guardarProducto:
                    Producto p=new Producto();
                p.setNombre(nombre.getText().toString());
                p.setDescripcion(descripcion.getText().toString());
                p.setPrecio(Double.parseDouble(precio.getText().toString()));
                p.setImagen(imageViewAbyte(miImagen));

                if (!p.esNuloP()){
                    Toast.makeText(this, "ERROR campos vacíos", Toast.LENGTH_SHORT).show();

                }
                else if (dao.insertarProducto(p))
                {
                    Toast.makeText(this, "Producto agregado con éxito", Toast.LENGTH_SHORT).show();
                    Intent i2=new Intent(Agregar_productos.this,AdminActivity.class);
                    startActivity(i2);
                }else {
                    Toast.makeText(this, "El producto ya existe", Toast.LENGTH_SHORT).show();

                }

                break;
        }
    }


    public void validarPermiso()
    {
        if(ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)
        {
            isPermissionGranted =true;
            Intent intent=new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,"Complete Action Using"),REQUEST_CODE_GALLERY);
        }
        else
        {
            ActivityCompat. requestPermissions(Agregar_productos.this,permissions,REQUEST_CODE_GALLERY);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull  String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
        {
            isPermissionGranted=true;
            Toast.makeText(this,"Permiso concedido",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"Permiso denegado",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null)
        {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                miImagen.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(),"ninguna selección",Toast.LENGTH_LONG).show();
        }
    }

    private byte[] imageViewAbyte(ImageView miImagen){
        Bitmap bitmapp=((BitmapDrawable)miImagen.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmapp.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] arreglobyte= stream.toByteArray();
        return arreglobyte;
    }


}