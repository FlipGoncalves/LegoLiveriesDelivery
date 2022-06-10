package restapi.tqs.Models;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class OrderLegoId implements Serializable{
    
    private long orderId;
    private long legoId;

    public OrderLegoId() {
    }

    public OrderLegoId(long orderId, long legoId) {
        this.orderId = orderId;
        this.legoId = legoId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof OrderLegoId)) {
            return false;
        }
        OrderLegoId orderLegoId = (OrderLegoId) o;
        return orderId == orderLegoId.orderId && legoId == orderLegoId.legoId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, legoId);
    } 


    public long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getLegoId() {
        return this.legoId;
    }

    public void setLegoId(long legoId) {
        this.legoId = legoId;
    }

    @Override
    public String toString() {
        return "{" +
            " clientId='" + getOrderId() + "'" +
            ", legoId='" + getLegoId() + "'" +
            "}";
    }

}
