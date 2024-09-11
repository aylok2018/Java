public class Category {
    private int id; // Унікальний ідентифікатор категорії
    private String name; // Назва категорії
    // Конструктор класу
    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Геттери та сеттери
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Категорія{" +
                "id=" + id +
                ", назва='" + name + '\'' +
                '}';
    }
}



