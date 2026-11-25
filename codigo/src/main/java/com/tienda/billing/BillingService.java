package com.tienda.billing;

import com.tienda.event.PedidoConfirmado;

public class BillingService {
    private final InvoiceRepository repository;

    public BillingService(InvoiceRepository repository) {
        this.repository = repository;
    }

    public void onPedidoConfirmado(PedidoConfirmado evento) {
        if ("C-999".equals(evento.getClienteId())) {
            System.out.println("FACTURA FALLO pedido=" + evento.getPedidoId() + " cliente=C-999");
            return;
        }
        Invoice invoice = repository.emitir(evento.getPedidoId(), evento.getClienteId(), evento.getTotal());
        System.out.println("FACTURA " + invoice.getNumero()
                + " pedido=" + evento.getPedidoId()
                + " cliente=" + evento.getClienteId()
                + " total=" + evento.getTotal());
    }
}
