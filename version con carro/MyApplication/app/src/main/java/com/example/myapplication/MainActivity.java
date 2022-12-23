package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
EditText user, pass;
Button btnEntrar,btnRegistrar;
daoUsuario dao;
public static int usuId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        user=findViewById(R.id.usuario);
        pass=findViewById(R.id.contrasena);

        btnEntrar=findViewById(R.id.ingresar);
        btnRegistrar=findViewById(R.id.registrar);


        btnEntrar.setOnClickListener(this);
        btnRegistrar.setOnClickListener(this);
        dao=new daoUsuario(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ingresar:

                String u=user.getText().toString();
                String p=pass.getText().toString();
                if (u.equals("")&&p.equals("")){
                    Toast.makeText(this, "ERROR campos vacíos", Toast.LENGTH_SHORT).show();
                    
                }
                else if (dao.login(u,p)==1){
                    Usuario ux=dao.getUsuario(u,p);
                    Toast.makeText(this, "Datos de usuario correctos", Toast.LENGTH_SHORT).show();
                    Intent i2=new Intent(MainActivity.this, VistaProdCliente.class);
                    //i2.putExtra("Id",ux.getId());
                    usuId = ux.getId();
                    startActivity(i2);
                    finish();


                }
                else if(dao.login(u,p)==2){

                    Usuario ux=dao.getUsuario(u,p);
                    Toast.makeText(this, "Datos de admin correcots", Toast.LENGTH_SHORT).show();
                    Intent i2=new Intent(MainActivity.this, AdminActivity.class);
                    i2.putExtra("Id",ux.getId());
                    startActivity(i2);
                    finish();
                }



                else {
                    Toast.makeText(this, "Usuario o contraseña incorrecto", Toast.LENGTH_SHORT).show();

                }

                break;

            case R.id.registrar:

                Intent i=new Intent(MainActivity.this, Registrar.class);
                startActivity(i);
                finish();
                break;


        }
    }
}