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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "lego")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "legoId")
public class Lego {
    private String name;
    @Column(name = "price")
    private double price;
    @Column(name = "imageUrl")
    private String imageUrl;

    
    @OneToMany(mappedBy = "lego")
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Favorites> favorites = new HashSet<>();

    
    @OneToMany(mappedBy = "lego")
    @JsonIdentityReference(alwaysAsId = true)
    private Set<OrderLego> orderLego = new HashSet<>();

    public Lego() {

    public Lego(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public long getLegoId() {
        return this.legoId;
    }

    public void setLegoId(long legoId) {
        this.legoId = legoId;
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
