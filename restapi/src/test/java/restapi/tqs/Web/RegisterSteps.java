package restapi.tqs.Web;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.Given;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterSteps {
    private WebDriver driver;

    @Given("I am in {string}")
    public void iAmOnThePage(String baseUrl) {
        driver.get(baseUrl);
    }

    @When("I register with {string} and {string} and {string}")
    public void iTryToRegisterWithAnd(String userName, String password, String rep_pass) {
        driver.findElement(By.id("name")).sendKeys(userName);
        driver.findElement(By.id("password1")).sendKeys(password);
        driver.findElement(By.id("password2")).sendKeys(rep_pass);
    }
    @When("I click Sumbit")
    public void iClickSubmit() {
        driver.findElement(By.id("_submit")).click();
    }
    @Then("I should {string}")
    public void iVerify(String type) {
        if (type.equals("be registered")) {
            assertTrue(driver.getCurrentUrl().equals("http://localhost:3000"));
        } else if (type.equals("not be registered")) {
            assertTrue(driver.getCurrentUrl().equals("http://localhost:3000/register"));
            assertEquals(driver.findElement(By.id("error")).getText(), anyString());
        }
    }
}