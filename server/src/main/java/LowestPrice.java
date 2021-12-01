public class LowestPrice {
    private double price;

    public LowestPrice() {
    }

    public LowestPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return "LowestPrice [price=" + price + "]";
    }
}
