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
public class LegoDTO {
    
    @NonNull
    private String name;

    private double price;

    @NonNull
    private String imgUrl;
}
