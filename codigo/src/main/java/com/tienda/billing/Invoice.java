package com.tienda.billing;

public class Invoice {
    private final String numero;
    private final long pedidoId;
    private final String clienteId;
    private final long total;

    public Invoice(String numero, long pedidoId, String clienteId, long total) {
        this.numero = numero;
        this.pedidoId = pedidoId;
        this.clienteId = clienteId;
        this.total = total;
    }
    public String getNumero() { return numero; }
    public long getPedidoId() { return pedidoId; }
    public String getClienteId() { return clienteId; }
    public long getTotal() { return total; }
}
