package restapi.tqs.Models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "orderId")
@Entity
@Table(name = "`order`")
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private long orderId;
    @Column(name = "external_order_id")
    private long externalOrderId;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "scheduled_time_of_delivery")
    private int scheduledTimeOfDelivery;
    @Column(name = "rider_name")
    private String riderName;
    @Column(name = "total_price")
    private double totalPrice;
    @Column(name = "order_status")
    private int orderStatus; //0 = Not done, 1 = In Progress, 2 = Done

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private Address address;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private Client client;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<OrderLego> orderLego = new HashSet<>();

    public Order() {
        this.orderStatus = 0;
        this.scheduledTimeOfDelivery = -1;
    }
    
    public long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getScheduledTimeOfDelivery() {
        return this.scheduledTimeOfDelivery;
    }

    public void setScheduledTimeOfDelivery(int scheduledTimeOfDelivery) {
        this.scheduledTimeOfDelivery = scheduledTimeOfDelivery;
    }

    public String getRiderName() {
        return this.riderName;
    }

    public void setRiderName(String riderName) {
        this.riderName = riderName;
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

    public double getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getExternalOrderId() {
        return this.externalOrderId;
    }

    public void setExternalOrderId(long externalOrderId) {
        this.externalOrderId = externalOrderId;
    }

    public int getOrderStatus() {
        return this.orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
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
