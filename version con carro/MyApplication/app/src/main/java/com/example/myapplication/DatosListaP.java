package com.example.myapplication;

public class DatosListaP
{
    private String nombre,descripcion;
    private double precio;
    private byte[] imagenp;

    public DatosListaP(String nombre, String descripcion, double precio, byte[] imagenp) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagenp = imagenp;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public byte[] getImagenp() {
        return imagenp;
    }

    public void setImagenp(byte[] imagenp) {
        this.imagenp = imagenp;
    }
}
