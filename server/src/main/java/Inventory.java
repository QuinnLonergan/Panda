
public class Inventory {
    private String id;
    private String name;
    private int capacity;
    private int stock;

    public Inventory() {
    }

    public Inventory(String id, String name, int capacity, int stock) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Inventory [id=" + id + ", name=" + name + ", capacity=" + capacity + ", stock=" + stock + "]";
    }

}
