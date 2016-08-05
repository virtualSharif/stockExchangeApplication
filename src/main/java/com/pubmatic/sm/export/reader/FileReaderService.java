package com.pubmatic.sm.export.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * This is the business service which contains actual logic.
 *
 * @author Sharif Malik
 *
 */

@Component
public class FileReaderService
{

    @SuppressWarnings("resource")
    public List<String> read(String filename) throws IOException
    {
        BufferedReader br = null;
        List<String> stockSymbols = new ArrayList<String>();
        String sCurrentLine;

        br = new BufferedReader(new FileReader(filename));

        while ((sCurrentLine = br.readLine()) != null)
        {
            stockSymbols.add(sCurrentLine);
        }
        return stockSymbols;
    }
}
