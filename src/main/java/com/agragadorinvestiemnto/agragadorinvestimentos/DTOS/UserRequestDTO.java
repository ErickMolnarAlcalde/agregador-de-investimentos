package com.agragadorinvestiemnto.agragadorinvestimentos.DTOS;

import jakarta.validation.constraints.Email;

public record UserRequestDTO(String username,
                             String password,
                             @Email
                             String email,
                             String role) {
}
