package com.tienda.orders;

import com.tienda.bus.EventBus;
import com.tienda.bus.ProductCatalog;
import com.tienda.event.PedidoConfirmado;
import com.tienda.event.PedidoCreado;
import com.tienda.event.PedidoRechazado;
import com.tienda.event.Product;

public class OrderService {
    private final EventBus bus;
    private final OrderRepository repository;
    private final ProductCatalog catalog;
    private final int umbral;
    private final int descuento;

    public OrderService(EventBus bus, OrderRepository repository, ProductCatalog catalog,
                        int umbral, int descuento) {
        this.bus = bus; this.repository = repository; this.catalog = catalog;
        this.umbral = umbral; this.descuento = descuento;
    }

    public void crearPedido(String clienteId, long productoId, int cantidad) {
        if (cantidad <= 0) throw new ValidationException("cantidad debe ser > 0");
        Product product = catalog.findById(productoId);
        if (product == null) throw new ValidationException("producto no existe");

        int desc = (cantidad >= umbral) ? descuento : 0;
        long total = (long) cantidad * product.getPrecio() * (100 - desc) / 100;

        Order order = repository.create(clienteId, productoId, cantidad, total);
        bus.publish(new PedidoCreado(order.getId(), clienteId, productoId, cantidad, total));
    }

    public void onPedidoConfirmado(PedidoConfirmado evento) {
        Order order = repository.findById(evento.getPedidoId());
        order.setEstado("CONFIRMADO");
        order.setTotal(evento.getTotal());
        System.out.println("PEDIDO " + order.getId() + " CONFIRMADO total=" + order.getTotal());
    }

    public void onPedidoRechazado(PedidoRechazado evento) {
        Order order = repository.findById(evento.getPedidoId());
        order.setEstado("RECHAZADO");
        order.setTotal(0);
        System.out.println("PEDIDO " + order.getId() + " RECHAZADO total=0");
    }

    public Order consultarPedido(long id) { return repository.findById(id); }

    public long totalPorCliente(String clienteId) {
        return repository.findAll().stream()
                .filter(o -> clienteId.equals(o.getClienteId()) && "CONFIRMADO".equals(o.getEstado()))
                .mapToLong(Order::getTotal).sum();
    }
}
