package com.example.danni.firebasetabs;

/**
 * Created by danni on 26/10/2017.
 */
public class Tiendas {
    private String Nombre;
    private String Tipo;
    private String PedidoMin;
    private String CostoEnvio;
    private String TiempoEnvio;
    private String Celular;

    public Tiendas() {
    }

    public Tiendas(String nombre, String tipo, String pedidoMin, String costoEnvio, String tiempoEnvio, String celular) {
        Nombre = nombre;
        Tipo = tipo;
        PedidoMin = pedidoMin;
        CostoEnvio = costoEnvio;
        TiempoEnvio = tiempoEnvio;
        Celular = celular;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public String getPedidoMin() {
        return PedidoMin;
    }

    public void setPedidoMin(String pedidoMin) {
        PedidoMin = pedidoMin;
    }

    public String getCostoEnvio() {
        return CostoEnvio;
    }

    public void setCostoEnvio(String costoEnvio) {
        CostoEnvio = costoEnvio;
    }

    public String getTiempoEnvio() {
        return TiempoEnvio;
    }

    public void setTiempoEnvio(String tiempoEnvio) {
        TiempoEnvio = tiempoEnvio;
    }

    public String getCelular() {
        return Celular;
    }

    public void setCelular(String celular) {
        Celular = celular;
    }
}
