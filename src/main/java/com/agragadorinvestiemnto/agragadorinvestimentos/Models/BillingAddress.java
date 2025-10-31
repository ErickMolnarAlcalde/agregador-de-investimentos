package com.agragadorinvestiemnto.agragadorinvestimentos.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "tb_billing_address")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BillingAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String street;

    private String number;

    @OneToOne
    @JoinColumn(name = "id_account")
    private Account account;
}
