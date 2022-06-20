package tqs.project.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@Entity
@Table(name = "rider")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "riderId")
public class Rider {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rider_id")
    private long riderId;
    @Column(name = "review_sum")
    private int reviewSum;
    @Column(name = "totaReviews")
    private int totalReviews;

    @OneToOne
    @MapsId
    private User user;

    @OneToMany(mappedBy = "rider")
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Order> orders = new HashSet<>();

    public Rider() {

    }

    public Rider(int reviewSum, int totalReviews) {
        this.reviewSum = reviewSum;
        this.totalReviews = totalReviews;
    }

    public long getRiderId() {
        return this.riderId;
    }

    public void setRiderId(long riderId) {
        this.riderId = riderId;
    }

    public int getReviewSum() {
        return this.reviewSum;
    }

    public void setReviewSum(int reviewSum) {
        this.reviewSum = reviewSum;
    }

    public int getTotalReviews() {
        return this.totalReviews;
    }

    public void setTotalReviews(int totalReviews) {
        this.totalReviews = totalReviews;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Order> getOrders() {
        return this.orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "{" +
            " riderId='" + getRiderId() + "'" +
            ", reviewSum='" + getReviewSum() + "'" +
            ", totalReviews='" + getTotalReviews() + "'" +
            ", user='" + getUser() + "'" +
            "}";
    }
}
