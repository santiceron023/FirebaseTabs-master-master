package com.example.danni.firebasetabs;

/**
 * Created by santiago ceron on 29/11/2017.
 */

public class Pedidos_Agrupados {
    String Cantidad;
    String Total;
    String NombreTienda;
    String NumeroPedido;
    String TiendaId;

    public Pedidos_Agrupados() {
    }

    public Pedidos_Agrupados(String cantidad, String total, String nombreTienda, String numeroPedido, String tiendaId) {
        Cantidad = cantidad;
        Total = total;
        NombreTienda = nombreTienda;
        NumeroPedido = numeroPedido;
        TiendaId = tiendaId;
    }

    public String getCantidad() {
        return Cantidad;
    }

    public void setCantidad(String cantidad) {
        Cantidad = cantidad;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getNombreTienda() {
        return NombreTienda;
    }

    public void setNombreTienda(String nombreTienda) {
        NombreTienda = nombreTienda;
    }

    public String getNumeroPedido() {
        return NumeroPedido;
    }

    public void setNumeroPedido(String numeroPedido) {
        NumeroPedido = numeroPedido;
    }

    public String getTiendaId() {
        return TiendaId;
    }

    public void setTiendaId(String tiendaId) {
        TiendaId = tiendaId;
    }
}
