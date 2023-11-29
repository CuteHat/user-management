package com.crocobet.usermanagement.dto;


import com.crocobet.usermanagement.exception.validation.StrongPassword;
import com.crocobet.usermanagement.persistence.model.RoleName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserCreateDTO {
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @Size(min = 4, max = 50)
    private String username;
    @Size(min = 8, max = 64)
    @StrongPassword
    private String password;
    private RoleName role;
    @NotNull
    private Boolean active;

    @Override
    public String toString() {
        return "UserCreateDTO{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", role=" + role +
                ", active=" + active +
                ", password= **** " +
                '}';
    }
}
