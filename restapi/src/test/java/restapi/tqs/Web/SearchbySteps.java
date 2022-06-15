package restapi.tqs.Web;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.Given;
import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.security.DrbgParameters.NextBytes;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class SearchbySteps {
    private WebDriver driver;
    private String baseUrl = "http://localhost:3000";

    @Given("I am in {string}")
    public void iAmOnThePage() {
        driver.get(baseUrl);
    }

    @When("I choose {string} on the search bar")
    public void iSelectType(String type) {
        Select sel = new Select(driver.findElement(By.id(type)));
        if (type.equals("price")) {
            sel.selectByValue("price");
        } else {
            sel.selectByValue("name");
        }
    }

    @When("I search for {string}")
    public void iSearchFor(String search) {
        driver.findElement(By.id("search_bar")).sendKeys(search);
    }

    @When("I click Search")
    public void iClickSubmit() {
        driver.findElement(By.id("_search")).click();
    }

    @Then("I should see the item {string} in my screen")
    public void iVerify(String lego) {
        assertEquals(driver.findElement(By.id("cart_length")).getText(), lego);
    }
}