package com.tienda.event;

public class Product {
    private final long id;
    private final String nombre;
    private final long precio;
    private int stock;

    public Product(long id, String nombre, long precio, int stock) {
        this.id = id; this.nombre = nombre; this.precio = precio; this.stock = stock;
    }
    public long getId() { return id; }
    public String getNombre() { return nombre; }
    public long getPrecio() { return precio; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}
