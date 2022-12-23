package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registrar extends AppCompatActivity implements View.OnClickListener{
EditText us,pas,nom,ap;
Button reg,can;
daoUsuario dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        getSupportActionBar().hide();
        us=(EditText)findViewById(R.id.usuarioRegistrar);
        pas=(EditText)findViewById(R.id.contrasenaRegistro);
        nom=(EditText)findViewById(R.id.nombreRegistrar);
        ap=(EditText)findViewById(R.id.apellidoRegistrar);

        reg=(Button)findViewById(R.id.buttonRegistrar);
        can=(Button)findViewById(R.id.buttonCancelar);


        reg.setOnClickListener(this);
        can.setOnClickListener(this);
        dao=new daoUsuario(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonRegistrar:
                Usuario u=new Usuario();
                u.setUsuario(us.getText().toString());
                u.setContrasena(pas.getText().toString());
                u.setApellido(ap.getText().toString());
                u.setNombre(nom.getText().toString());
                if (!u.esNulo()){
                    Toast.makeText(this, "ERROR campos vacíos", Toast.LENGTH_SHORT).show();


                }
                else if (dao.insertarUsuario(u))
                {
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    Intent i2=new Intent(Registrar.this,MainActivity.class);
                    startActivity(i2);
                    finish();


                }else {
                    Toast.makeText(this, "Usuario ya existe", Toast.LENGTH_SHORT).show();

                }
                break;

            case R.id.buttonCancelar:
                Intent i=new Intent(Registrar.this,MainActivity.class);
                startActivity(i);
                finish();


                break;




        }
    }
}