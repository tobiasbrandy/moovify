package ar.edu.itba.paw.webapp.dto.input;

import ar.edu.itba.paw.webapp.dto.input.validation.annotations.SpacesNormalization;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserCreateDto {

    @NotNull
    @Size(min = 6, max = 50)
    @Pattern(regexp = "^[a-zA-Z0-9#_]+$")
    private String username;

    @NotNull
    @Pattern(regexp = "^[^\\s]+$")
    @Size(min=12, max=30)
    private String password;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z ]+$")
    @SpacesNormalization(message = "invalid use of spaces")
    @Size(min = 2, max = 50)
    private String name;

    @Email
    @NotEmpty
    private String email;

    @NotNull
    @Size(max=400)
    private String description;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
