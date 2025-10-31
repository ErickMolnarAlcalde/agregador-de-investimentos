package com.agragadorinvestiemnto.agragadorinvestimentos.Repository;

import com.agragadorinvestiemnto.agragadorinvestimentos.Models.AccountStock;
import com.agragadorinvestiemnto.agragadorinvestimentos.Models.AccountStockId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId> {
}
