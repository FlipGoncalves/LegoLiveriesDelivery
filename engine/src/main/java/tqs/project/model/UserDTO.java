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

    public UserDTO(@NonNull String username, @NonNull String email, @NonNull String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
