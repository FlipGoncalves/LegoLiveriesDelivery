package restapi.tqs.Web;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.Given;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginSteps {
    private WebDriver driver;

    @Given("I am in {string}")
    public void iAmOnThePage(String baseUrl) {
        driver.get(baseUrl);
    }

    @When("I login with {string} and {string}")
    public void iTryToLoginWithAnd(String userName, String password) {
        driver.findElement(By.id("name")).sendKeys(userName);
        driver.findElement(By.id("password")).sendKeys(password);
    }
    @When("I click Sumbit")
    public void iClickSubmit() {
        driver.findElement(By.id("_submit")).click();
    }
    @Then("I should {string}")
    public void iVerify(String type) {
        if (type.equals("logged in")) {
            assertTrue(driver.getCurrentUrl().equals("http://localhost:3000"));
        } else if (type.equals("not logged in")) {
            assertTrue(driver.getCurrentUrl().equals("http://localhost:3000/login"));
            assertEquals(driver.findElement(By.id("error")).getText(), anyString());
        }
    }
}