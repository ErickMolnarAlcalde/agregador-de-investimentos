package com.agragadorinvestiemnto.agragadorinvestimentos.Repository;

import com.agragadorinvestiemnto.agragadorinvestimentos.Models.BillingAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BillingAddresRepository extends JpaRepository<BillingAddress, UUID> {
}
