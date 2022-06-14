package restapi.tqs.Web;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.Given;
import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyString;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class LoginSteps {
    private WebDriver driver;
    private String baseUrl = "http://localhost:3000";

    @Given("I am in {string}")
    public void iAmOnTheLoginPage() {
        driver.get(baseUrl + "/login");
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
    public void iVerify() {
        if (driver.getCurrentUrl().equals("http://localhost:3000")) {
            System.out.println("logged in");
        } else if (driver.getCurrentUrl().equals("http://localhost:3000/login")) {
            assertEquals(driver.findElement(By.id("error")).getText(), anyString());
            System.out.println("not logged in");
        }
    }
}