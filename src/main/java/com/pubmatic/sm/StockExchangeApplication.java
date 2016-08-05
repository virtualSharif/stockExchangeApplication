package com.pubmatic.sm;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pubmatic.sm.business.StockExchangeService;
import com.pubmatic.sm.export.ExportService;
import com.pubmatic.sm.export.reader.FileReaderService;
import com.pubmatic.sm.model.StockDTO;

/**
 * This is the entry point to our Project/Application.
 *
 * Note : reads data from application.yml -- > @Value("${output.filename:null}"
 *
 * @author Sharif Malik
 *
 */

@SpringBootApplication
public class StockExchangeApplication implements CommandLineRunner
{

    @Autowired
    private StockExchangeService stockExchangeService;

    @Autowired
    private ExportService exportService;

    @Autowired
    private FileReaderService fileReaderService;

    @Value("${output.filename:null}")
    private String outputFileName;

    @Value("${input.filename:null}")
    private String inputFileName;

    public static void main(String args[]) throws IOException
    {
        SpringApplication.run(StockExchangeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception
    {
        List<String> stockSymbols = fileReaderService.read(inputFileName);
        List<StockDTO> stockDTOs = stockExchangeService.getInformation(stockSymbols);
        exportService.writeToCSVfile(stockDTOs, outputFileName);
    }

}