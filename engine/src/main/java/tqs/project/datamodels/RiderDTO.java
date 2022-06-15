package tqs.project.datamodels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RiderDTO {
    @NonNull
    private String username;

    @NonNull
    private String email;

    @NonNull
    private String password;

    private int numRev;

    private int sumRev;
}
