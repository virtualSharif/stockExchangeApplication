Test for Pubmatic  @Author : Sharif Malik
============================
Note : Solution is solved using
i.	Spring Tool Suite,
ii.	Spring Framework especially Spring Dependency Injection
iii.Maven for building the project.
iv. Lombok for auto creation of setters, getters, constructors, and etc
v. 	YahooFinance (http://financequotes-api.com/) for accessing the YahooFinance Stock rates.

This is a Java command line tool that transforms the result data into a CSV file.

```java -jar target/stock-exchange-application.jar ```

Output last lines :

c.pubmatic.sm.StockExchangeApplication   : Started StockExchangeApplication in 3.963 seconds (JVM running for 4.401)

Expected :
To be executed within 30 seconds.


### Instructions

-Spring Tool Suite / Eclipse users can import project by using the option of ,,Import Existing maven project" from the file system.

-To run the application using maven build use below command:
`clean install spring-boot:run`

Note : Output file name is taken from application.yml (i.e stock-information-output.csv)

### Exception thrown at runtime
Currently application is also throwing the exception while running the application :
i.e NumberFormatException in StockQuotesData.getStats() function #52

Hence, its is from the YahooFinance Library. I have created the issue in their github project.

###https://github.com/sstrickx/yahoofinance-api/issues/52


Once this issue is fixed. I will update project respectively.

