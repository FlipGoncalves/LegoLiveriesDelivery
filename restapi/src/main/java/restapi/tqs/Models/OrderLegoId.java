package restapi.tqs.Models;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class OrderLegoId implements Serializable{
    
    private long clientId;
    private long legoId;

    public OrderLegoId() {
    }

    public OrderLegoId(long clientId, long legoId) {
        this.clientId = clientId;
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
        return clientId == orderLegoId.clientId && legoId == orderLegoId.legoId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, legoId);
    } 


    public long getClientId() {
        return this.clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
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
            " clientId='" + getClientId() + "'" +
            ", legoId='" + getLegoId() + "'" +
            "}";
    }

}
