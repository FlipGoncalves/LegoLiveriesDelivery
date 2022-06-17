package restapi.tqs.DataModels;

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
public class AddressDTO {
    @NonNull
    private String street;

    @NonNull
    private String postalCode;

    @NonNull
    private String city;

    @NonNull
    private String country;

    private double latitude;
    
    private double longitude;
}