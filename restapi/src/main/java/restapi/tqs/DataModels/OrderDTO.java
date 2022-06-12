package restapi.tqs.DataModels;

import java.util.List;

public class OrderDTO {
    
    private long clientId;
    private long addressId;
    private int scheduledTimeOfDelivery;
    private List<OrderLegoDTO> legos;


    public OrderDTO() {

    }

    public OrderDTO(long clientId, long addressId, int scheduledTimeOfDelivery, List<OrderLegoDTO> legos) {
        this.clientId = clientId;
        this.addressId = addressId;
        this.scheduledTimeOfDelivery = scheduledTimeOfDelivery;
        this.legos = legos;
    }


    public long getClientId() {
        return this.clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public long getAddressId() {
        return this.addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public int getScheduledTimeOfDelivery() {
        return this.scheduledTimeOfDelivery;
    }

    public void setScheduledTimeOfDelivery(int scheduledTimeOfDelivery) {
        this.scheduledTimeOfDelivery = scheduledTimeOfDelivery;
    }

    public List<OrderLegoDTO> getLegos() {
        return this.legos;
    }

    public void setLegos(List<OrderLegoDTO> legos) {
        this.legos = legos;
    }


    @Override
    public String toString() {
        return "{" +
            " clientId='" + getClientId() + "'" +
            ", addressId='" + getAddressId() + "'" +
            ", scheduledtimeOfDelivery='" + getScheduledTimeOfDelivery() + "'" +
            ", legos='" + getLegos() + "'" +
            "}";
    }

}
