package com.tienda.orders;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OrderRepository {
    private final Map<Long, Order> orders = new LinkedHashMap<>();
    private long nextId = 1;

    public Order create(String clienteId, long productoId, int cantidad, long total) {
        Order order = new Order(nextId++, clienteId, productoId, cantidad, "PENDING", total);
        orders.put(order.getId(), order);
        return order;
    }
    public Order findById(long id) { return orders.get(id); }
    public List<Order> findAll() { return new ArrayList<>(orders.values()); }
}
