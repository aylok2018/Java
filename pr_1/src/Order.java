import java.util.ArrayList;
import java.util.List;

class Order {
    private final List<Product> products; // Список товарів у замовленні
    private final double totalPrice; // Загальна вартість замовлення
    private String status; // Статус замовлення

    public Order(Cart cart) {
        this.products = new ArrayList<>(cart.getProducts()); // Копіювання товарів з кошика
        this.totalPrice = cart.getTotalPrice();
        this.status = "Нове"; // Стандартний статус при створенні замовлення
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products); // Повертаємо копію списку, щоб запобігти змінам ззовні
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Замовлення:\n");
        for (Product product : products) {
            sb.append(product.toString()).append("\n");
        }
        sb.append("Загальна вартість: ").append(totalPrice).append("\n");
        sb.append("Статус: ").append(status);
        return sb.toString();
    }
}
