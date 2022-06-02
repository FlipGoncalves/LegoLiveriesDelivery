package tqs.project.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    @NonNull
    private String username;

    @NonNull
    private String email;

    @NonNull
    private String password;
}
