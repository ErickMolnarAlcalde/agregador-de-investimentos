package com.agragadorinvestiemnto.agragadorinvestimentos.Repository;

import com.agragadorinvestiemnto.agragadorinvestimentos.Models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
}
