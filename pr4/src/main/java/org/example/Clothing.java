package org.example;

// Клас Clothing, що також реалізує інтерфейс Product.
public class Clothing implements Product {
    private String name;

    // Конструктор, який приймає назву продукту.
    public Clothing(String name) {
        this.name = name;
    }

    // Реалізація методу getName().
    @Override
    public String getName() {
        return name;
    }
}
