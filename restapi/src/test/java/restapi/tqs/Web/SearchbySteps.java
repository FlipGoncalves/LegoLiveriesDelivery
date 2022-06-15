package restapi.tqs.Web;

import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.Given;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.Select;

public class SearchbySteps {
    private final FirefoxOptions options = new FirefoxOptions();
    private WebDriver driver;

    @Given("I am in {string}")
    public void iAmOnThePage(String baseUrl) {
        WebDriverManager.firefoxdriver().setup();
        options.setHeadless(true);
        driver = new FirefoxDriver(options);
        
        driver.get(baseUrl);
    }

    @When("I choose {string} on the search bar")
    public void iSelectType(String type) {
        Select sel = new Select(driver.findElement(By.id("category")));
        sel.selectByValue(type);
    }

    @When("I search for {string}")
    public void iSearchFor(String search) {
        driver.findElement(By.id("search")).sendKeys(search);
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