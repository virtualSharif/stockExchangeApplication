package com.pubmatic.sm.model;

import lombok.Data;

/**
 * This is POJO class which can used for Data Transfer Object. Used Lombok for getters , setters, constructors and etc
 *
 * @author Sharif Malik
 *
 */

@Data
public class StockDTO
{
    private String stockSymbol;
    private String currentPrice;
    private String yearTargetPrice;
    private String yearHigh;
    private String yearLow;
}
