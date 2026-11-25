package com.tienda.event;

public class PedidoConfirmado {
    private final long pedidoId;
    private final String clienteId;
    private final long total;

    public PedidoConfirmado(long pedidoId, String clienteId, long total) {
        this.pedidoId = pedidoId; this.clienteId = clienteId; this.total = total;
    }
    public long getPedidoId() { return pedidoId; }
    public String getClienteId() { return clienteId; }
    public long getTotal() { return total; }
}
