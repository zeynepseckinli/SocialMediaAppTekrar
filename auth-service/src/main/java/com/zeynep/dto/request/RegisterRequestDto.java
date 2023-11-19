package com.zeynep.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {

    @NotBlank
    @Size(min = 5, max = 20, message = "Kullanici adı en az 5, en fazla 20 karakterden oluşmalıdır.")
    private String username;
    @Email
    private String email;
    @NotBlank
    @Size(min = 8,max = 32, message = "Sifre minimum 8, maksimum 32 karakterden oluşmalıdır.")
    private String password;
}