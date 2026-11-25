package com.tienda;

import com.tienda.bus.EventBus;
import com.tienda.bus.InMemoryEventBus;
import com.tienda.event.PedidoConfirmado;
import com.tienda.event.PedidoRechazado;
import com.tienda.event.Product;
import com.tienda.inventory.InventoryService;
import com.tienda.inventory.ProductRepository;
import com.tienda.orders.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ReglasNegocioTest {
    private OrderService orderService;
    private OrderRepository orderRepo;

    @BeforeEach
    public void setup() {
        EventBus bus = new InMemoryEventBus();
        ProductRepository productRepo = new ProductRepository();
        productRepo.add(new Product(1, "Audífonos", 70000, 50));
        InventoryService inventory = new InventoryService(bus, productRepo);
        orderRepo = new OrderRepository();
        orderService = new OrderService(bus, orderRepo, inventory, 6, 15);
        bus.subscribe(PedidoConfirmado.class, orderService::onPedidoConfirmado);
        bus.subscribe(PedidoRechazado.class, orderService::onPedidoRechazado);
    }

    @Test public void rn1_sinDescuentoBajoUmbral() {
        orderService.crearPedido("C-001", 1, 5);
        assertEquals(350000, orderService.consultarPedido(1).getTotal());
    }

    @Test public void rn1_conDescuentoEnUmbral() {
        orderService.crearPedido("C-001", 1, 6);
        assertEquals(357000, orderService.consultarPedido(1).getTotal());
    }

    @Test public void rn2_totalEntero() {
        orderService.crearPedido("C-001", 1, 39);
        assertEquals(2320500, orderService.consultarPedido(1).getTotal());
    }

    @Test public void rn3_rechazaSiSePasa() {
        orderService.crearPedido("C-001", 1, 50);
        orderService.crearPedido("C-002", 1, 1);
        assertEquals("RECHAZADO", orderService.consultarPedido(2).getEstado());
        assertEquals(0, orderService.consultarPedido(2).getTotal());
    }

    @Test public void rn3_confirmaSiDejaCero() {
        orderService.crearPedido("C-001", 1, 50);
        assertEquals("CONFIRMADO", orderService.consultarPedido(1).getEstado());
    }

    @Test public void rn5_cantidadInvalida() {
        assertThrows(ValidationException.class, () -> orderService.crearPedido("C-001", 1, 0));
        assertEquals(0, orderRepo.findAll().size());
    }

    @Test public void rn5_productoInexistente() {
        assertThrows(ValidationException.class, () -> orderService.crearPedido("C-001", 999, 1));
        assertEquals(0, orderRepo.findAll().size());
    }
}
