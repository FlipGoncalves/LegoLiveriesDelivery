package restapi.tqs.DataModels;

public class OrderLegoDTO {
    
    private long legoId;
    private int quantity;
    private double legoPrice;


    public OrderLegoDTO() {
    }

    public OrderLegoDTO(long legoId, int quantity, double legoPrice) {
        this.legoId = legoId;
        this.quantity = quantity;
        this.legoPrice = legoPrice;
    }

    public long getLegoId() {
        return this.legoId;
    }

    public void setLegoId(long legoId) {
        this.legoId = legoId;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getLegoPrice() {
        return this.legoPrice;
    }

    public void setLegoPrice(double legoPrice) {
        this.legoPrice = legoPrice;
    }

    @Override
    public String toString() {
        return "{" +
            " legoId='" + getLegoId() + "'" +
            ", quantity='" + getQuantity() + "'" +
            ", legoPrice='" + getLegoPrice() + "'" +
            "}";
    }

}
