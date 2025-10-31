package com.agragadorinvestiemnto.agragadorinvestimentos.Models;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Embeddable

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountStockId {

    private UUID accountId;

    private String stockId;

}
