package restapi.tqs.Models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "order_wow")
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private long orderId;
    @Column(name = "date")
    private Date date;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private Address address;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "order")
    private Set<OrderLego> orderLego = new HashSet<>();

    public Order() {
    }

    public long getOrderId() {
        return this.orderId;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
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
            " orderId='" + getOrderId() + "'" +
            ", date='" + getDate() + "'" +
            ", address='" + getAddress() + "'" +
            ", client='" + getClient() + "'" +
            "}";
    }

}
