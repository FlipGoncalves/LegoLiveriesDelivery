package tqs.project.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "`order`")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "orderId")
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private long orderId;
    @Column(name = "clientName")
    private String clientName;
    @Column(name = "`date`")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "time_of_delivery")
    private int timeOfDelivery;
    @Column(name = "review")
    private int review;
    @Column(name = "status")
    private int status; //0 = Not done 1 = In Transit 2 = Done

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private Store store;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private Rider rider;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private Address address;

    public Order() {
    }

    public Order(String clientName, Date date, int timeOfDelivery, int review) {
        this.clientName = clientName;
        this.date = date;
        this.timeOfDelivery = timeOfDelivery;
        this.review = review;
        this.status = 0;
    }

    public long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getClientName() {
        return this.clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTimeOfDelivery() {
        return this.timeOfDelivery;
    }

    public void setTimeOfDelivery(int timeOfDelivery) {
        this.timeOfDelivery = timeOfDelivery;
    }

    public int getReview() {
        return this.review;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public Store getStore() {
        return this.store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Rider getRider() {
        return this.rider;
    }

    public void setRider(Rider rider) {
        this.rider = rider;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "{" +
            " orderId='" + getOrderId() + "'" +
            ", clientName='" + getClientName() + "'" +
            ", date='" + getDate() + "'" +
            ", timeOfDelivery='" + getTimeOfDelivery() + "'" +
            ", review='" + getReview() + "'" +
            ", store='" + getStore() + "'" +
            ", rider='" + getRider() + "'" +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
