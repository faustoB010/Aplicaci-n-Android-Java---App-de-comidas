package com.example.myapplication;

import android.content.ContentValues;

import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

public class daoUsuario {
    Context c;
    Usuario usuario;
    Producto producto;
    ArrayList<Usuario> listaUsuarios;
    ArrayList<Producto> listaProductos;
    SQLiteDatabase sqlDatabase;
    String bd = "BDUsuarios";
    String tabla = "create table if not exists usuario(id integer primary key autoincrement,usuario text,pass text" +
            ",nombre text, ap text);";
    String tProducto = "create table if not exists Producto(idp integer primary key autoincrement,nom_producto text,descrip_producto text" +
            ",precio real,imagen blob);";
    String creacionAdmin = "INSERT INTO usuario(usuario,pass,nombre,ap)" +
            "SELECT 'admin', '1234','',''" +
            "WHERE NOT EXISTS(SELECT 1 FROM usuario WHERE usuario = 'admin' AND pass = '1234');";


    public daoUsuario(Context c) {
        this.c = c;
        sqlDatabase = c.openOrCreateDatabase(bd, Context.MODE_PRIVATE, null);
        sqlDatabase.execSQL(tabla);
        sqlDatabase.execSQL(creacionAdmin);
        sqlDatabase.execSQL(tProducto);
        usuario = new Usuario();
        producto = new Producto();


    }

    public boolean insertarUsuario(Usuario usuario) {

        if (buscar(usuario.getUsuario()) == 0) {
            ContentValues cv = new ContentValues();
            cv.put("usuario", usuario.getUsuario());
            cv.put("pass", usuario.getContrasena());
            cv.put("nombre", usuario.getNombre());
            cv.put("ap ", usuario.getApellido());

            return (sqlDatabase.insert("usuario", null, cv)) > 0;

        }
        return false;

    }

    public int buscar(String u) {
        int x = 0;
        listaUsuarios = selectUsuario();
        for (Usuario us : listaUsuarios
        ) {
            if (us.getUsuario().equals(u))
                x++;

        }
        return x;
    }

    public ArrayList<Usuario> selectUsuario() {
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();
        listaUsuarios.clear();
        Cursor cr = sqlDatabase.rawQuery("select * from usuario", null);
        if (cr != null && cr.moveToFirst()) {
            do {
                Usuario u = new Usuario();
                u.setId(cr.getInt(0));
                u.setUsuario(cr.getString(1));
                u.setContrasena(cr.getString(2));
                u.setNombre(cr.getString(3));
                u.setApellido(cr.getString(4));
                listaUsuarios.add(u);


            } while (cr.moveToNext());

        }

        return listaUsuarios;
    }

    public int login(String u, String p) {
        int a = 0;

        Cursor cr = sqlDatabase.rawQuery("select * from usuario", null);
        if (cr != null && cr.moveToFirst()) {
            do {


                if (cr.getString(1).equals(u) && cr.getString(2).equals(p)) {
                    if (cr.getString(1).equals("admin") && cr.getString(2).equals("1234"))
                        return 2;
                    a = 1;

                }

            } while (cr.moveToNext());

        }
        return a;

    }

    public Usuario getUsuario(String u, String p) {
        listaUsuarios = selectUsuario();
        for (Usuario us : listaUsuarios) {
            if (us.getUsuario().equals(u) && us.getContrasena().equals(p)) {
                return us;
            }
        }
        return null;
    }

    //--------------------------------------------------------------------------------------------------------------------------------------
    public boolean insertarProducto(Producto producto) {

        if (buscarP(producto.getNombre()) == 0) {
            ContentValues cv = new ContentValues();
            cv.put("nom_producto", producto.getNombre());
            cv.put("descrip_producto", producto.getDescripcion());
            cv.put("precio", producto.getPrecio());
            cv.put("imagen ", producto.getImagen());

            return (sqlDatabase.insert("Producto", null, cv)) > 0;
        }
        return false;

    }

    public int buscarP(String u) {
        int x = 0;
        listaProductos = selectProducto();
        for (Producto us : listaProductos
        ) {
            if (us.getNombre().equals(u))
                x++;

        }
        return x;
    }

    public ArrayList<Producto> selectProducto() {
        ArrayList<Producto> listaProductos = new ArrayList<>();
        listaProductos.clear();
        Cursor cr = sqlDatabase.rawQuery("select * from Producto", null);
        if (cr != null && cr.moveToFirst()) {
            do {
                Producto p = new Producto();
                p.setIdp(cr.getInt(0));
                p.setNombre(cr.getString(1));
                p.setDescripcion(cr.getString(2));
                p.setPrecio(cr.getDouble(3));
                p.setImagen(cr.getBlob(4));
                listaProductos.add(p);


            } while (cr.moveToNext());

        }

        return listaProductos;
    }

    public Cursor getDatos() {
        String query = "Select * from Producto";
        Cursor data = sqlDatabase.rawQuery(query, null);
        return data;
    }

    public Cursor getItemId(String nom) {

        Cursor dataItem = sqlDatabase.rawQuery("Select * from Producto " + "  Where nom_producto" + "= '" + nom + "'", null);
        return dataItem;


    }
    public Boolean updatedata(String nombre,String descripcion,double precio)
    {

            ContentValues cv = new ContentValues();
            cv.put("nom_producto",nombre );
            cv.put("descrip_producto",descripcion);
            cv.put("precio", precio);

        sqlDatabase.update("Producto",cv,"nom_producto= ?",new String[]{nombre});
            return true;

    }

    public Integer borrardata(String nombre)
    {
        return sqlDatabase.delete("Producto", "nom_producto = ?",new String[]{nombre});
    }
}







