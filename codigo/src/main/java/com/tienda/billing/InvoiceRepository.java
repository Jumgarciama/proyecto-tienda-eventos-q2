package com.tienda.billing;

public class InvoiceRepository {
    private long nextNumber = 1;

    public Invoice emitir(long pedidoId, String clienteId, long total) {
        String numero = String.format("F-%04d", nextNumber++);
        return new Invoice(numero, pedidoId, clienteId, total);
    }
}
