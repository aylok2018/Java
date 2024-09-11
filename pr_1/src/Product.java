class Product {
    private final int id;
    private final String name;
    private final double price;
    private final String description;
    private final Category category;

    public Product(int id, String name, double price, String description, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Назва: " + name + ", Ціна: " + price + ", Опис: " + description + ", Категорія: " + category.getName();
    }
}






