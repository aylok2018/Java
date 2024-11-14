package org.example;

// Клас для зберігання замовлень.
public class OrderStorage<T extends Product> {
    private java.util.List<OrderProcessor<? extends T>> orders = new java.util.ArrayList<>();

    // Метод для додавання замовлення до списку.
    public void addOrder(OrderProcessor<? extends T> order) {
        orders.add(order);
    }

    // Метод для отримання всіх замовлень.
    public java.util.List<OrderProcessor<? extends T>> getOrders() {
        return orders;
    }
}
