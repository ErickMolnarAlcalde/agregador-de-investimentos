package com.agragadorinvestiemnto.agragadorinvestimentos.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_stock")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Stock {

    @Id
    private String ticker;

    private String description;

    @OneToMany(mappedBy = "stock")
    private List<AccountStock> accounts;




}
