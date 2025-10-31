package com.agragadorinvestiemnto.agragadorinvestimentos.Controllers;

import com.agragadorinvestiemnto.agragadorinvestimentos.DTOS.AccountStockResponseDto;
import com.agragadorinvestiemnto.agragadorinvestimentos.DTOS.AssociateAccountStockDto;
import com.agragadorinvestiemnto.agragadorinvestimentos.DTOS.CreateStockDto;
import com.agragadorinvestiemnto.agragadorinvestimentos.Service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/{accountid}/stocks")
    public ResponseEntity<Void> associateStock(@PathVariable UUID accountid,
                                               @Valid @RequestBody AssociateAccountStockDto dto){
        accountService.associateStock(accountid,dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{accountid}/stocks")
    public ResponseEntity<List<AccountStockResponseDto>>listStock(@PathVariable UUID accountid){
        var stocks = accountService.listStocks(accountid);
        return ResponseEntity.ok(stocks);
    }
}

