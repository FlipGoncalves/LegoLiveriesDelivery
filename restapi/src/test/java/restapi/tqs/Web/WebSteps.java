package restapi.tqs.Web;

import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.Given;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.TimeoutException;

public class WebSteps {
    private final FirefoxOptions options = new FirefoxOptions();
    private WebDriver driver;
    private WebDriverWait wait;

    @Given("I am in {string}")
    public void iAmOnThePage(String baseUrl) {
        WebDriverManager.chromedriver().setup();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        
        driver.get(baseUrl);
    }

    @Then("I should see the Cart with {string} items")
    public void iVerifyCart(String num) {
        assertEquals(driver.findElement(By.id("cart_length")).getAttribute("innerHTML"), num);
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
    @Then("I should not be logged in")
    public void iVerifyNotLogin() {
        assertTrue(driver.getCurrentUrl().equals("http://localhost:3000/login"));
        assertEquals(driver.findElement(By.id("error")).getAttribute("innerHTML"), "ERROR during log in");
    }



    // Register Steps
    @When("I register with {string}, {string}, {string} and {string}")
    public void iTryToRegisterWithAnd(String userName, String email, String password, String rep_pass) {
        driver.findElement(By.id("name")).sendKeys(userName);
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("password1")).sendKeys(password);
        driver.findElement(By.id("password2")).sendKeys(rep_pass);
    }
    @When("I click Register")
    public void iClickRegister() {
        driver.findElement(By.id("_submit")).click();
    }
    @Then("I should be registered")
    public void iVerifyRegister() {
        System.out.println(driver.getCurrentUrl());
        try {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.urlToBe("http://localhost:3000/"));
        } catch(TimeoutException e) {
            System.err.println(e);
            if (driver.findElement(By.id("error")).isDisplayed()) {
                System.out.println("Error message is visible ? " + driver.findElement(By.id("error")).getAttribute("innerHTML"));
            }
        }
        System.out.println(driver.getCurrentUrl());
        assertTrue(driver.getCurrentUrl().equals("http://localhost:3000/"));
    }


    // Add item to cart
    @When("I click on the Lego {string}")
    public void iSelectLego(String name) {
        try {
            wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div/section[2]/div/div[1]")));
        } catch(TimeoutException e) {
            System.err.println(e);
        }

        driver.findElement(By.id(name.replaceAll("\\s+", "")+"ID")).click();
    }
    @When("I select the quantity {string} for the lego {string}")
    public void iSelectQtty(String qtty, String name) {
        Select sel = new Select(driver.findElement(By.id("qtty"+name.replaceAll("\\s+", ""))));
        sel.selectByValue(qtty+"");
    }
    @When("I click Add item to cart")
    public void iClickAddToCart() {
        driver.findElement(By.id("_submit_cart")).click();
    }



    // Order Steps
    @Given("I add an item to the cart")
    public void iHadItemToCart() {
        iSelectLego("Lego Test");
        iSelectQtty("1", "Lego Test");
        iClickAddToCart();
    }

    @When("I click on the cart")
    public void iClickOnCart() {

        if (! driver.findElement(By.id("cart_open")).isDisplayed()) {
            iClickAddToCart();
            wait = new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cart_open")));
        }

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.id("cart_open")));

        driver.findElement(By.id("cart_open")).click();
    }

    @When("I input the street: {string}, the city: {string}, the country: {string}, the postal code: {string}, the latitude: {string} and the longitude: {string}")
    public void iSelectType(String street, String city, String country, String code, String latit, String longit) {
        driver.findElement(By.id("street")).sendKeys(street);
        driver.findElement(By.id("city")).sendKeys(city);
        driver.findElement(By.id("country")).sendKeys(country);
        driver.findElement(By.id("postal")).sendKeys(code);
        driver.findElement(By.id("latit")).sendKeys(latit);
        driver.findElement(By.id("longit")).sendKeys(longit);
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
        assertEquals(driver.findElement(By.id("error_message")).getText(), "Please add items to your cart");
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
        String type;
        if (lego.matches("\\d+\\.\\d+")) {
            type = "div";
        } else {
            type = "a";
        }

        try {
            wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div/section[2]/div/div[1]")));
        } catch(TimeoutException e) {
            System.err.println(e);
        }
        
        assertEquals(driver.findElement(By.xpath("/html/body/div/div/div/section[2]/div/div[1]/div[1]/div/button/figcaption/"+type)).getAttribute("innerHTML"), lego);
    }
}