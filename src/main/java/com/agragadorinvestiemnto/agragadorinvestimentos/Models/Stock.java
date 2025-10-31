package com.agragadorinvestiemnto.agragadorinvestimentos.Models;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_stock")
public class Stock {

    @Id
    private String ticker;

    private String description;

    @OneToMany(mappedBy = "stock")
    private List<AccountStock> accounts;




}
