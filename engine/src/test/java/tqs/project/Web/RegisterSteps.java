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

public class RegisterSteps {
    private final FirefoxOptions options = new FirefoxOptions();
    private WebDriver driver;

    @Given("I am in {string}")
    public void iAmOnRegisterPage(String baseUrl) {
        WebDriverManager.firefoxdriver().setup();
        options.setHeadless(true);
        driver = new FirefoxDriver(options);
        
        driver.get(baseUrl);
    }

    @When("I register with {string}, {string} and {string}")
    public void iTryToRegisterWithAnd(String userName, String email, String password) {
        driver.findElement(By.id("name")).sendKeys(userName);
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("pass")).sendKeys(password);
    }
    @When("I click Sumbit")
    public void iClickRegister() {
        driver.findElement(By.id("_submit")).click();
    }
    @Then("I should {string}")
    public void iVerifyRegister(String type) {
        if (type.equals("be registered")) {
            assertTrue(driver.getCurrentUrl().equals("http://localhost:3000"));
        } else if (type.equals("not be registered")) {
            assertTrue(driver.getCurrentUrl().equals("http://localhost:3000/sign-up"));
            assertEquals(driver.findElement(By.id("error")).getText(), anyString());
        }
    }
}