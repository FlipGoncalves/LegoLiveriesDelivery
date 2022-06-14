package tqs.project.datamodels;


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
public class StoreDTO {

    @NonNull
    private String name;

    @NonNull
    private AddressDTO address;
}
