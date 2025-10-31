package com.agragadorinvestiemnto.agragadorinvestimentos.Controllers;

import com.agragadorinvestiemnto.agragadorinvestimentos.DTOS.CreateStockDto;
import com.agragadorinvestiemnto.agragadorinvestimentos.DTOS.UserRequestDTO;
import com.agragadorinvestiemnto.agragadorinvestimentos.DTOS.UserResponseDTO;
import com.agragadorinvestiemnto.agragadorinvestimentos.Service.StockService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/stocks")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping
    public ResponseEntity<Void> createStock(@Valid @RequestBody CreateStockDto createStockDto){
        stockService.createStock(createStockDto);
        return ResponseEntity.ok().build();
    }
}
