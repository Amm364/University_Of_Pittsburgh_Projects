package com.example.tests;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class TestCases {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "https://cs1632ex.herokuapp.com/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testCathedralPicsOrderedList() throws Exception {
        driver.get(baseUrl + "/cathy");
        driver.findElement(By.linkText("Cathedral Pics")).click();
        assertTrue(isElementPresent(By.cssSelector("ol > li")));
    }

    @Test
    public void testFactorialResult() throws Exception {
        driver.get(baseUrl + "/fib");
        driver.findElement(By.linkText("Factorial")).click();
        driver.findElement(By.name("value")).clear();
        driver.findElement(By.name("value")).sendKeys("5");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        assertEquals("Factorial of 5 is 120!", driver.findElement(By.cssSelector("h2")).getText());
    }

    @Test
    public void testFactorialResult1() throws Exception {
        driver.get(baseUrl + "/fact");
        driver.findElement(By.linkText("Factorial")).click();
        driver.findElement(By.name("value")).clear();
        driver.findElement(By.name("value")).sendKeys("1");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        assertEquals("Factorial of 1 is 1!", driver.findElement(By.cssSelector("h2")).getText());
    }

    @Test
    public void testFactorialResult100() throws Exception {
        driver.get(baseUrl + "/fact");
        driver.findElement(By.linkText("Factorial")).click();
        driver.findElement(By.name("value")).clear();
        driver.findElement(By.name("value")).sendKeys("100");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        try {
            assertEquals("Factorial of 100 is 93326215443944152681699238856266700490715968264381621468592963895217599993229915608941463976156518286253697920827223758251185210916864000000000000000000000000!", driver.findElement(By.cssSelector("h2")).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
    }

    @Test
    public void testFactorialResultDecimal() throws Exception {
        driver.get(baseUrl + "/fact");
        driver.findElement(By.linkText("Factorial")).click();
        driver.findElement(By.name("value")).clear();
        driver.findElement(By.name("value")).sendKeys("5.1");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        assertEquals("Factorial of 5.1 is 1!", driver.findElement(By.cssSelector("h2")).getText());
    }

    @Test
    public void testFactorialResultGreaterThan100() throws Exception {
        driver.get(baseUrl + "/fact");
        driver.findElement(By.linkText("Factorial")).click();
        driver.findElement(By.name("value")).clear();
        driver.findElement(By.name("value")).sendKeys("101");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        assertEquals("Factorial of 101 is 1!", driver.findElement(By.cssSelector("h2")).getText());
    }

    @Test
    public void testFactorialResultLessThan1() throws Exception {
        driver.get(baseUrl + "/factres/?value=101");
        driver.findElement(By.linkText("Factorial")).click();
        driver.findElement(By.name("value")).clear();
        driver.findElement(By.name("value")).sendKeys("-1");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        assertEquals("Factorial of -1 is 1!", driver.findElement(By.cssSelector("h2")).getText());
    }

    @Test
    public void testFibonacciResult() throws Exception {
        driver.get(baseUrl + "/fib");
        driver.findElement(By.linkText("Fibonacci")).click();
        driver.findElement(By.id("tb1")).clear();
        driver.findElement(By.id("tb1")).sendKeys("5");
        driver.findElement(By.id("sub")).click();
        assertEquals("Fibonacci of 5 is 8!", driver.findElement(By.cssSelector("h2")).getText());
    }

    @Test
    public void testFibonacciResultDecimal() throws Exception {
        driver.get(baseUrl + "/fib");
        driver.findElement(By.linkText("Fibonacci")).click();
        driver.findElement(By.id("tb1")).clear();
        driver.findElement(By.id("tb1")).sendKeys("10.1");
        driver.findElement(By.id("sub")).click();
        assertEquals("Fibonacci of 10.1 is 1!", driver.findElement(By.cssSelector("h2")).getText());
    }

    @Test
    public void testFibonacciResultEdgeCase1() throws Exception {
        driver.get(baseUrl + "/fib");
        driver.findElement(By.linkText("Fibonacci")).click();
        driver.findElement(By.id("tb1")).clear();
        driver.findElement(By.id("tb1")).sendKeys("1");
        driver.findElement(By.id("sub")).click();
        assertEquals("Fibonacci of 1 is 1!", driver.findElement(By.cssSelector("h2")).getText());
    }

    @Test
    public void testFibonacciResultEdgeCase100() throws Exception {
        driver.get(baseUrl + "/fib");
        driver.findElement(By.linkText("Fibonacci")).click();
        driver.findElement(By.id("tb1")).clear();
        driver.findElement(By.id("tb1")).sendKeys("100");
        driver.findElement(By.id("sub")).click();
        assertEquals("Fibonacci of 100 is 354224848179261915075!", driver.findElement(By.cssSelector("h2")).getText());
    }

    @Test
    public void testHelloAddedName() throws Exception {
        driver.get("https://cs1632ex.herokuapp.com/hello/Alex");
        assertEquals("Hello CS1632, from Alex!", driver.findElement(By.cssSelector("h2")).getText());
    }

    @Test
    public void testHelloAddedPercentName() throws Exception {
        driver.get("https://cs1632ex.herokuapp.com/hello/%Alex");
        assertEquals("Hello CS1632, from %Alex!", driver.findElement(By.cssSelector("h2")).getText());
    }

    @Test
    public void testHelloTextNoArguments() throws Exception {
        driver.get(baseUrl + "/hello");
        driver.findElement(By.linkText("Hello")).click();
        assertEquals("Hello CS1632, from Prof. Laboon!", driver.findElement(By.cssSelector("h2")).getText());
    }

    @Test
    public void testHomePageLaboonText() throws Exception {
        driver.get(baseUrl + "/");
        driver.findElement(By.linkText("CS1632 D3 Home")).click();
        try {
            assertEquals("Used for CS1632 Software Quality Assurance, taught by Bill Laboon", driver.findElement(By.cssSelector("div.row > p.lead")).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
    }

    @Test
    public void testHomePageWelcomeText() throws Exception {
        driver.get(baseUrl + "/");
        driver.findElement(By.linkText("CS1632 D3 Home")).click();
        try {
            assertTrue(driver.findElement(By.cssSelector("p.lead")).getText().matches("^Welcome, friend,\nto a land of pure calculation\\.[\\s\\S]*$"));
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
    }

    @Test
    public void testVerifyCathedralPicsPageLink() throws Exception {
        driver.get(baseUrl + "/cathy");
        assertEquals("CS1632 D3 Home", driver.findElement(By.linkText("CS1632 D3 Home")).getText());
        assertEquals("Factorial", driver.findElement(By.linkText("Factorial")).getText());
        assertEquals("Fibonacci", driver.findElement(By.linkText("Fibonacci")).getText());
        assertEquals("Hello", driver.findElement(By.linkText("Hello")).getText());
        assertEquals("Cathedral Pics", driver.findElement(By.linkText("Cathedral Pics")).getText());
    }

    @Test
    public void testVerifyFactorialPageLink() throws Exception {
        driver.get(baseUrl + "/fact");
        assertEquals("CS1632 D3 Home", driver.findElement(By.linkText("CS1632 D3 Home")).getText());
        assertEquals("Factorial", driver.findElement(By.linkText("Factorial")).getText());
        assertEquals("Fibonacci", driver.findElement(By.linkText("Fibonacci")).getText());
        assertEquals("Hello", driver.findElement(By.linkText("Hello")).getText());
        assertEquals("Cathedral Pics", driver.findElement(By.linkText("Cathedral Pics")).getText());
    }

    @Test
    public void testVerifyFibonacciPageLink() throws Exception {
        driver.get(baseUrl + "/fib");
        assertEquals("CS1632 D3 Home", driver.findElement(By.linkText("CS1632 D3 Home")).getText());
        assertEquals("Factorial", driver.findElement(By.linkText("Factorial")).getText());
        assertEquals("Fibonacci", driver.findElement(By.linkText("Fibonacci")).getText());
        assertEquals("Hello", driver.findElement(By.linkText("Hello")).getText());
        assertEquals("Cathedral Pics", driver.findElement(By.linkText("Cathedral Pics")).getText());
    }

    @Test
    public void testVerifyHelloPageLink() throws Exception {
        driver.get(baseUrl + "/hello");
        assertEquals("CS1632 D3 Home", driver.findElement(By.linkText("CS1632 D3 Home")).getText());
        assertEquals("Factorial", driver.findElement(By.linkText("Factorial")).getText());
        assertEquals("Fibonacci", driver.findElement(By.linkText("Fibonacci")).getText());
        assertEquals("Hello", driver.findElement(By.linkText("Hello")).getText());
        assertEquals("Cathedral Pics", driver.findElement(By.linkText("Cathedral Pics")).getText());
    }

    @Test
    public void testVerifyHomePageLink() throws Exception {
        driver.get(baseUrl + "/");
        assertEquals("CS1632 D3 Home", driver.findElement(By.linkText("CS1632 D3 Home")).getText());
        assertEquals("Factorial", driver.findElement(By.linkText("Factorial")).getText());
        assertEquals("Fibonacci", driver.findElement(By.linkText("Fibonacci")).getText());
        assertEquals("Hello", driver.findElement(By.linkText("Hello")).getText());
        assertEquals("Cathedral Pics", driver.findElement(By.linkText("Cathedral Pics")).getText());
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
