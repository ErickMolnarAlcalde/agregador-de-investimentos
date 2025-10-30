package com.agragadorinvestiemnto.agragadorinvestimentos.DTOS;


import java.time.LocalDateTime;

public record ErrorDto (String message,
                        String detail,
                        LocalDateTime time) {
}
