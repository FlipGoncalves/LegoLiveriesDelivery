package restapi.tqs.Models;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "order_lego")
public class OrderLego {
   
    @EmbeddedId
    OrderLegoId id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Order order;

    @ManyToOne
    @MapsId("legoId")
    @JoinColumn(name = "lego_id",referencedColumnName = "lego_id")
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
}
