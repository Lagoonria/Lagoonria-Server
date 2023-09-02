package com.example.lagoonria.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotBlank(message = "first name is required")
    private String firstName;
    @NotBlank(message = "last name is required")
    private String lastName;
    @NotBlank(message = "address is required")
    private String address;
    @NotBlank(message = "email is required")
    @Email(message = "email is not valid")
    private String email;
    @NotBlank(message = "created password is required")
    @Size(min = 8, message = "created password should have 8 characters")
    private String createdPassword;
    @NotBlank(message = "confirm password is required")
    @Size(min = 8, message = "confirm password should have 8 characters")
    private String confirmPassword;

}
