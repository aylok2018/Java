package org.example;

// Клас для керування багатопоточністю під час обробки замовлень.
public class OrderThreadManager {

    // Узагальнений метод для обробки замовлень у потоках.
    public static <T extends Product> void processOrdersInThreads(java.util.List<OrderProcessor<? extends T>> orders) {
        orders.forEach(order -> {
            Runnable task = order::processOrder;  // Лямбда-вираз для створення задачі.
            new Thread(task).start(); // Створюємо і запускаємо новий потік для кожного замовлення.
        });
    }
}
