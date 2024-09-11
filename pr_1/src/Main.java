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
            switch (choice) {
                case 1:
                    System.out.println(product1);
                    System.out.println(product2);
                    System.out.println(product3);
                    break;
                case 2:
                    System.out.println("Введіть ID товару для додавання до кошика:");
                    int idToAdd = scanner.nextInt();
                    if (idToAdd == 1) cart.addProduct(product1);
                    else if (idToAdd == 2) cart.addProduct(product2);
                    else if (idToAdd == 3) cart.addProduct(product3);
                    else System.out.println("Товар з таким ID не знайдено");
                    break;
                case 3:
                    System.out.println("Введіть ID товару для видалення з кошика:");
                    int idToRemove = scanner.nextInt();
                    if (idToRemove == 1) cart.removeProduct(product1);
                    else if (idToRemove == 2) cart.removeProduct(product2);
                    else if (idToRemove == 3) cart.removeProduct(product3);
                    else System.out.println("Товар з таким ID не знайдено в кошику");
                    break;
                case 4:
                    System.out.println(cart);
                    break;
                case 5:
                    Order order = new Order(cart);
                    orderHistory.addOrder(order);
                    System.out.println("Замовлення оформлено!");
                    cart = new Cart(); // Очищення кошика після оформлення замовлення
                    break;
                case 6:
                    System.out.println(orderHistory);
                    break;
                case 7:
                    System.out.println("Введіть назву товару для пошуку:");
                    String nameToSearch = scanner.nextLine().toLowerCase();
                    List<Product> foundByName = productList.stream()
                            .filter(product -> product.getName().toLowerCase().contains(nameToSearch))
                            .toList();
                    if (foundByName.isEmpty()) {
                        System.out.println("Товари з такою назвою не знайдено.");
                    } else {
                        foundByName.forEach(System.out::println);
                    }
                    break;
                case 8:
                    System.out.println("Введіть ID категорії для пошуку:");
                    int categoryId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    List<Product> foundByCategory = productList.stream()
                            .filter(product -> product.getCategory().getId() == categoryId)
                            .toList();
                    if (foundByCategory.isEmpty()) {
                        System.out.println("Товари з такою категорією не знайдено.");
                    } else {
                        foundByCategory.forEach(System.out::println);
                    }
                    break;
                case 0:
                    System.out.println("Дякуємо, що використовували наш магазин!");
                    return;
                default:
                    System.out.println("Невідома опція. Спробуйте ще раз.");
                    break;
            }
        }
    }
}

class Cart {
    private final List<Product> products; // Список товарів у кошику

    // Конструктор класу
    public Cart() {
        this.products = new ArrayList<>();
    }

    // Метод для додавання товару до кошика
    public void addProduct(Product product) {
        products.add(product);
    }

    // Метод для видалення товару з кошика
    public void removeProduct(Product product) {
        products.remove(product);
    }
    // Метод для отримання загальної вартості товарів у кошику
    public double getTotalPrice() {
        double total = 0;
        for (Product product : products) {
            total += product.getPrice();
        }
        return total;
    }

    private static Product findProductById(List<Product> productList, int id) {
        return productList.stream()
                .filter(product -> product.getId() == id)
                .findFirst()
                .orElse(null);
    }
    // Метод для виведення інформації про всі товари у кошику
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Кошик містить:\n");
        for (Product product : products) {
            sb.append(product.toString()).append("\n");
        }
        sb.append("Загальна вартість: ").append(getTotalPrice());
        return sb.toString();
    }

    public int getProducts() {
        return 0;
    }
}
class OrderHistory {
    private final List<Order> orders;

    public OrderHistory() {
        this.orders = new ArrayList<>();
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public List<Order> getOrders() {
        return new ArrayList<>(orders); // Повертаємо копію списку
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Історія замовлень:\n");
        for (Order order : orders) {
            sb.append(order.toString()).append("\n\n");
        }
        return sb.toString();
    }
}








