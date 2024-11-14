package org.example;

// Головний клас, який демонструє роботу всієї системи.
public class Main {

    public static void main(String[] args) {
        // Створення замовлення на електроніку.
        OrderProcessor<Electronics> electronicsOrder = new OrderProcessor<>(new Electronics("Laptop"));
        // Створення замовлення на одяг.
        OrderProcessor<Clothing> clothingOrder = new OrderProcessor<>(new Clothing("Jacket"));

        // Створюємо об'єкт сховища для зберігання замовлень.
        OrderStorage<Product> storage = new OrderStorage<>();

        // Додаємо замовлення на електроніку до сховища.
        storage.addOrder(electronicsOrder);
        // Додаємо замовлення на одяг до сховища.
        storage.addOrder(clothingOrder);

        // Обробляємо замовлення у багатопоточному режимі.
        OrderThreadManager.processOrdersInThreads(storage.getOrders());

        // Приклад використання лямбда-виразу для фільтрації замовлень за типом
        storage.getOrders().stream()
                .filter(order -> order.getClass().equals(OrderProcessor.class)) // Фільтруємо замовлення по типу
                .forEach(order -> {
                    order.processOrder();
                });
    }
}
