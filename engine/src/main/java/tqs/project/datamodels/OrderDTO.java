package tqs.project.datamodels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDTO {

    @NonNull
    private String clientName;

    private int timeOfDelivery;

    @NonNull
    private String storeName;

    @NonNull
    private AddressDTO address;
}
