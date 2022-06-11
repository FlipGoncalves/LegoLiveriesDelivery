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

@Entity
@Table(name = "`order`")
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private long orderId;
    @Column(name = "external_order_id")
    private long externalOrderId;
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
    @JoinColumn(name = "store_id", referencedColumnName = "store_id")
    private Store store;

    @ManyToOne
    //@JoinColumn(name = "rider_id", referencedColumnName = "rider_id")
    private Rider rider;

    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private Address address;

    public Order() {
    }

    public Order(long externalOrderId, String clientName, Date date, int timeOfDelivery, int review) {
        this.externalOrderId = externalOrderId;
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

    public long getExternalOrderId() {
        return this.externalOrderId;
    }

    public void setExternalOrderId(long externalOrderId) {
        this.externalOrderId = externalOrderId;
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
            ", externalOrderId='" + getExternalOrderId() + "'" +
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
