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
public class StudentUITest {
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

    private void createStudent(String name, String major, String gpa) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-student-name")));

        driver.findElement(By.id("new-student-name")).sendKeys(name);
        driver.findElement(By.id("new-student-major")).sendKeys(major);
        driver.findElement(By.id("new-student-gpa")).sendKeys(gpa);

        driver.findElement(By.id("add-student-button")).click();
    }

    @Test
    @Order(1)
    @DisplayName("Create a valid student")
    public void createValidStudent() {
        driver.get("http://localhost:5173/students");

        createStudent("John Smith", "Computer Science", "3.8");

        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("student-list-table"), "John Smith"));

        WebElement table = driver.findElement(By.id("student-list-table"));
        assertTrue(table.getText().contains("John Smith"));

    }

    @Test
    @Order(2)
    @DisplayName("Create student with lower bound GPA")
    public void createValidStudentLowerBoundGPA() {
        driver.get("http://localhost:5173/students");

        createStudent("John Smith", "Computer Science", "0");

        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("student-list-table"), "0"));

        WebElement table = driver.findElement(By.id("student-list-table"));
        assertTrue(table.getText().contains("0"));

    }

    @Test
    @Order(3)
    @DisplayName("Create student with upper bound GPA")
    public void createValidStudentUpperBoundGPA() {
        driver.get("http://localhost:5173/students");

        createStudent("John Smith", "Computer Science", "4");

        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("student-list-table"), "4"));

        WebElement table = driver.findElement(By.id("student-list-table"));
        assertTrue(table.getText().contains("4"));

    }

    @Test
    @Order(4)
    @DisplayName("Create student with value below lower bound GPA")
    public void createStudentBelowLowerBoundGPA() {
        driver.get("http://localhost:5173/students");

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("[id^='student-row-']")
        ));

        int orig_size = driver.findElements(By.cssSelector("[id^='student-row-']")).size();

        createStudent("John Smith", "Computer Science", "-0.1");

        int updated_size = driver.findElements(By.cssSelector("[id^='student-row-']")).size();

        assertEquals(orig_size, updated_size);
    }

    @Test
    @Order(5)
    @DisplayName("Create student with value above upper bound GPA")
    public void createStudentAboveUpperBoundGPA() {
        driver.get("http://localhost:5173/students");

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("[id^='student-row-']")
        ));

        int orig_size = driver.findElements(By.cssSelector("[id^='student-row-']")).size();

        createStudent("John Smith", "Computer Science", "4.1");

        int updated_size = driver.findElements(By.cssSelector("[id^='student-row-']")).size();

        assertEquals(orig_size, updated_size);
    }

    @Test
    @Order(6)
    @DisplayName("Create student with lower bound name")
    public void createValidStudentLowerBoundBoundName() {
        driver.get("http://localhost:5173/students");

        createStudent("C", "Computer Science", "3.5");

        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("student-list-table"), "C"));

        WebElement table = driver.findElement(By.id("student-list-table"));
        assertTrue(table.getText().contains("C"));
    }

    @Test
    @Order(7)
    @DisplayName("Create student with upper bound name")
    public void createValidStudentUpperBoundName() {
        driver.get("http://localhost:5173/students");

        String c = "C".repeat(255);
        createStudent(c, "Computer Science", "3.5");

        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("student-list-table"), "C"));

        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.id("student-list-table"), c
        ));

        WebElement table = driver.findElement(By.id("student-list-table"));
        assertTrue(table.getText().contains(c));
    }

    @Test
    @Order(8)
    @DisplayName("Create student with value below lower bound name")
    public void createStudentBelowLowerBoundName() {
        driver.get("http://localhost:5173/students");

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("[id^='student-row-']")
        ));

        int orig_size = driver.findElements(By.cssSelector("[id^='student-row-']")).size();

        createStudent("", "Computer Science", "3.5");

        int updated_size = driver.findElements(By.cssSelector("[id^='student-row-']")).size();

        assertEquals(orig_size, updated_size);
    }

    @Test
    @Order(9)
    @DisplayName("Create student with value above upper bound name")
    public void createStudentAboveUpperBoundName() {
        driver.get("http://localhost:5173/students");

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("[id^='student-row-']")
        ));

        int orig_size = driver.findElements(By.cssSelector("[id^='student-row-']")).size();

        String c = "C".repeat(256);
        createStudent(c, "Computer Science", "3,5");

        int updated_size = driver.findElements(By.cssSelector("[id^='student-row-']")).size();

        assertEquals(orig_size, updated_size);
    }

    @Test
    @Order(10)
    @DisplayName("Create student with major missing")
    public void createStudentMajorMissing() {
        driver.get("http://localhost:5173/students");

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("[id^='student-row-']")
        ));

        int orig_size = driver.findElements(By.cssSelector("[id^='student-row-']")).size();

        createStudent("John Smith", "", "3,5");

        int updated_size = driver.findElements(By.cssSelector("[id^='student-row-']")).size();

        assertEquals(orig_size, updated_size);
    }

    @Test
    @Order(11)
    @DisplayName("Create student with major missing")
    public void createStudentGPAMissing() {
        driver.get("http://localhost:5173/students");

        createStudent("John Smith", "Computer Science", "");

        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("student-list-table"), ""));

        WebElement table = driver.findElement(By.id("student-list-table"));
        assertTrue(table.getText().contains(""));

    }

    @Test
    @Order(12)
    @DisplayName("Edit student name")
    public void editStudentName() {
        driver.get("http://localhost:5173/students");

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("[id^='edit-student-button']")
        ));

        WebElement row = driver.findElement(
                By.xpath("(//tr[starts-with(@id,'student-row-')])[2]")
        );

        row.findElement(By.id("edit-student-button")).click();

        driver.findElement(By.id("edit-student-name")).clear();
        driver.findElement(By.id("edit-student-name")).sendKeys("Mike Barnes");

        driver.findElement(By.id("edit-student-save-button")).click();

        WebElement table = driver.findElement(By.id("student-list-table"));

        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.id("student-list-table"),
                "Mike Barnes"
        ));

        assertTrue(table.getText().contains("Mike Barnes"));
    }

    @Test
    @Order(13)
    @DisplayName("Edit student gpa")
    public void editStudentGPA() {
        driver.get("http://localhost:5173/students");

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("[id^='edit-student-button']")
        ));

        WebElement row = driver.findElement(By.cssSelector("[id^='student-row-']"));

        row.findElement(By.id("edit-student-button")).click();

        driver.findElement(By.id("edit-student-gpa")).clear();
        driver.findElement(By.id("edit-student-gpa")).sendKeys("2.4");

        driver.findElement(By.id("edit-student-save-button")).click();

        WebElement table = driver.findElement(By.id("student-list-table"));

        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.id("student-list-table"),
                "2.4"
        ));

        assertTrue(table.getText().contains("2.4"));
    }

    @Test
    @Order(14)
    @DisplayName("Edit student major")
    public void editStudentMajor() {
        driver.get("http://localhost:5173/students");

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("[id^='edit-student-button']")
        ));

        WebElement row = driver.findElement(By.cssSelector("[id^='student-row-']"));
        row.findElement(By.id("edit-student-button")).click();

        driver.findElement(By.cssSelector("[id^='edit-student-button']")).click();

        driver.findElement(By.id("edit-student-major")).clear();
        driver.findElement(By.id("edit-student-major")).sendKeys("Accounting");

        driver.findElement(By.id("edit-student-save-button")).click();

        WebElement table = driver.findElement(By.id("student-list-table"));

        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.id("student-list-table"),
                "Accounting"
        ));

        assertTrue(table.getText().contains("Accounting"));
    }

    @Test
    @Order(15)
    @DisplayName("Edit student invalid name")
    public void editStudentInvalidName() {
        driver.get("http://localhost:5173/students");

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("[id^='edit-student-button']")
        ));

        WebElement row = driver.findElement(By.cssSelector("[id^='student-row-']"));
        row.findElement(By.id("edit-student-button")).click();

        driver.findElement(By.cssSelector("[id^='edit-student-button']")).click();

        driver.findElement(By.id("edit-student-name")).clear();
        driver.findElement(By.id("edit-student-name")).sendKeys("");

        driver.findElement(By.id("edit-student-save-button")).click();

        WebElement table = driver.findElement(By.id("student-list-table"));

        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.id("student-list-table"), "Mike Barnes"
        ));

        assertTrue(table.getText().contains("Mike Barnes"));
    }

    @Test
    @Order(16)
    @DisplayName("Edit student invalid gpa")
    public void editStudentInvalidGPA() {
        driver.get("http://localhost:5173/students");

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("[id^='edit-student-button']")
        ));

        WebElement row = driver.findElement(By.cssSelector("[id^='student-row-']"));
        row.findElement(By.id("edit-student-button")).click();

        driver.findElement(By.cssSelector("[id^='edit-student-button']")).click();

        driver.findElement(By.id("edit-student-gpa")).clear();
        driver.findElement(By.id("edit-student-gpa")).sendKeys("4.5");

        driver.findElement(By.id("edit-student-save-button")).click();

        WebElement table = driver.findElement(By.id("student-list-table"));

        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.id("student-list-table"), "2.4"
        ));

        assertTrue(table.getText().contains("2.4"));
    }

    @Test
    @Order(17)
    @DisplayName("Edit student invalid major")
    public void editStudentInvalidMajor() {
        driver.get("http://localhost:5173/students");

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("[id^='edit-student-button']")
        ));

        WebElement row = driver.findElement(By.cssSelector("[id^='student-row-']"));
        row.findElement(By.id("edit-student-button")).click();

        driver.findElement(By.cssSelector("[id^='edit-student-button']")).click();

        driver.findElement(By.id("edit-student-major")).clear();
        driver.findElement(By.id("edit-student-major")).sendKeys("");

        driver.findElement(By.id("edit-student-save-button")).click();

        WebElement table = driver.findElement(By.id("student-list-table"));

        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.id("student-list-table"), "Accounting"
        ));

        assertTrue(table.getText().contains("Accounting"));
    }

    @Test
    @Order(18)
    @DisplayName("Delete student")
    public void deleteStudent() {
        driver.get("http://localhost:5173/students");

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("[id^='student-row-']")
        ));

        int before = driver.findElements(By.cssSelector("[id^='student-row-']")).size();

        driver.findElement(By.cssSelector("[id^='delete-student-button']")).click();


        wait.until(d ->
                d.findElements(By.cssSelector("[id^='student-row-']")).size() == before - 1
        );

        int after = driver.findElements(By.cssSelector("[id^='student-row-']")).size();

        assertEquals(before - 1, after);
    }
}