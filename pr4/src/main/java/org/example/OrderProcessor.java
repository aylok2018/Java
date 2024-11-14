package org.example;

// Узагальнений клас для обробки замовлень на будь-які продукти.
public class OrderProcessor<T extends Product> {
    private T product;

    // Конструктор, який приймає об'єкт продукту.
    public OrderProcessor(T product) {
        this.product = product;
    }

    // Метод для обробки замовлення, виводить назву продукту.
    public void processOrder() {
        System.out.println("Processing order for: " + product.getName());
    }
}
