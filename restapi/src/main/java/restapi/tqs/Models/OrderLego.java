package restapi.tqs.Models;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "order_lego")
public class OrderLego {
   
    @EmbeddedId
    @JsonIgnore
    OrderLegoId id;

    @ManyToOne
    @MapsId("orderId")
    @JsonIdentityReference(alwaysAsId = true)
    private Order order;

    @ManyToOne
    @MapsId("legoId")
    @JsonIdentityReference(alwaysAsId = true)
    private Lego lego;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private double price;

    public OrderLego() {
    }

    public OrderLegoId getId() {
        return this.id;
    }

    public void setId(OrderLegoId id) {
        this.id = id;
    }

    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Lego getLego() {
        return this.lego;
    }

    public void setLego(Lego lego) {
        this.lego = lego;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", order='" + getOrder() + "'" +
            ", lego='" + getLego() + "'" +
            ", quantity='" + getQuantity() + "'" +
            ", price='" + getPrice() + "'" +
            "}";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof OrderLego)) {
            return false;
        }
        OrderLego orderLego = (OrderLego) o;
        return Objects.equals(id, orderLego.id) && Objects.equals(order, orderLego.order) && Objects.equals(lego, orderLego.lego) && quantity == orderLego.quantity && price == orderLego.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, order, lego, quantity, price);
    }

}
