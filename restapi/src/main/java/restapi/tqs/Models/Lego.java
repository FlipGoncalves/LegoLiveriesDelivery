package restapi.tqs.Models;

public class Lego {
    private String name;
    private Double price;

    public Lego(String name, Double price) {
        this.name = name;
        this.price = price;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Lego [name=" + name + ", price=" + price + "]";
    }
}
