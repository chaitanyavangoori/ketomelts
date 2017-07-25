package com.ketomelts.main.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;


/**
 * Created by Chaitanya on 12/6/16.
 */
@Entity
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @NotNull
    private String symbol;
    @NotNull
    private String name;
    private Double price;

    public Double getPrice() {
        return price;
    }

    public Long getNumberOfShares() {
        return numberOfShares;
    }

    public Double getMarketValue() {
        return marketValue;
    }

    private Long numberOfShares;
    private Double marketValue;

    public Stock(){}

    public Stock(String symbol, String name, Double price, Long numberOfShares, Double marketValue){
        this.symbol = symbol;
        this.name = name;
        this.price=price;
        this.numberOfShares = numberOfShares;
        this.marketValue = marketValue;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }


    public long getId() {
        return id;
    }

    public void setNumberOfShares(Long numberOfShares) {
        this.numberOfShares = numberOfShares;
    }
}
