package com.agragadorinvestiemnto.agragadorinvestimentos.Repository;

import com.agragadorinvestiemnto.agragadorinvestimentos.Models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock,String> {
}
