package com.pubmatic.sm.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(StockExchangeService.class);

    public List<StockDTO> getInformation(List<String> stockSymbols)
    {
        List<StockDTO> stockDTOs = new ArrayList<StockDTO>();
        String[] newSymbols = stockSymbols.toArray(new String[stockSymbols.size()]);
        Map<String, Stock> stocks = new TreeMap<String, Stock>(YahooFinance.get(newSymbols));

        for (Map.Entry<String, Stock> stock : stocks.entrySet())
        {
            StockDTO stockDTO = new StockDTO();
            stockDTO.setStockSymbol(stock.getKey());
            if (isStockInvalid(stock))
            {
                createInvalidStockDTO(stockDTO);
            }
            else
            {
                createValidStockDTO(stock, stockDTO);
            }
            LOGGER.info(stockDTO.toString());
            stockDTOs.add(stockDTO);
        }
        return stockDTOs;
    }

    private void createValidStockDTO(Map.Entry<String, Stock> stock, StockDTO stockDTO)
    {
        stockDTO.setCurrentPrice(stock.getValue().getQuote().getPrice().toString());
        stockDTO.setYearTargetPrice(stock.getValue().getStats().getOneYearTargetPrice().toString());
        stockDTO.setYearHigh(stock.getValue().getQuote().getYearHigh().toString());
        stockDTO.setYearLow(stock.getValue().getQuote().getYearLow().toString());
    }

    private void createInvalidStockDTO(StockDTO stockDTO)
    {
        String invalidValue = new String("-1");
        stockDTO.setCurrentPrice(invalidValue);
        stockDTO.setYearTargetPrice(invalidValue);
        stockDTO.setYearHigh(invalidValue);
        stockDTO.setYearLow(invalidValue);
    }

    private boolean isStockInvalid(Entry<String, Stock> stock)
    {
        if (stock.getValue().getQuote().getPrice().toString().equals("0"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
