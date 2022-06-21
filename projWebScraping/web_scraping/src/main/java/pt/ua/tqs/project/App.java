package pt.ua.tqs.project;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class App 
{
    public static void main( String[] args ) throws JsonIOException, IOException
    {
        String url = "https://www.continente.pt/pesquisa/?q=lego&start=0";
        WebDriver driver = WebDriverManager.chromedriver().create();
        driver.get(url);

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        driver.findElement(By.id("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll")).click();
        
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int numberOfPages = 10;

        for(int i = 0; i < numberOfPages - 1; i++){
            System.out.println("Click number " + i);
            driver.findElement(By.cssSelector(".search-view-more-products-button")).click();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        List<WebElement> products = driver.findElements(By.cssSelector(".ct-inner-tile-wrap"));
        
        ArrayList<LegoSet> legoList = new ArrayList<>();
        String imageUrl = "", name = "", price = "";
        LegoSet lego = new LegoSet();
        for (WebElement webElement : products) {
            imageUrl = webElement.findElement(By.cssSelector(".ct-image-container")).findElement(By.tagName("a")).findElement(By.tagName("picture")).findElement(By.tagName("img")).getAttribute("src");
            name = webElement.findElement(By.cssSelector(".ct-tile-body")).findElement(By.cssSelector(".ct-pdp-details")).findElement(By.cssSelector(".ct-pdp-link")).findElement(By.tagName("a")).getText();
            price = webElement.findElement(By.cssSelector(".ct-tile-body")).findElement(By.cssSelector(".ct-tile-bottom")).findElement(By.cssSelector(".ct-price-wrap")).findElement(By.cssSelector(".js-product-price")).findElement(By.cssSelector(".prices-wrapper")).findElement(By.tagName("span")).findElement(By.tagName("span")).getAttribute("content");
            lego = new LegoSet(imageUrl, name, price);
            legoList.add(lego);
        }

        /*for (LegoSet l : legoList) {
            System.out.println("------------------------");
            System.out.println(l.toString());
            System.out.println("------------------------");
        }

        System.out.println("Size: " + legoList.size());*/

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Writer writer = new FileWriter("legoList.json");
        gson.toJson(legoList, writer);
        writer.flush();
        writer.close();
        driver.close();
        return;
    }
}
