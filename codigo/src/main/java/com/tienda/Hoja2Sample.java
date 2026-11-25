package com.tienda;

import com.tienda.billing.BillingService;
import com.tienda.billing.InvoiceRepository;
import com.tienda.bus.EventBus;
import com.tienda.bus.InMemoryEventBus;
import com.tienda.event.PedidoConfirmado;
import com.tienda.event.PedidoRechazado;
import com.tienda.event.Product;
import com.tienda.inventory.InventoryService;
import com.tienda.inventory.ProductRepository;
import com.tienda.notifications.NotificationService;
import com.tienda.orders.OrderRepository;
import com.tienda.orders.OrderService;

public class Hoja2Sample {
    public static void main(String[] args) {
        EventBus bus = new InMemoryEventBus();
        ProductRepository productRepo = new ProductRepository();
        productRepo.add(new Product(1, "Audifonos", 70000, 50));
        InventoryService inventory = new InventoryService(bus, productRepo);
        OrderRepository orderRepo = new OrderRepository();
        OrderService orderService = new OrderService(bus, orderRepo, inventory, 6, 15);

        InvoiceRepository invoiceRepo = new InvoiceRepository();
        BillingService billing = new BillingService(invoiceRepo);
        NotificationService notification = new NotificationService();

        bus.subscribe(PedidoConfirmado.class, orderService::onPedidoConfirmado);
        bus.subscribe(PedidoConfirmado.class, billing::onPedidoConfirmado);
        bus.subscribe(PedidoConfirmado.class, notification::onPedidoConfirmado);
        bus.subscribe(PedidoRechazado.class, orderService::onPedidoRechazado);

        orderService.crearPedido("C-001", 1, 5);
        orderService.crearPedido("C-999", 1, 5);
        orderService.crearPedido("C-002", 1, 6);
    }
}
