package com.agragadorinvestiemnto.agragadorinvestimentos.Service;

import com.agragadorinvestiemnto.agragadorinvestimentos.DTOS.CreateStockDto;
import com.agragadorinvestiemnto.agragadorinvestimentos.Models.Stock;
import com.agragadorinvestiemnto.agragadorinvestimentos.Repository.StockRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class StockService {

    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void createStock(CreateStockDto createStockDto){
        Stock stock = new Stock(
                createStockDto.stockId(),
                createStockDto.description(),
                new ArrayList<>()
        );
        stockRepository.save(stock);
    }
}
