package tqs.project.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "rider")
public class Rider {
    
    @Id
    @Column(name = "rider_id")
    private long riderId;
    @Column(name = "review_sum")
    private int reviewSum;
    @Column(name = "totaReviews")
    private int totalReviews;

    @OneToOne
    @JoinColumn(referencedColumnName = "user_id")
    @MapsId
    private User user;

    @OneToMany(mappedBy = "rider")
    private Set<Order> orders = new HashSet<>();

    public Rider() {

    }

    public Rider(long riderId, int reviewSum, int totalReviews) {
        this.riderId = riderId;
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
