package com.tienda;

import com.tienda.bus.EventBus;
import com.tienda.bus.InMemoryEventBus;
import com.tienda.event.PedidoConfirmado;
import com.tienda.event.PedidoRechazado;
import com.tienda.event.Product;
import com.tienda.inventory.InventoryService;
import com.tienda.inventory.ProductRepository;
import com.tienda.orders.OrderRepository;
import com.tienda.orders.OrderService;

public class Hoja1Sample {
    public static void main(String[] args) {
        EventBus bus = new InMemoryEventBus();
        ProductRepository productRepo = new ProductRepository();
        productRepo.add(new Product(1, "Audífonos", 70000, 50));
        InventoryService inventory = new InventoryService(bus, productRepo);
        OrderRepository orderRepo = new OrderRepository();
        OrderService orderService = new OrderService(bus, orderRepo, inventory, 6, 15);
        bus.subscribe(PedidoConfirmado.class, orderService::onPedidoConfirmado);
        bus.subscribe(PedidoRechazado.class, orderService::onPedidoRechazado);

        orderService.crearPedido("C-001", 1, 5);
        orderService.crearPedido("C-002", 1, 6);
        orderService.crearPedido("C-001", 1, 40);
        orderService.crearPedido("C-003", 1, 39);
    }
}
