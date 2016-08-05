package com.pubmatic.sm.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        Map<String, Stock> stocks = YahooFinance.get(newSymbols); // single request

        for (Map.Entry<String, Stock> stock : stocks.entrySet())
        {
            StockDTO stockDTO = new StockDTO();
            stockDTO.setStockSymbol(stock.getKey());
            if (isSymbolInvalid(stock.getKey()))
            {
                String invalidValue = new String("-1");
                stockDTO.setCurrentPrice(invalidValue);
                stockDTO.setYearTargetPrice(invalidValue);
                stockDTO.setYearHigh(invalidValue);
                stockDTO.setYearLow(invalidValue);
            }
            else
            {
                stockDTO.setCurrentPrice(stock.getValue().getQuote().getPrice().toString());
                stockDTO.setYearTargetPrice(stock.getValue().getStats().getOneYearTargetPrice().toString());
                stockDTO.setYearHigh(stock.getValue().getQuote().getYearHigh().toString());
                stockDTO.setYearLow(stock.getValue().getQuote().getYearLow().toString());
            }
            LOGGER.info(stockDTO.toString());
            stockDTOs.add(stockDTO);
        }
        return stockDTOs;
    }

    private boolean isSymbolInvalid(String symbolName)
    {
        Pattern pattern = Pattern.compile("[a-zA-Z]*");
        Matcher matcher = pattern.matcher(symbolName);
        return !matcher.matches() ? true : false;
    }

}
