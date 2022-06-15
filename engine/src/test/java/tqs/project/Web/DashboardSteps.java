package tqs.project.Web;

import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.Given;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DashboardSteps {
    private final FirefoxOptions options = new FirefoxOptions();
    private WebDriver driver;
    private WebElement element;

    @Given("I am in {string}")
    public void iAmOnDashboardPage(String baseUrl) {
        WebDriverManager.firefoxdriver().setup();
        options.setHeadless(true);
        driver = new FirefoxDriver(options);
        
        driver.get(baseUrl);
    }

    @When("I look at the number of {string}")
    public void iTryToLoginWithAnd(String type) {
        element = driver.findElement(By.id(type));
    }

    @When("I click Sumbit")
    public void iClickSubmit() {
        driver.findElement(By.id("_submit")).click();
    }

    @Then("I can see it is not {int}")
    public void iVerifyNotNum(int num) {
        assertNotEquals(element.getText(), num+"");
    }

    @Then("I can see it is {int}")
    public void iVerifyNum(int num) {
        assertEquals(element.getText(), num+"");
    }
}