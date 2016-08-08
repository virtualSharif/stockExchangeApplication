package com.pubmatic.sm.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.pubmatic.sm.model.StockDTO;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

/**
 * This is the business service which contains actual logic.
 *
 * @author Sharif Malik
 *
 */

@Component
public class StockExchangeService
{
    private static final String INVALID_VALUE = "-1";

    public List<StockDTO> getInformation(List<String> stockSymbols) throws IOException
    {
        List<StockDTO> stockDTOs = new ArrayList<StockDTO>();
        String[] newSymbols = stockSymbols.toArray(new String[stockSymbols.size()]);
        Map<String, Stock> stocks = new TreeMap<String, Stock>(YahooFinance.get(newSymbols));

        for (Map.Entry<String, Stock> stock : stocks.entrySet())
        {
            StockDTO stockDTO = new StockDTO();
            stockDTO.setStockSymbol(stock.getKey());
            createStockDTO(stock, stockDTO);
            stockDTOs.add(stockDTO);
        }
        return stockDTOs;
    }

    private void createStockDTO(Entry<String, Stock> stock, StockDTO stockDTO)
    {
        if (isStockQuotePriceExists(stock.getValue()))
        {
            stockDTO.setCurrentPrice(stock.getValue().getQuote().getPrice().toString());
            stockDTO.setYearHigh(stock.getValue().getQuote().getYearHigh().toString());
            stockDTO.setYearLow(stock.getValue().getQuote().getYearLow().toString());
            if (isStockStatExists(stock.getValue()))
            {
                stockDTO.setYearTargetPrice(stock.getValue().getStats().getOneYearTargetPrice().toString());

            }
            else
            {
                stockDTO.setYearTargetPrice(INVALID_VALUE);
            }

        }
        else
        {
            createInvalidStockDTO(stockDTO);
        }
    }

    private void createInvalidStockDTO(StockDTO stockDTO)
    {
        stockDTO.setCurrentPrice(INVALID_VALUE);
        stockDTO.setYearTargetPrice(INVALID_VALUE);
        stockDTO.setYearHigh(INVALID_VALUE);
        stockDTO.setYearLow(INVALID_VALUE);
    }

    private boolean isStockQuotePriceExists(Stock stock)
    {
        if (stock.getQuote().getPrice() == null)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    private boolean isStockStatExists(Stock stock)
    {
        if (stock.getStats().getOneYearTargetPrice() == null)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

}
