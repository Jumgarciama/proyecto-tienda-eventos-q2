package com.tienda.inventory;

import com.tienda.event.Product;
import java.util.HashMap;
import java.util.Map;

public class ProductRepository {
    private final Map<Long, Product> products = new HashMap<>();
    public void add(Product p) { products.put(p.getId(), p); }
    public Product findById(long id) { return products.get(id); }
}
