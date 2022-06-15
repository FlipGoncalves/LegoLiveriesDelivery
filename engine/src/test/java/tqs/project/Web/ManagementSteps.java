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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class ManagementSteps {
    private final FirefoxOptions options = new FirefoxOptions();
    private WebDriver driver;
    private WebElement element;

    @Given("I am in {string}")
    public void iAmOnManagementPage(String baseUrl) {
        WebDriverManager.firefoxdriver().setup();
        options.setHeadless(true);
        driver = new FirefoxDriver(options);
        
        driver.get(baseUrl);
    }

    @When("I look at the {string} table")
    public void iLookAtTable(String type) {
        element = driver.findElement(By.id(type));
    }

    @Then("I can see there are more than {num}")
    public void iCanSee(int num) {
        System.out.println(element.getText());
        // assertTrue(Integer.parseInt(element.getText()) > num);
    }

    @When("I input my {string}, {string} and {string}")
    public void iInputDataInRider(String name, String email, String pass) {
        driver.findElement(By.id("name")).sendKeys(name);
        driver.findElement(By.id("password")).sendKeys(pass);
        driver.findElement(By.id("email")).sendKeys(email);
    }

    @When("I click Add a Rider")
    public void iAddRider() {
        driver.findElement(By.id("add_rider")).click();
    }

    @Then("I can see the rider {string} was added")
    public void iVerifyRider(String name) {
        driver.findElement(By.id("riders"));
    }
}