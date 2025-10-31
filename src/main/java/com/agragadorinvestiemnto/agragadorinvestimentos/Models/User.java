package com.agragadorinvestiemnto.agragadorinvestimentos.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_user")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String username;

    private String email;

    private String password;

    private String role;

    @CreationTimestamp
    private Instant creationTimeStamp;

    @CreationTimestamp
    private Instant updateTimeStamp;

    @OneToMany(mappedBy = "user")
    private List<Account> accounts;

    @ManyToMany
    @JoinTable(name = "tb_account_stock",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name ="stock_id"))
    private List<Stock> stocks;

}
