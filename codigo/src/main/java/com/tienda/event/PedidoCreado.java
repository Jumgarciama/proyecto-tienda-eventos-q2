package com.tienda.event;

public class PedidoCreado {
    private final long pedidoId;
    private final String clienteId;
    private final long productoId;
    private final int cantidad;
    private final long total;

    public PedidoCreado(long pedidoId, String clienteId, long productoId, int cantidad, long total) {
        this.pedidoId = pedidoId; this.clienteId = clienteId;
        this.productoId = productoId; this.cantidad = cantidad; this.total = total;
    }
    public long getPedidoId() { return pedidoId; }
    public String getClienteId() { return clienteId; }
    public long getProductoId() { return productoId; }
    public int getCantidad() { return cantidad; }
    public long getTotal() { return total; }
}
