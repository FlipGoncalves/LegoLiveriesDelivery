package restapi.tqs.Web;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.Given;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class OrderSteps {
    private WebDriver driver;

    @Given("I am in {string}")
    public void iAmOnThePage(String baseUrl) {
        driver.get(baseUrl);
    }

    @Given("I add an item to the cart")
    public void iHadItemToCart() {
        driver.findElement(By.className("openmodal")).click();
        driver.findElement(By.id("_submit_cart")).click();
    }

    @When("I click on the cart")
    public void iClickOnCart() {
        driver.findElement(By.id("cart_open")).click();
    }

    @When("I input my {string}, {string}, {string} and {string}")
    public void iSelectType(String street, String city, String country, String code) {
        driver.findElement(By.id("street")).sendKeys(street);
        driver.findElement(By.id("city")).sendKeys(city);
        driver.findElement(By.id("country")).sendKeys(country);
        driver.findElement(By.id("postal")).sendKeys(code);
    }

    @When("I choose {string}")
    public void iSelectTime(String time) {
        Select sel = new Select(driver.findElement(By.id("hour")));
        sel.selectByValue(time);
    }

    @When("I click Order")
    public void iClickOrder() {
        driver.findElement(By.id("_order")).click();
    }

    @Then("I should see the Cart with {int} items")
    public void iVerify(int num) {
        assertEquals(driver.findElement(By.id("cart_length")).getText(), num);
    }

    @Then("I should see an error message")
    public void iVerifyError() {
        assertEquals(driver.findElement(By.id("error_message")).getText(), anyString());
    }
}