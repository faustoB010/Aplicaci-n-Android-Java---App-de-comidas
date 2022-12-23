package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class daoUsuProd {
    Context c;
   UsuProd usuProd;
    ArrayList<UsuProd> listaClaves;
    SQLiteDatabase sqlDatabase;
    String bd = "BDUsuarios";
    String tabla = "create table if not exists UsuProd(" +
            "id integer," +
            "idp integer," +
            "unidades integer," +
            "PRIMARY KEY (id, idp)," +
            "FOREIGN KEY (id)" +
            "  REFERENCES usuario(id)" +
            "    ON DELETE CASCADE" +
            "    ON UPDATE NO ACTION," +
            "FOREIGN KEY (idp)" +
            "  REFERENCES Producto(idp) " +
            "    ON DELETE CASCADE" +
            "    ON UPDATE NO ACTION);";


    public daoUsuProd(Context c) {
        this.c = c;
        sqlDatabase = c.openOrCreateDatabase(bd, Context.MODE_PRIVATE, null);
        sqlDatabase.execSQL(tabla);
        usuProd = new UsuProd();
    }

    public boolean insertarProdUsu(int prodId, int usuId, int uni) {

        if (buscarP(usuProd.getId()) == 0  && buscarP(usuProd.getIdp()) == 0 ) {
            ContentValues cv = new ContentValues();
            cv.put("id", usuId);
            cv.put("idp", prodId);
            cv.put("unidades", uni);

            return (sqlDatabase.insert("UsuProd", null, cv)) > 0;
        }
        return false;

    }

    public int buscarP(int u) {
        int x = 0;
        listaClaves = selectProducto();
        for (UsuProd us : listaClaves)
            if (us.getId() == u)
                x++;

        return x;
    }

    public ArrayList<UsuProd> selectProducto() {
        ArrayList<UsuProd> listaClaves = new ArrayList<>();
        listaClaves.clear();
        Cursor cr = sqlDatabase.rawQuery("select * from UsuProd", null);

        if (cr != null && cr.moveToFirst()) {
            do {
                UsuProd p = new UsuProd();
                p.setId(cr.getInt(0));
                p.setIdp(cr.getInt(1));
                p.setUnit(cr.getInt(2));

                listaClaves.add(p);
            } while (cr.moveToNext());
        }

        return listaClaves;
    }

    public Cursor getDatos() {
        String query = "Select distinct nom_producto, unidades, precio, imagen FROM Producto inner join UsuProd;";
        Cursor data = sqlDatabase.rawQuery(query, null);
        return data;
    }

    public Cursor getPedidoPorUsuario(String id) {

        Cursor dataItem = sqlDatabase.rawQuery("Select * from UsuProd " + "  Where id" + "= '" + id + "'", null);
        return dataItem;
    }
}
