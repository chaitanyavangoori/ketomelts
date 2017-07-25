package com.ketomelts.main.services;

import com.ketomelts.main.domain.Stock;
import com.ketomelts.main.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Chaitanya on 12/6/16.
 */
@Service
public interface StockService {
    List<Stock> savePortfolio(List<Stock> stocks, StockRepository stockRepository);

    List<Stock> getSavedPortfolios(StockRepository stockRepository);

    Stock updateStock(Stock stock, StockRepository stockRepository);

    List<Stock> deleteStock(Long id, StockRepository stockRepository);
}
