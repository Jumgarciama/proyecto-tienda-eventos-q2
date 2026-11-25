package com.tienda.bus;

import com.tienda.event.Product;

public interface ProductCatalog {
    Product findById(long id);
}
