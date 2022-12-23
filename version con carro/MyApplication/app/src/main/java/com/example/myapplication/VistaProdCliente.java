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

public class VistaProdCliente extends AppCompatActivity {

    public ListView listview;
    Button btnCarrito;
    daoUsuario dao;

    ArrayList<DatosListaP> listadatos=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        getSupportActionBar().hide();
        listview=findViewById(R.id.prodList);
        dao=new daoUsuario(this);
        populateView();
        CustomAdapter adapter = new CustomAdapter(this,R.layout.vista_prod_cliente,listadatos);
        listview.setAdapter(adapter);

        btnCarrito = findViewById(R.id.btnVerCarrito);

        btnCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2=new Intent(VistaProdCliente.this, CarritoProductos.class);
                startActivity(i2);
            }
        });
    }



    public void populateView()
    {
        Cursor data=dao.getDatos();
        while(data.moveToNext())
        {
            String nombrep=data.getString(1);
            String descripcion=data.getString(2);
            double precio =data.getDouble(3);
            byte[] imagen=data.getBlob(4);
            listadatos.add(new DatosListaP(nombrep,descripcion,precio,imagen));
        }
    }
    public class CustomAdapter extends BaseAdapter {
        private Context context;
        private int layout;
        ArrayList<DatosListaP> textList;

        public CustomAdapter(Context context, int layout, ArrayList<DatosListaP> textList) {
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
        private class ViewHolder
        {
            ImageView imageView;
            TextView textnombre;
            TextView textprecio;
            TextView textdescrip;
        }
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public View getView(int position, View view, ViewGroup parent) {

            View row = view;
            ViewHolder holder;
            if(row==null)
            {
                LayoutInflater inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                row=inflater.inflate(layout,null);
                holder=new ViewHolder();
                holder.textnombre=row.findViewById(R.id.prodNombre);
                holder.textdescrip=row.findViewById(R.id.prodDescripcion);
                holder.textprecio=row.findViewById(R.id.prodPrecio);
                holder.imageView=row.findViewById(R.id.prodImg);
                row.setTag(holder);

            }
            else
            {
                holder= (ViewHolder) row.getTag();
            }
            final DatosListaP comida=textList.get(position);
            holder.textnombre.setText(comida.getNombre());
            holder.textdescrip.setText(comida.getDescripcion());
            holder.textprecio.setText(String.valueOf(comida.getPrecio()));
            byte[] imagenproducto=comida.getImagenp();
            Bitmap bitmap= BitmapFactory.decodeByteArray(imagenproducto,0,imagenproducto.length);
            holder.imageView.setImageBitmap(bitmap);
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cursor data=dao.getItemId(comida.getNombre());

                    int itemid=-1;
                    String  productoNombre="";
                    String productoDescripcion="";
                    double productoPrecio=0;
                    byte[] productoImagen=null;
                    while (data.moveToNext()) {
                        itemid = data.getInt(0);
                        productoNombre = data.getString(1);
                        productoDescripcion = data.getString(2);
                        productoPrecio = data.getDouble(3);
                        productoImagen = data.getBlob(4);
                        Intent editarIntent = new Intent(VistaProdCliente.this, Cliente_Ver_Agregar.class);
                        editarIntent.putExtra("id", itemid);
                        editarIntent.putExtra("nom_producto", comida.getNombre());
                        editarIntent.putExtra("descrip_producto", comida.getDescripcion());
                        editarIntent.putExtra("precio", comida.getPrecio());
                        ByteArrayOutputStream bs = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bs);
                        editarIntent.putExtra("byteArray", bs.toByteArray());
                        startActivity(editarIntent);
                    }
                }
            });
            return row;
        }

    }

}