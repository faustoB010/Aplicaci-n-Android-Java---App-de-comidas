package com.example.myapplication;

public class UsuProd extends Producto{
    int id, idp, unit;

    public UsuProd(int id, int idp) {
        this.id = id;
        this.idp = idp;
    }

    public UsuProd(String nombre, double precio, byte[] imagen, int unit) {
        super(nombre, precio, imagen);
        this.unit = unit;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public UsuProd() {
    }

    public int getId() {
        return id;
    }

    public int getIdp() {
        return idp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdp(int idp) {
        this.idp = idp;
    }
}
