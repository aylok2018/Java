package org.example;

// Клас Electronics, що реалізує інтерфейс Product.
public class Electronics implements Product {
    private String name;

    // Конструктор, який приймає назву продукту.
    public Electronics(String name) {
        this.name = name;
    }

    // Реалізація методу getName().
    @Override
    public String getName() {
        return name;
    }
}
