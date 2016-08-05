package com.pubmatic.sm.export;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import org.springframework.stereotype.Component;

import com.pubmatic.sm.model.StockDTO;

/**
 * This is the business service which contains actual logic.
 *
 * @author Sharif Malik
 *
 */

@Component
public class ExportService
{
    @SuppressWarnings("resource")
    public void writeToCSVfile(List<StockDTO> stockDTOs, String fileName) throws IOException
    {

        Writer writer = null;
        File outputFile = new File(fileName);
        writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile)));
        if (stockDTOs.isEmpty())
        {
            writer.write("OOPS! No Data to write");
        }
        else
        {
            writer.write("StockSymbol, Current Price, Year Target Price, Year High, Year Low\n");
            StringBuffer stringBuffer = new StringBuffer();
            for (StockDTO stockDTO : stockDTOs)
            {
                stringBuffer.append(stockDTO.getStockSymbol()).append(", ").append(stockDTO.getCurrentPrice().toString()).append(", ").append(stockDTO.getYearTargetPrice().toString()).append(", ")
                        .append(stockDTO.getCurrentPrice().toString()).append(", ").append(stockDTO.getYearLow().toString()).append("\n");
            }
            writer.write(stringBuffer.toString());
        }
        writer.flush();
    }

}
