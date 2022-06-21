package restapi.tqs.DataModels;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderLegoDTO {
    
    private long legoId;

    private int quantity;

    private double legoPrice;

}
