package restapi.tqs.DataModels;

import java.util.Objects;

public class LegoDTO {
    
    private String name;
    private double price;
    private String imgUrl;

    public LegoDTO() {
    }

    public LegoDTO(String name, double price, String imgUrl) {
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImgUrl() {
        return this.imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "{" +
            " name='" + getName() + "'" +
            ", price='" + getPrice() + "'" +
            ", imgUrl='" + getImgUrl() + "'" +
            "}";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof LegoDTO)) {
            return false;
        }
        LegoDTO legoDTO = (LegoDTO) o;
        return Objects.equals(name, legoDTO.name) && price == legoDTO.price && Objects.equals(imgUrl, legoDTO.imgUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, imgUrl);
    }

}
