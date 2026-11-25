package com.tienda.inventory;

import com.tienda.bus.EventBus;
import com.tienda.bus.ProductCatalog;
import com.tienda.event.PedidoConfirmado;
import com.tienda.event.PedidoCreado;
import com.tienda.event.PedidoRechazado;
import com.tienda.event.Product;

public class InventoryService implements ProductCatalog {
    private final EventBus bus;
    private final ProductRepository repository;

    public InventoryService(EventBus bus, ProductRepository repository) {
        this.bus = bus; this.repository = repository;
        bus.subscribe(PedidoCreado.class, this::onPedidoCreado);
    }

    private void onPedidoCreado(PedidoCreado evento) {
        Product product = repository.findById(evento.getProductoId());
        if (product != null && product.getStock() >= evento.getCantidad()) {
            product.setStock(product.getStock() - evento.getCantidad());
            bus.publish(new PedidoConfirmado(evento.getPedidoId(), evento.getClienteId(), evento.getTotal()));
        } else {
            bus.publish(new PedidoRechazado(evento.getPedidoId()));
        }
    }

    @Override
    public Product findById(long id) { return repository.findById(id); }
}
