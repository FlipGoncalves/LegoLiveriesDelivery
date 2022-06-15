package restapi.tqs.Web;

import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.Given;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;

import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class WebSteps {
    private final FirefoxOptions options = new FirefoxOptions();
    private WebDriver driver;

    @Given("I am in {string}")
    public void iAmOnThePage(String baseUrl) {
        WebDriverManager.firefoxdriver().setup();
        options.setHeadless(true);
        driver = new FirefoxDriver(options);
        
        driver.get(baseUrl);
    }

    @Then("I should see the Cart with {int} items")
    public void iVerify(int num) {
        assertEquals(driver.findElement(By.id("cart_length")).getText(), num);
    }



    // Login Steps
    @When("I login with {string} and {string}")
    public void iTryToLoginWithAnd(String userName, String password) {
        driver.findElement(By.id("name")).sendKeys(userName);
        driver.findElement(By.id("password")).sendKeys(password);
    }
    @When("I click Login")
    public void iClickLogin() {
        driver.findElement(By.id("_submit")).click();
    }
    @Then("I should be logged in")
    public void iVerifyLogin() {
        assertTrue(driver.getCurrentUrl().equals("http://localhost:3000"));
    }
    @Then("I should not be logged in")
    public void iVerifyNotLogin() {
        assertTrue(driver.getCurrentUrl().equals("http://localhost:3000/login"));
        assertEquals(driver.findElement(By.id("error")).getText(), anyString());
    }



    // Register Steps
    @When("I register with {string} and {string} and {string}")
    public void iTryToRegisterWithAnd(String userName, String password, String rep_pass) {
        driver.findElement(By.id("name")).sendKeys(userName);
        driver.findElement(By.id("password1")).sendKeys(password);
        driver.findElement(By.id("password2")).sendKeys(rep_pass);
    }
    @When("I click Register")
    public void iClickRegister() {
        driver.findElement(By.id("_submit")).click();
    }
    @Then("I should be registered")
    public void iVerifyRegister() {
        assertTrue(driver.getCurrentUrl().equals("http://localhost:3000"));
    }
    @Then("I should not be registered")
    public void iVerifyNotRegister() {
        assertTrue(driver.getCurrentUrl().equals("http://localhost:3000/register"));
        assertEquals(driver.findElement(By.id("error")).getText(), anyString());
    }



    // Add item to cart
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
    @When("I click Add item to cart")
    public void iClickAddToCart() {
        driver.findElement(By.id("_submit_cart")).click();
    }



    // Order Steps
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

    @Then("I should see an error message")
    public void iVerifyError() {
        assertEquals(driver.findElement(By.id("error_message")).getText(), anyString());
    }



    // Search by Name / Price
    @When("I choose {string} on the search bar")
    public void iSelectType(String type) {
        Select sel = new Select(driver.findElement(By.id("category")));
        sel.selectByValue(type);
    }

    @When("I search for {string}")
    public void iSearchFor(String search) {
        driver.findElement(By.id("search")).sendKeys(search);
    }

    @When("I click Search")
    public void iClickSubmit() {
        driver.findElement(By.id("_search")).click();
    }

    @Then("I should see the item {string} in my screen")
    public void iVerify(String lego) {
        assertEquals(driver.findElement(By.id("cart_length")).getText(), lego);
    }

    @When("I choose {string} on the dropdown")
    public void i_choose_on_the_dropdown(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
}