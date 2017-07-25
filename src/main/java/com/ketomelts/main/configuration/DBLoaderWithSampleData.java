package com.ketomelts.main.configuration;

import com.ketomelts.main.domain.Stock;
import com.ketomelts.main.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chaitanya on 12/7/16.
 * This class is created to set up db sample data
 */

@Component
public class DBLoaderWithSampleData implements CommandLineRunner{
    private StockRepository stockRepository;

    @Autowired
    public DBLoaderWithSampleData(StockRepository stockRepository){
        this.stockRepository = stockRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        List<Stock> stocks = new ArrayList<>();
        stocks.add(new Stock("GOOGL", "Alphabet, Inc.", null, null, null));
        stocks.add(new Stock("GS", "Goldman Sachs Group Inc", null, new Long(10), null));
        stocks.add(new Stock("JPM", "JPMORGAN CHASE &CO", null, null, null));
        stocks.add(new Stock("INTC", "Intel Corporation", null, new Long(4), null));
        stocks.add(new Stock("AAPL", "Apple Inc.", null, new Long(22), null));
        stockRepository.save(stocks);
    }
}
