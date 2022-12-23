package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class CarritoProductos extends AppCompatActivity {

    public ListView listview;
    TextView subTotTxt;
    Button btnVolver;
    daoUsuProd daoU;
    double subTot;


    ArrayList<UsuProd> listadatos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito_productos);
        btnVolver = findViewById(R.id.btnAgregarComida);
        subTotTxt = findViewById(R.id.textView9);
       // getSupportActionBar().hide();
        listview = findViewById(R.id.pedidoList);
        daoU = new daoUsuProd(this);

        populateView();
        CarritoProductos.CustomAdapter adapter = new CarritoProductos.CustomAdapter(this, R.layout.activity_prod_carrito, listadatos);
        listview.setAdapter(adapter);

        subTotTxt.setText("$ "+ subTot);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2=new Intent(CarritoProductos.this, VistaProdCliente.class);
                startActivity(i2);
            }
        });
    }

    public void populateView() {
        Cursor dataUsu = daoU.getDatos();

        while (dataUsu.moveToNext()) {
            String nombrep = dataUsu.getString(0);
            int unidades = dataUsu.getInt(1);
            double precio = dataUsu.getDouble(2);
            byte[] imagen = dataUsu.getBlob(3);
            subTot += unidades * precio;
            listadatos.add(new UsuProd(nombrep, precio, imagen, unidades));
        }
    }

    public class CustomAdapter extends BaseAdapter {
        private Context context;
        private int layout;
        ArrayList<UsuProd> textList;

        public CustomAdapter(Context context, int layout, ArrayList<UsuProd> textList) {
            this.context = context;
            this.layout = layout;
            this.textList = textList;
        }

        @Override
        public int getCount() {
            return textList.size();
        }

        @Override
        public Object getItem(int position) {
            return textList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            View row = view;
            ViewHolder holder;
            if(row==null)
            {
                LayoutInflater inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                row=inflater.inflate(layout,null);
                holder=new ViewHolder();
                holder.setTextnombre(row.findViewById(R.id.prodNombre2));
                holder.setTextdescrip(row.findViewById(R.id.prodUnidades));
                holder.setTextprecio(row.findViewById(R.id.prodPrecio2));
                holder.setImageView(row.findViewById(R.id.prodImg2));
                row.setTag(holder);

            }
            else
            {
                holder= (ViewHolder) row.getTag();
            }
            final UsuProd comida=textList.get(position);
            holder.textnombre.setText(comida.getNombre());
            holder.textdescrip.setText(String.valueOf(comida.getUnit()));
            holder.textprecio.setText(String.valueOf(comida.getPrecio()));
            byte[] imagenproducto=comida.getImagen();
            Bitmap bitmap= BitmapFactory.decodeByteArray(imagenproducto,0,imagenproducto.length);
            holder.imageView.setImageBitmap(bitmap);
            return row;
        }


    }

}