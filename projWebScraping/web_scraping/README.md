# Web Scraping The _Continente_ Website For Lego Sets

The purpose of this small project is to obtain data to use in the client backend of the "legoliveries" project. To interact with the internet and collect the data needed, we used Selenium. _Continente_ is a portuguese retail chain owned by "Sonae Distribuição" and has hypermarkets all over continental Portugal as well as Azores and Madeira.

## How it works

 - We open a WebDriver and go to "https://www.continente.pt/pesquisa/?q=lego&start=0", which is the _Continente_ page after searching __lego__
 - Since when using the WebDriver to interact with the website we are presented with a _accept cookies_ popup, we wait 5 seconds for the popup to load, click the __accept cookies__ button and wait 5 seconds for the popup to disappear
 - We then click on the __Ver mais produtos__, which in english means __See more products__, a number of times defined by us. Each page had 24 products so we clicked this button 9 times to have 240 products in total.
 - After loading all the products, we get all the elements (in which an element corresponds to a product)
 - For each product, we get the image, name and price, put them in a __LegoSet__ object and add this object to an ArrayList
 - We then use _GSON_ library to convert our list of __LegoSet__ objects into a json and write this json to the file __legoList.json__
 - Finally we flush and close the writer and close the driver

## How to run

Run the following command

        mvn compile exec:java -Dexec.mainClass="pt.ua.tqs.project.App"

## Related links

- Continente website : https://www.continente.pt/
- GSON library : https://github.com/google/gson