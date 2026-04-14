package com.baarsch_bytes.end2end;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentUITest {
    private static final int MAX_WAIT = 10;
    private static final String BASE_URL = "http://frontend:5173";

    private WebDriver driver;
    private WebDriverWait wait;
}