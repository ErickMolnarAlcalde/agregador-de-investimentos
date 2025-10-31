package com.agragadorinvestiemnto.agragadorinvestimentos.Service;

import com.agragadorinvestiemnto.agragadorinvestimentos.DTOS.AccountStockResponseDto;
import com.agragadorinvestiemnto.agragadorinvestimentos.DTOS.AssociateAccountStockDto;
import com.agragadorinvestiemnto.agragadorinvestimentos.Models.AccountStock;
import com.agragadorinvestiemnto.agragadorinvestimentos.Models.AccountStockId;
import com.agragadorinvestiemnto.agragadorinvestimentos.Repository.AccountRepository;
import com.agragadorinvestiemnto.agragadorinvestimentos.Repository.AccountStockRepository;
import com.agragadorinvestiemnto.agragadorinvestimentos.Repository.StockRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    private AccountRepository accountRepository;
    private StockRepository stockRepository;
    private AccountStockRepository accountStockRepository;

    public AccountService(AccountRepository accountRepository, StockRepository stockRepository,
                          AccountStockRepository accountStockRepository) {
        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;
        this.accountStockRepository = accountStockRepository;
    }

    public void associateStock(UUID accountId, AssociateAccountStockDto dto){
        var account = accountRepository.findById(accountId).orElseThrow(()->
                new RuntimeException("Account id not found!"));
        var stock = stockRepository.findById(dto.stockId()).orElseThrow(()->
                new RuntimeException("Stock id nor found!"));

        var id = new AccountStockId(account.getId(), stock.getTicker());
        var entity = new AccountStock(
                id,
                account,
                stock,
                dto.quantity()
        );
        accountStockRepository.save(entity);
    }
    
    public List<AccountStockResponseDto> listStocks(UUID accountId){

        var account = accountRepository.findById(accountId).orElseThrow(()->
                new RuntimeException("id not found"));

         return account.getAccountStocks().stream().map(as->
                new AccountStockResponseDto(as.getId().getStockId(),
                        as.getQuantity(), 0.0)).toList();

    }
}
