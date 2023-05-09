package com.tiny.triumph.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record RegistrationRequest(@NotEmpty String firstName, @NotEmpty String lastName,
                                  @NotNull @Size(min = 1, message = "Email is required.") @Email(message = "Email is not well formatted.") String email,
                                  @NotNull @Size(min = 6, max = 15) String password) {

}
