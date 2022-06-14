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

public class AdditemSteps {
    private WebDriver driver;
    private String baseUrl = "http://localhost:3000";

    @Given("I am in {string}")
    public void iAmOnThePage() {
        driver.get(baseUrl);
    }

    @When("I click on the Lego {string}")
    public void iSelectLego(String name) {
        assertEquals(driver.findElement(By.id(name.replace("\\s+", ""))).getText(), name);
        driver.findElement(By.id(name.replace("\\s+", ""))).click();
    }
    @When("I select the quantity {int} for the lego {string}")
    public void iSelectQtty(int qtty, String name) {
        Select sel = new Select(driver.findElement(By.id("qtty"+name.replace("\\s+", ""))));
        sel.selectByValue(qtty+"");
    }

    @When("I click Submit")
    public void iClickSubmit() {
        driver.findElement(By.id("_submit_cart")).click();
    }
    @Then("I should see the Cart with {int} items")
    public void iVerify(int items) {
        assertEquals(driver.findElement(By.id("cart_length")), items);
    }
}