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

        driver.findElement(By.id("add-course-button")).click();
    }

    @Test
    @Order(1)
    @DisplayName("Create a course")
    public void createCourse() {
        driver.get("http://localhost:5173");

        wait.until(ExpectedConditions.elementToBeClickable(
                By.id("courses-button")
        )).click();

        addCourse("Computer Science", "Jones Bell", "30", "101");

        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.id("course-list-table"),
                "Computer Science"
        ));

        WebElement table = driver.findElement(By.id("course-list-table"));
        assertTrue(table.getText().contains("Computer Science"));
    }
}