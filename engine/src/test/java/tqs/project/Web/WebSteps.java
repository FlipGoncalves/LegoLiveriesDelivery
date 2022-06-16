package tqs.project.Web;

import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.Given;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.JavascriptExecutor;

public class WebSteps {
    private final FirefoxOptions options = new FirefoxOptions();
    private WebDriver driver;
    private WebElement element;

    @Given("I am in {string}")
    public void iAmOnThePage(String baseUrl) {
        WebDriverManager.firefoxdriver().setup();
        options.setHeadless(true);
        driver = new FirefoxDriver(options);
        
        driver.get(baseUrl);
    }

    // Management Steps
    @When("I look at the {string} table")
    public void iLookAtTable(String type) {
        driver.findElement(By.id(type));
    }

    @Then("I can see there are more than {string} {string}")
    public void iCanSee(String num, String table) {

        WebElement baseTable = driver.findElement(By.id(table));
        List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));

        assertTrue(tableRows.size() - 1 > Integer.parseInt(num));
    }

    @When("I input my {string}, {string} and {string}")
    public void iInputDataInRider(String name, String email, String pass) {
        driver.findElement(By.id("name")).sendKeys(name);
        driver.findElement(By.id("password")).sendKeys(pass);
        driver.findElement(By.id("email")).sendKeys(email);
    }

    @When("I click Add a Rider")
    public void iAddRider() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.id("add_rider")));
        driver.findElement(By.id("add_rider")).click();
    }

    @Then("I can see the rider {string} was added")
    public void iVerifyRider(String name) {
        WebElement baseTable = driver.findElement(By.id("riders"));
        List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));

        assertEquals(tableRows.get(tableRows.size()-1).getText(), name);
    }



    // Login Steps
    @When("I login with {string} and {string}")
    public void iTryToLoginWithAnd(String email, String password) {
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("pass")).sendKeys(password);
    }
    @When("I click Login")
    public void iClickLogin() {
        driver.findElement(By.id("signin")).click();
    }
    @Then("I should be logged in")
    public void iVerifyLogin() {

        // engine connection not working //

        assertTrue(driver.getCurrentUrl().equals("http://localhost:3001"));
    }
    @Then("I should not be logged in")
    public void iVerifyNotLogin() {
        assertTrue(driver.getCurrentUrl().equals("http://localhost:3001/sign-in"));
        assertEquals(driver.findElement(By.id("error")).getText(), "ERROR during sign in");
    }



    // Register Steps
    @When("I register with {string}, {string} and {string}")
    public void iTryToRegisterWithAnd(String userName, String email, String password) {
        driver.findElement(By.id("name")).sendKeys(userName);
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("pass")).sendKeys(password);
    }
    @When("I click Register")
    public void iClickRegister() {
        driver.findElement(By.id("_submit")).click();
    }
    @Then("I should be registered")
    public void iVerifyRegister() {

        // engine connection not working //

        assertTrue(driver.getCurrentUrl().equals("http://localhost:3001"));
    }

    @Then("I should not be registered")
    public void iVerifyNotRegister() {
        assertTrue(driver.getCurrentUrl().equals("http://localhost:3001/sign-up"));
        assertEquals(driver.findElement(By.id("error")).getText(), "ERROR during sign up");
    }

    

    // Dashboard Steps
    @When("I look at the number of {string}")
    public void iTryToLoginWithAnd(String type) {
        element = driver.findElement(By.id(type));
    }

    @Then("I can see it is not {string}")
    public void iVerifyNotNum(String num) {
        assertNotEquals(element.getText(), num);
    }

    @Then("I can see it is {string}")
    public void iVerifyNum(String num) {
        assertEquals(element.getText(), num);
    }


}