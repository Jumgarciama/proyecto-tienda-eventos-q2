package com.tienda.event;

public class PedidoRechazado {
    private final long pedidoId;
    public PedidoRechazado(long pedidoId) { this.pedidoId = pedidoId; }
    public long getPedidoId() { return pedidoId; }
}
