package restapi.tqs.DataModels;

import java.util.List;

public class OrderDTO {
    
    private long clientId;
    private long addressId;
    private int scheduledtimeOfDelivery;
    private List<OrderLegoDTO> legos;


    public OrderDTO() {

    }

    public OrderDTO(long clientId, long addressId, int scheduledtimeOfDelivery, List<OrderLegoDTO> legos) {
        this.clientId = clientId;
        this.addressId = addressId;
        this.scheduledtimeOfDelivery = scheduledtimeOfDelivery;
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

    public int getScheduledtimeOfDelivery() {
        return this.scheduledtimeOfDelivery;
    }

    public void setScheduledtimeOfDelivery(int scheduledtimeOfDelivery) {
        this.scheduledtimeOfDelivery = scheduledtimeOfDelivery;
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
            ", scheduledtimeOfDelivery='" + getScheduledtimeOfDelivery() + "'" +
            ", legos='" + getLegos() + "'" +
            "}";
    }

}
