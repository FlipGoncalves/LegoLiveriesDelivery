package pt.ua.tqs.project;

public class LegoSet {

    private String imageUrl;
    private String name;
    private String price;


    public LegoSet() {
    }

    public LegoSet(String imageUrl, String name, String price) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.price = price;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "{" +
            " imageUrl='" + getImageUrl() + "'" +
            ", name='" + getName() + "'" +
            ", price='" + getPrice() + "'" +
            "}";
    }

}
