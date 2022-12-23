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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Objects;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {
    public ListView listview;
    daoUsuario dao;
    ArrayList<DatosListaP> listadatos=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Objects.requireNonNull(getSupportActionBar()).hide();
        ImageView iconsuma=findViewById(R.id.iconsuma);
        ImageView iconpedidos=findViewById(R.id.iconpedidos);
       iconpedidos.setOnClickListener(this);
       iconsuma.setOnClickListener(this);

       listview=findViewById(R.id.listap);
        dao=new daoUsuario(this);
        populateView();
        CustumAdapter adapter = new CustumAdapter(this,R.layout.lista_productos,listadatos);
       listview.setAdapter(adapter);
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
    public class CustumAdapter extends BaseAdapter
    {
        final Context context;
        final int layout;
        ArrayList<DatosListaP> textList;

        public CustumAdapter(Context context, int layout, ArrayList<DatosListaP> textList) {
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

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public View getView(int position, View view, ViewGroup parent) {

            View row = view;
           ViewHolder holder;
           if(row==null)
           {
               LayoutInflater inflater= (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
               row=inflater.inflate(layout,null);
               holder=new ViewHolder();
               holder.textnombre=row.findViewById(R.id.textViewnom);
              holder.textdescrip=row.findViewById(R.id.textViewdesc);
               holder.textprecio=row.findViewById(R.id.textViewprecio);
               holder.imageView=row.findViewById(R.id.imageViewP);
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
                        productoDescripcion=  data.getString(2);
                        productoPrecio = data.getDouble(3);
                        productoImagen = data.getBlob(4);
                        Intent editarIntent = new Intent(AdminActivity.this, EditarData.class);
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
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iconpedidos:
                Intent a=new Intent(AdminActivity.this,listaPedidos.class);
                startActivity(a);
                break;
            case R.id.iconsuma:
                Intent b=new Intent(AdminActivity.this,Agregar_productos.class);
                startActivity(b);

                break;
        }
    }
}