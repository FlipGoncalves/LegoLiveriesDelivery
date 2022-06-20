package restapi.tqs.Models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "lego")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "legoId")
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
    
    @OneToMany(mappedBy = "lego", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIdentityReference(alwaysAsId = true)
    private Set<OrderLego> orderLego = new HashSet<>();

    public Lego() {

    }

    public long getLegoId() {
        return this.legoId;
    }

    public void setLegoId(long legoId) {
        this.legoId = legoId;
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
