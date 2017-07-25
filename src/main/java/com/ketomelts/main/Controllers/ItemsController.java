package com.ketomelts.main.Controllers;

import com.ketomelts.main.domain.Stock;
import com.ketomelts.main.repository.StockRepository;
import com.ketomelts.main.services.StockService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Chaitanya on 12/6/16.
 * This Class is created to handle all portfolio related rest requests
 */
@Controller
@RequestMapping(value= "/user")
public class ItemsController {

    @Autowired
    private StockService stockService;

    StockRepository stockRepository;

    @Autowired
    public ItemsController(StockRepository stockRepository){
        this.stockRepository = stockRepository;
    }

//Saves list of portfolios
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity savePortfolio(@RequestBody List<Stock> stocks){
        List<Stock> list =  stockService.savePortfolio(stocks, stockRepository);
        for (Stock stock: list){
            System.out.println("list returned: "+ stock.getName());
        }
        //To escape special characters when serializing as JSON
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return ResponseEntity.ok(gson.toJson(list));
    }

    //get all saved portfolios
    @RequestMapping(value = "/savedPortfolios", method = RequestMethod.GET)
    public  @ResponseBody ResponseEntity getSavedPortfolios(){
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return ResponseEntity.ok(gson.toJson(stockService.getSavedPortfolios(stockRepository)));
    }

    //update existing portfolio by selecting number of stocks
    @RequestMapping(value = "/updatePortfolio", method = RequestMethod.PUT)
    public  @ResponseBody ResponseEntity updatePortfolio(@RequestBody Stock stock){
        try {
            stockService.updateStock(stock, stockRepository);
        }
        catch (Exception e){
            //Should put Logger instead
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok().build();
    }
//deletes existing portfolio
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity deletePortfolio(@PathVariable Long id){
        try {
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            return ResponseEntity.ok(gson.toJson(stockService.deleteStock(id, stockRepository)));
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
