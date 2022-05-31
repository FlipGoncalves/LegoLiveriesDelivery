package restapi.tqs.Models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "lego")
public class Lego {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lego_id")
    private long legoId;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private double price;
    @Column(name = "imageUrl")
    private String imageUrl;

    @OneToMany(mappedBy = "lego")
    private Set<Favorites> favorites = new HashSet<>();

    @OneToMany(mappedBy = "lego")
    private Set<OrderLego> orderLego = new HashSet<>();

    public Lego() {

    }
    

    public long getLegoId() {
        return this.legoId;
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

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Set<Favorites> getFavorites() {
        return this.favorites;
    }

    public void setFavorites(Set<Favorites> favorites) {
        this.favorites = favorites;
    }

    public Set<OrderLego> getOrderLego() {
        return this.orderLego;
    }

    public void setOrderLego(Set<OrderLego> orderLego) {
        this.orderLego = orderLego;
    }


    @Override
    public String toString() {
        return "{" +
            " legoId='" + getLegoId() + "'" +
            ", name='" + getName() + "'" +
            ", price='" + getPrice() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            "}";
    }

}
