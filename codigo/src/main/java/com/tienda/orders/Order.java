package com.tienda.orders;

public class Order {
    private final long id;
    private final String clienteId;
    private final long productoId;
    private final int cantidad;
    private String estado;
    private long total;

    public Order(long id, String clienteId, long productoId, int cantidad, String estado, long total) {
        this.id = id; this.clienteId = clienteId; this.productoId = productoId;
        this.cantidad = cantidad; this.estado = estado; this.total = total;
    }
    public long getId() { return id; }
    public String getClienteId() { return clienteId; }
    public long getProductoId() { return productoId; }
    public int getCantidad() { return cantidad; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public long getTotal() { return total; }
    public void setTotal(long total) { this.total = total; }
}
