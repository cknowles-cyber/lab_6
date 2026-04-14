package com.baarsch_bytes.end2end;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import java.util.logging.Level;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.WebElement;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CourseUITest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void addCourse(String name, String instructor, String maxSize, String room) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-course-name")));

        driver.findElement(By.id("new-course-name")).sendKeys(name);
        driver.findElement(By.id("new-course-instructor")).sendKeys(instructor);
        driver.findElement(By.id("new-course-max-size")).sendKeys(maxSize);
        driver.findElement(By.id("new-course-room")).sendKeys(room);

        driver.findElement(By.xpath("//button[text()='Add Course']")).click();
    }

    @Test
    @Order(1)
    @DisplayName("Create a valid course")
    public void createCourse() {
        driver.get("http://localhost:5173");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-course-list-link")));
        driver.findElement(By.id("nav-course-list-link")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-course-name")));

        addCourse("Computer Science", "Jones Bell", "30", "101");

        WebElement table = driver.findElement(By.id("course-list-table"));

        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.id("course-list-table"),
                "Computer Science"
        ));

        assertTrue(table.getText().contains("Computer Science"));
    }

    @Test
    @Order(2)
    @DisplayName("Create a course with lower bound name")
    public void createLowerBoundCourse() {
        driver.get("http://localhost:5173");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-course-list-link")));
        driver.findElement(By.id("nav-course-list-link")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-course-name")));

        addCourse("C", "Jones Bell", "30", "101");

        WebElement table = driver.findElement(By.id("course-list-table"));

        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.id("course-list-table"),
                "Computer Science"
        ));

        assertTrue(table.getText().contains("C"));
    }

    @Test
    @Order(3)
    @DisplayName("Create a course with upper bound name")
    public void createUpperBoundCourse() {
        driver.get("http://localhost:5173");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-course-list-link")));
        driver.findElement(By.id("nav-course-list-link")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-course-name")));

        String c = "C".repeat(255);
        addCourse(c, "Jones Bell", "30", "101");

        WebElement table = driver.findElement(By.id("course-list-table"));

        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.id("course-list-table"),
                "Computer Science"
        ));

        assertTrue(table.getText().contains(c));
    }

    @Test
    @Order(4)
    @DisplayName("Create a course with lower bound instructor")
    public void createLowerBoundInstructor() {
        driver.get("http://localhost:5173");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-course-list-link")));
        driver.findElement(By.id("nav-course-list-link")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-course-name")));

        addCourse("Computer Science", "J", "30", "101");

        WebElement table = driver.findElement(By.id("course-list-table"));

        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.id("course-list-table"),
                "Computer Science"
        ));

        assertTrue(table.getText().contains("J"));
    }

    @Test
    @Order(5)
    @DisplayName("Create a course with upper bound instructor")
    public void createUpperBoundInstructor() {
        driver.get("http://localhost:5173");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-course-list-link")));
        driver.findElement(By.id("nav-course-list-link")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-course-name")));

        String c = "C".repeat(255);
        addCourse("Computer Science", c, "30", "101");

        WebElement table = driver.findElement(By.id("course-list-table"));

        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.id("course-list-table"),
                c
        ));

        assertTrue(table.getText().contains(c));
    }

    @Test
    @Order(6)
    @DisplayName("Create a course with lower bound max size")
    public void createLowerBoundMazSize() {
        driver.get("http://localhost:5173");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-course-list-link")));
        driver.findElement(By.id("nav-course-list-link")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-course-name")));

        addCourse("Computer Science", "Jones Bell", "1", "101");

        WebElement table = driver.findElement(By.id("course-list-table"));

        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.id("course-list-table"),
                "1"
        ));

        assertTrue(table.getText().contains("C"));
    }

    @Order(7)
    @DisplayName("Create a course with value below lower bound max size")
    public void createBelowLowerBoundMaxSize() {
        driver.get("http://localhost:5173");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-course-list-link")));
        driver.findElement(By.id("nav-course-list-link")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-course-name")));

        int orig_size = driver.findElements(By.cssSelector("[id^='course-row-']")).size();

        addCourse("Computer Science", "Jones Bell", "-1", "105");

        int updated_size = driver.findElements(By.cssSelector("[id^='course-row-']")).size();

        WebElement table = driver.findElement(By.id("course-list-table"));

        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.id("course-list-table"),
                "105"
        ));

        assertEquals(orig_size, updated_size);
    }

    @Test
    @Order(8)
    @DisplayName("Create a course with lower bound room")
    public void createLowerBoundRoom() {
        driver.get("http://localhost:5173");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-course-list-link")));
        driver.findElement(By.id("nav-course-list-link")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-course-name")));

        addCourse("Computer Science", "Jones Bell", "30", "L");

        WebElement table = driver.findElement(By.id("course-list-table"));

        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.id("course-list-table"),
                "L"
        ));

        assertTrue(table.getText().contains("L"));
    }

    @Test
    @Order(8)
    @DisplayName("Create a course with upper bound room")
    public void createUpperBoundRoom() {
        driver.get("http://localhost:5173");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-course-list-link")));
        driver.findElement(By.id("nav-course-list-link")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-course-name")));

        String l = "L".repeat(255);
        addCourse("Computer Science", "Jones Bell", "30", l);

        WebElement table = driver.findElement(By.id("course-list-table"));

        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.id("course-list-table"),
                l
        ));

        assertTrue(table.getText().contains(l));
    }

    @Order(8)
    @DisplayName("Create a course with missing name")
    public void createCourseMissingName() {
        driver.get("http://localhost:5173");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-course-list-link")));
        driver.findElement(By.id("nav-course-list-link")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-course-name")));

        int orig_size = driver.findElements(By.cssSelector("[id^='course-row-']")).size();

        addCourse("", "Jones Bell", "30", "104");

        int updated_size = driver.findElements(By.cssSelector("[id^='course-row-']")).size();

        WebElement table = driver.findElement(By.id("course-list-table"));

        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.id("course-list-table"),
                "104"
        ));

        assertEquals(orig_size, updated_size);
    }

    @Order(9)
    @DisplayName("Create a course with missing instructor")
    public void createCourseMissingInstructor() {
        driver.get("http://localhost:5173");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-course-list-link")));
        driver.findElement(By.id("nav-course-list-link")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-course-name")));

        int orig_size = driver.findElements(By.cssSelector("[id^='course-row-']")).size();

        addCourse("Computer Science", "", "30", "103");

        int updated_size = driver.findElements(By.cssSelector("[id^='course-row-']")).size();

        WebElement table = driver.findElement(By.id("course-list-table"));

        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.id("course-list-table"),
                "103"
        ));

        assertEquals(orig_size, updated_size);
    }

    @Order(9)
    @DisplayName("Create a course with missing maxSize")
    public void createCourseMissingMaxSize() {
        driver.get("http://localhost:5173");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-course-list-link")));
        driver.findElement(By.id("nav-course-list-link")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-course-name")));

        int orig_size = driver.findElements(By.cssSelector("[id^='course-row-']")).size();

        addCourse("Computer Science", "Jones Bell", "", "70");

        int updated_size = driver.findElements(By.cssSelector("[id^='course-row-']")).size();

        WebElement table = driver.findElement(By.id("course-list-table"));

        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.id("course-list-table"),
                "70"
        ));

        assertEquals(orig_size, updated_size);
    }

    @Order(10)
    @DisplayName("Create a course with missing room")
    public void createCourseMissingRoom() {
        driver.get("http://localhost:5173");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-course-list-link")));
        driver.findElement(By.id("nav-course-list-link")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-course-name")));

        int orig_size = driver.findElements(By.cssSelector("[id^='course-row-']")).size();

        addCourse("Computer Science", "Jones Bell", "35", "");

        int updated_size = driver.findElements(By.cssSelector("[id^='course-row-']")).size();

        WebElement table = driver.findElement(By.id("course-list-table"));

        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.id("course-list-table"),
                "35"
        ));

        assertEquals(orig_size, updated_size);
    }

}