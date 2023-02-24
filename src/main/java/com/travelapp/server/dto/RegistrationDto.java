package com.travelapp.server.dto;

import com.travelapp.server.entity.User;
import java.time.LocalDate;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegistrationDto {

    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    private LocalDate dateOfBirth;

    public User toUser() {
        User user = new User(username, password, email, dateOfBirth);
        user.setPassword(password);
        return user;
    }
}
