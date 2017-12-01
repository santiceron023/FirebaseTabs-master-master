package com.example.danni.firebasetabs;

/**
 * Created by santiago ceron on 30/10/2017.
 */

public class Productos{
    private String Nombre;
    private String Tamaño;
    private String Marca;
    private String Precio;

    public Productos(String nombre, String tamaño, String marca, String precio) {
        this.Nombre = nombre;
        this.Tamaño = tamaño;
        this.Marca = marca;
        this.Precio = precio;
    }

    public Productos() {
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getTamaño() {
        return Tamaño;
    }

    public void setTamaño(String tamaño) {
        Tamaño = tamaño;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String marca) {
        Marca = marca;
    }

    public String getPrecio() {
        return Precio;
    }

    public void setPrecio(String precio) {
        Precio = precio;
    }
}
