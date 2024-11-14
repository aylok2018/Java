package org.example;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Створення категорій
        Category electronics = new Category(1, "Електроніка");
        Category smartphones = new Category(2, "Смартфони");
        Category accessories = new Category(3, "Аксесуари");

        // Створення об'єктів класу Product
        Product product1 = new Product(1, "Ноутбук", 19999.99, "Високопродуктивний ноутбук для роботи та ігор", electronics);
        Product product2 = new Product(2, "Смартфон", 12999.50, "Смартфон з великим екраном та високою автономністю", smartphones);
        Product product3 = new Product(3, "Навушники", 2499.00, "Бездротові навушники з шумозаглушенням", accessories);

        List<Product> productList = List.of(product1, product2, product3);

        // Створення кошика і історії замовлень
        Cart cart = new Cart();
        OrderHistory orderHistory = new OrderHistory();

        while (true) {
            System.out.println("\nВиберіть опцію:");
            System.out.println("1 - Переглянути список товарів");
            System.out.println("2 - Додати товар до кошика");
            System.out.println("3 - Видалити товар з кошика");
            System.out.println("4 - Переглянути кошик");
            System.out.println("5 - Зробити замовлення");
            System.out.println("6 - Переглянути історію замовлень");
            System.out.println("7 - Пошук товарів за назвою");
            System.out.println("8 - Пошук товарів за категорією");
            System.out.println("0 - Вийти");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Споживаємо новий рядок після вводу числа
            switch (choice) {
                case 1 -> productList.forEach(System.out::println);
                case 2 -> {
                    System.out.println("Введіть ID товару для додавання до кошика:");
                    int idToAdd = scanner.nextInt();
                    productList.stream()
                            .filter(product -> product.getId() == idToAdd)
                            .findFirst()
                            .ifPresentOrElse(cart::addProduct,
                                    () -> System.out.println("Товар з таким ID не знайдено"));
                }
                case 3 -> {
                    System.out.println("Введіть ID товару для видалення з кошика:");
                    int idToRemove = scanner.nextInt();
                    productList.stream()
                            .filter(product -> product.getId() == idToRemove)
                            .findFirst()
                            .ifPresentOrElse(cart::removeProduct,
                                    () -> System.out.println("Товар з таким ID не знайдено в кошику"));
                }
                case 4 -> System.out.println(cart);
                case 5 -> {
                    Order order = new Order(cart);
                    orderHistory.addOrder(order);
                    System.out.println("Замовлення оформлено!");
                    cart.clear(); // Очищення кошика після оформлення замовлення
                }
                case 6 -> System.out.println(orderHistory);
                case 7 -> {
                    System.out.println("Введіть назву товару для пошуку:");
                    String nameToSearch = scanner.nextLine().toLowerCase();
                    List<Product> foundByName = productList.stream()
                            .filter(product -> product.getName().toLowerCase().contains(nameToSearch))
                            .collect(Collectors.toList());
                    if (foundByName.isEmpty()) {
                        System.out.println("Товари з такою назвою не знайдено.");
                    } else {
                        foundByName.forEach(System.out::println);
                    }
                }
                case 8 -> {
                    System.out.println("Введіть ID категорії для пошуку:");
                    int categoryId = scanner.nextInt();
                    List<Product> foundByCategory = productList.stream()
                            .filter(product -> product.getCategory().getId() == categoryId)
                            .collect(Collectors.toList());
                    if (foundByCategory.isEmpty()) {
                        System.out.println("Товари з такою категорією не знайдено.");
                    } else {
                        foundByCategory.forEach(System.out::println);
                    }
                }
                case 0 -> {
                    System.out.println("Дякуємо, що використовували наш магазин!");
                    return;
                }
                default -> System.out.println("Невідома опція. Спробуйте ще раз.");
            }
        }
    }
}

// Lombok автоматично генерує всі необхідні методи
@Data
@AllArgsConstructor
@NoArgsConstructor
class Category {
    private int id;
    private String name;
}

@Data
@AllArgsConstructor
class Product {
    private int id;
    private String name;
    private double price;
    private String description;
    private Category category;
}

@Data
class Cart {
    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public double getTotalPrice() {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }

    public void clear() {
        products.clear();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Кошик містить:\n");
        products.forEach(product -> sb.append(product).append("\n"));
        sb.append("Загальна вартість: ").append(getTotalPrice());
        return sb.toString();
    }
}

@Data
class OrderHistory {
    private final List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        orders.add(order);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Історія замовлень:\n");
        orders.forEach(order -> sb.append(order).append("\n\n"));
        return sb.toString();
    }
}

@Data
class Order {
    private final List<Product> products;
    private final double totalPrice;
    private String status;

    public Order(Cart cart) {
        this.products = new ArrayList<>(cart.getProducts());
        this.totalPrice = cart.getTotalPrice();
        this.status = "Нове";
    }
}
