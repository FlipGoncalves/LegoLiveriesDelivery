package tqs.project.Web;

import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.Given;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class LoginSteps {
    private final FirefoxOptions options = new FirefoxOptions();
    private WebDriver driver;

    @Given("I am in {string}")
    public void iAmOnLoginPage(String baseUrl) {
        WebDriverManager.firefoxdriver().setup();
        options.setHeadless(true);
        driver = new FirefoxDriver(options);
        
        driver.get(baseUrl);
    }

    @When("I login with {string} and {string}")
    public void iTryToLoginWithAnd(String email, String password) {
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("pass")).sendKeys(password);
    }
    @When("I click Sumbit")
    public void iClickLogin() {
        driver.findElement(By.id("signin")).click();
    }
    @Then("I should {string}")
    public void iVerifyLogin(String type) {
        if (type.equals("be logged in")) {
            assertTrue(driver.getCurrentUrl().equals("http://localhost:3000"));
        } else if (type.equals("not be logged in")) {
            assertTrue(driver.getCurrentUrl().equals("http://localhost:3000/login"));
            assertEquals(driver.findElement(By.id("error")).getText(), anyString());
        }
    }
}
