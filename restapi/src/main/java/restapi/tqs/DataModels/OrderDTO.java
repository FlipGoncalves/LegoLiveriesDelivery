package restapi.tqs.DataModels;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDTO {
    
    private long clientId;

    @NonNull
    private AddressDTO address;

    private int scheduledTimeOfDelivery;

    @NonNull
    private List<OrderLegoDTO> legos;

}
